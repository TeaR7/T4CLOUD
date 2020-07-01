package com.t4cloud.t.third.controller;

import cn.hutool.core.util.StrUtil;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.client.DingUserClient;
import com.t4cloud.t.feign.dto.SysUserThirdDTO;
import com.t4cloud.t.third.entity.ThirdConfig;
import com.t4cloud.t.third.service.IThirdConfigService;
import com.t4cloud.t.third.util.DingUtils;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 钉钉用户 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-06-28
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "钉钉用户操作接口", tags = "钉钉用户", position = 0)
@RequestMapping("/DingUser")
public class DingUserController implements DingUserClient {

    @Autowired
    private IThirdConfigService thirdConfigService;

    /**
     * 钉钉内H5登录鉴权
     */
    @Override
    @AutoLog(value = "钉钉内H5登录鉴权")
    @PostMapping("/oauth")
    @ApiOperation(position = 1, value = "钉钉内H5登录鉴权", notes = "钉钉内H5登录鉴权(CODE回调）")
    public R<SysUserThirdDTO> userOauth(
            @ApiParam(required = true, name = "code", value = "返回的code值")
            @RequestParam(value = "code") String code
    ) {
        //登录方式
        Integer loginType = 2;
        log.info("进入授权回调,code:" + code + ",loginType:" + loginType);

        if (StrUtil.isBlank(code)) {
            return R.error("code不允许为空！");
        }
        // 获取租户的钉钉配置信息
        ThirdConfig thirdConfig = thirdConfigService.lambdaQuery()
                .eq(ThirdConfig::getCode, loginType)
                .eq(ThirdConfig::getStatus, 1)
                .one();
        if (thirdConfig == null) {
            return R.error("无钉钉API配置！");
        }

        //1.通过code换取用户信息
        OapiSnsGetuserinfoBycodeResponse.UserInfo userOauth = null;
        try {
            userOauth = DingUtils.getUseroauth(thirdConfig, code);
        } catch (ApiException e) {
            e.printStackTrace();
            return R.error("获取钉钉用户信息异常！" + e.getErrMsg());
        }
        //openId
        String openid = userOauth.getOpenid();
        //唯一标识unionId
        String unionId = userOauth.getUnionid();
        if (StrUtil.isBlank(unionId)) {
            unionId = openid;
        }
        //昵称
        String nickname = userOauth.getNick();

        //获取access-token
        String accessToken;
        try {
            accessToken = DingUtils.getAccessToken(thirdConfig);
        } catch (ApiException e) {
            return R.error("获取钉钉系统Token异常！" + e.getErrMsg());
        }

        //获取用户userId
        String userId;
        try {
            userId = DingUtils.getUserIdByUnionId(accessToken, unionId);
        } catch (ApiException e) {
            return R.error("获取用户Userid异常！" + e.getErrMsg());
        }

        //获取用户详细信息
        OapiUserGetResponse userInfo = null;
        try {
            userInfo = DingUtils.getUserInfo(accessToken, userId);
        } catch (ApiException e) {
            e.printStackTrace();
            return R.error("获取用户详情异常！" + e.getErrMsg());
        }

        //头像
        String headimgurl = userInfo.getAvatar();
        if (headimgurl.startsWith("http:")) {
            headimgurl = headimgurl.replace("http:", "https:");
        }

        //性别(钉钉员工信息没有性别）
//        String sex = userInfo.get;

        //组装用户第三方信息对象
        SysUserThirdDTO userThird = new SysUserThirdDTO();
        userThird.setOutId(userId);
        userThird.setImg(headimgurl);
        userThird.setNickName(nickname);
        userThird.setPhone(userInfo.getMobile());
        userThird.setEmail(userInfo.getEmail());
        userThird.setLoginType(loginType);

        return R.ok("微信用户鉴权成功", userThird);
    }

}
