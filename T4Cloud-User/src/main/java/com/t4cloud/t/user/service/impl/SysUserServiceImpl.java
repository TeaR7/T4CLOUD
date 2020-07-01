package com.t4cloud.t.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.t4cloud.t.base.constant.CacheConstant;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.base.utils.JwtUtil;
import com.t4cloud.t.base.utils.PwdUtil;
import com.t4cloud.t.base.utils.RedisUtil;
import com.t4cloud.t.base.utils.UUIDUtil;
import com.t4cloud.t.feign.client.SystemSysUserRoleClient;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import com.t4cloud.t.feign.dto.SysUserRoleDTO;
import com.t4cloud.t.feign.dto.SysUserThirdDTO;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.entity.SysUserThird;
import com.t4cloud.t.user.mapper.SysUserMapper;
import com.t4cloud.t.user.service.ISysUserService;
import com.t4cloud.t.user.service.ISysUserThirdService;
import com.vdurmont.emoji.EmojiParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/16 13:39
 */
@Service
@Slf4j
@AllArgsConstructor
public class SysUserServiceImpl extends T4ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private Environment environment;

    @Autowired
    private ISysUserThirdService sysUserThirdService;
    @Autowired
    private SystemSysUserRoleClient systemSysUserRoleClient;

    /**
     * 用户校验成功后生成token
     *
     * @param user 已登录用户对象
     *             <p>
     * @return com.t4cloud.t.common.entity.LoginUser
     * --------------------
     * @author TeaR
     * @date 2020/1/16 15:11
     */
    @Override
    public LoginUser generateToken(LoginUser user) {
        String token = JwtUtil.sign(user.getId(), user.getUsername(), user.getPassword());
        // 设置token缓存有效时间
        redisUtil.set(CacheConstant.SYS_USERS_TOKEN + user.getId() + "-" + token, token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        redisUtil.set(CacheConstant.SYS_USERS_CACHE + user.getId(), user, JwtUtil.EXPIRE_TIME * 2 / 1000);
        user.setToken(token);

        //脱敏处理
        user.setPassword(null);
        user.setId(null);
        user.setSalt(null);

        return user;
    }

    /**
     * 检验基础的图片验证码
     *
     * @param code 用户提交的验证码
     * @param key  验证码对应的KEY
     *             <p>
     * @return boolean
     * --------------------
     * @author TeaR
     * @date 2020/2/10 17:33
     */
    @Override
    public boolean checkCode(String prefix, String code, String key) {

        //获取是否验证验证码
        Boolean check = Boolean.parseBoolean(environment.getProperty("t4cloud.check-code"));

        if (check) {
            //开始验证验证码
            String checkCode = (String) redisUtil.get(prefix + key);

            if (StringUtils.isEmpty(checkCode)) {
                return false;
            }

            if (!checkCode.equalsIgnoreCase(code)) {
                return false;
            }

            //验证通过，失效该验证码
            redisUtil.del(prefix + key);
        }

        return true;
    }

    /**
     * 刷新用户缓存
     *
     * @param userId 用户ID
     *               <p>
     * @return void
     * --------------------
     * @author TeaR
     * @date 2020/4/22 14:53
     */
    @Override
    public void freshUserCache(String userId) {
        //更新用户缓存
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(getById(userId), loginUser);
        redisUtil.set(CacheConstant.SYS_USERS_CACHE + userId, loginUser, redisUtil.getExpire(CacheConstant.SYS_USERS_CACHE + userId));
    }

    /**
     * 通过第三方用户信息新增或获取用户信息
     *
     * @param userOauth  第三方信息
     * @param sysRoleDTO 用户角色（新增用户的时候生效）
     *                   <p>
     * @return com.t4cloud.t.user.entity.SysUser
     * --------------------
     * @author TeaR
     * @date 2020/6/28 16:17
     */
    @Override
    public SysUser getByThirdInfo(SysUserThirdDTO userOauth, SysRoleDTO sysRoleDTO) {

        //根据loginType和unionId查询第三表
        SysUserThird sysUserThird = sysUserThirdService.lambdaQuery()
                .eq(SysUserThird::getLoginType, userOauth.getLoginType())
                .eq(SysUserThird::getOutId, userOauth.getOutId()).one();

        //最后准备的用户对象
        SysUser user;

        //如果为null的话则是新用户，先插入
        if (null == sysUserThird) {
            //如果没查到就开始注册用户

            sysUserThird = new SysUserThird();

            BeanUtil.copyProperties(userOauth, sysUserThird);

            //然后插入用户表
            user = new SysUser();
            //配置用户信息
            String username = EmojiParser.removeAllEmojis(sysUserThird.getNickName());
            if (StrUtil.isBlank(username)) {
                username = "";
            }
            String generate = IdUtil.objectId() + IdUtil.objectId();
            if (username.length() + generate.length() <= 32) {
                username = username + generate;
            } else {
                username = username + "-" + generate.substring(username.length() + 1, 32);
            }
            user.setUsername(username);
            user.setPhone(sysUserThird.getPhone());
            user.setEmail(sysUserThird.getEmail());
            user.setRealname(sysUserThird.getNickName());
            user.setWorkNo(UUIDUtil.shortCode(username)[0]);
            user.setAvatar(sysUserThird.getImg());
            user.setGender(Integer.parseInt(sysUserThird.getGender()));
            //配置密码，但是第三方登录默认没有密码
            String salt = RandomUtil.randomString(8);
            user.setSalt(salt);
            //填充随机密码
            String password = PwdUtil.encrypt(user.getUsername(), RandomUtil.randomString(8), user.getSalt());
            user.setPassword(password);
            user.setStatus(1);
            save(user);

            //将user表中id放入第三方表的in_id
            sysUserThird.setInId(user.getId());
            sysUserThirdService.save(sysUserThird);

            //增加用户权限
            SysUserRoleDTO userRoleDTO = new SysUserRoleDTO();
            userRoleDTO.setUserId(user.getId());
            userRoleDTO.setRoleIds(sysRoleDTO.getId());
            systemSysUserRoleClient.regUserRole(userRoleDTO);

            //注册完成,user对象获取成功

        } else {
            //说明地三方表中有，已经注册过的，更新信息
            //写入openid
            sysUserThird.setImg(userOauth.getImg());
            sysUserThird.setNickName(userOauth.getNickName());

            sysUserThird.setAccessToken(userOauth.getAccessToken());
            sysUserThird.setExpress(userOauth.getExpress());
            sysUserThirdService.updateById(sysUserThird);

            //更新完毕，用in_id查询用户
            user = getById(sysUserThird.getInId());

            //更新用户信息（直接覆盖） -- 如果需要随微信更新信息的话就打开此处
//                    user.setRealname(sysUserThird.getNickName());
//                    user.setAvatar(sysUserThird.getImg());
//                    user.setGender(Integer.parseInt(sysUserThird.getGender()));
//
//                    service.updateById(user);

            //登录完成,user对象获取成功
        }

        return user;
    }

}
