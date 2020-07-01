package com.t4cloud.t.user.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.EasyPoiUtil;
import com.t4cloud.t.base.utils.PwdUtil;
import com.t4cloud.t.base.utils.RedisUtil;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.feign.client.SystemSysUserRoleClient;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import com.t4cloud.t.feign.dto.SysUserRoleDTO;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.t4cloud.t.base.constant.LogConstant.LOG_TYPE_USER;
import static com.t4cloud.t.base.constant.LogConstant.OP_TYPE_EDIT;


/**
 * 用户表 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-19
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "用户表", tags = "用户表接口", position = 10)
@RequestMapping("/SysUser")
public class SysUserController extends T4Controller<SysUser, ISysUserService> {

    @Autowired
    private final SystemSysUserRoleClient systemSysUserRoleClient;

    @Autowired
    private final RedisUtil redisUtil;

    /**
     * 详情
     */
    @AutoLog(value = "用户表-详情", operateType = 4)
    @GetMapping("/detail")
    @RequiresPermissions("user:SysUser:VIEW")
    @ApiOperation(position = 1, value = "用户表-详情", notes = "传入sysUser")
    public R<SysUser> detail(SysUser sysUser, HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWrapper = T4Query.initQuery(sysUser, req.getParameterMap());
        SysUser detail = service.getOne(sysUserQueryWrapper);
        //查询用户角色
        if (detail == null) {
            return R.error("用户表-详情查询失败,找不到符合条件的用户");
        }
        //调用feign-client查询用户角色
        R<List<SysRoleDTO>> result = systemSysUserRoleClient.getRoleList(detail.getId());
        if (result.isSuccess()) {
            List<SysRoleDTO> sysRoleDTOS = result.getResult();
            List<String> roleIds = sysRoleDTOS.stream().map(SysRoleDTO::getId).collect(Collectors.toList());
            detail.setRoles(StringUtils.join(roleIds.toArray(), StrUtil.C_COMMA));
        }
        return R.ok("用户表-详情查询成功", detail);
    }

    /**
     * 全部列表 用户表
     */
    @AutoLog(value = "用户表-全部列表", operateType = 4)
    @GetMapping("/list")
    @RequiresPermissions("user:SysUser:VIEW")
    @ApiOperation(position = 2, value = "用户表-全部列表", notes = "传入sysUser")
    public R<List<SysUser>> list(SysUser sysUser, HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWrapper = T4Query.initQuery(sysUser, req.getParameterMap());
        List<SysUser> list = service.list(sysUserQueryWrapper);
        return R.ok("用户表-全部列表查询成功", list);
    }

    /**
     * 全部列表(受限查询） 用户表
     */
    @AutoLog(value = "用户表-全部列表(受限查询）", operateType = 4)
    @GetMapping("/limitList")
    @ApiOperation(position = 2, value = "用户表-全部列表(受限查询）", notes = "传入sysUser")
    public R<List<SysUser>> limitList(SysUser sysUser, HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWrapper = T4Query.initQuery(sysUser, req.getParameterMap());
        sysUserQueryWrapper.lambda().select(SysUser::getAvatar, SysUser::getRealname, SysUser::getId);
        List<SysUser> list = service.list(sysUserQueryWrapper);
        return R.ok("用户表-全部列表(受限查询）查询成功", list);
    }

    /**
     * 分页查询 用户表
     */
    @AutoLog(value = "用户表-分页查询", operateType = 4)
    @GetMapping("/page")
    @RequiresPermissions("user:SysUser:VIEW")
    @ApiOperation(position = 3, value = "用户表-分页查询", notes = "传入sysUser")
    public R<IPage<SysUser>> page(SysUser sysUser,
                                  @ApiParam(name = "pageNo", required = false)
                                  @RequestParam(name = "pageNo", required = false, defaultValue = "1") Integer pageNo,
                                  @ApiParam(name = "pageSize", required = false)
                                  @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                  HttpServletRequest req) {
        QueryWrapper<SysUser> sysUserQueryWrapper = T4Query.initQuery(sysUser, req.getParameterMap());
        //限定用户角色
        if (StrUtil.isNotBlank(sysUser.getRoles())) {
            //获取该角色下所有用户ID
            R<List<String>> userListR = systemSysUserRoleClient.getUserIdList(sysUser.getRoles());
            if (userListR.isSuccess()) {
                sysUserQueryWrapper.lambda().in(CollectionUtil.isNotEmpty(userListR.getResult()), SysUser::getId, userListR.getResult());
            }
        }
        IPage<SysUser> pages = service.page(new Page<>(pageNo, pageSize), sysUserQueryWrapper);
        return R.ok("用户表-分页查询查询成功", pages);
    }

    /**
     * 新增 用户表
     */
    @AutoLog(value = "用户表-新增", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("user:SysUser:ADD")
    @ApiOperation(position = 4, value = "用户表-新增", notes = "传入sysUser")
    public R save(@Valid @RequestBody SysUser sysUser, BindingResult bindingResult) {
        //生成密码
        String salt = RandomUtil.randomString(8);
        sysUser.setSalt(salt);
        //填充随机密码
        String password = PwdUtil.encrypt(sysUser.getUsername(), sysUser.getPassword(), sysUser.getSalt());
        sysUser.setPassword(password);
        //先新增
        boolean save = service.save(sysUser);
        //编辑权限
        if (save) {
            //调用feignclient编辑用户权限
            SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO()
                    .setUserId(sysUser.getId())
                    .setRoleIds(sysUser.getRoles());
            systemSysUserRoleClient.saveByUser(sysUserRoleDTO);
        }
        return R.ok("用户表-新增成功", save);
    }

    /**
     * 修改 用户表
     */
    @AutoLog(value = "用户表-修改", operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"user:SysUser:ADD", "user:SysUser:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "用户表-修改", notes = "传入sysUser")
    public R update(@Valid @RequestBody SysUser sysUser, BindingResult bindingResult) {
        //只读属性不允许修改
        sysUser.setUsername(null).setWorkNo(null);
        //先更新
        boolean update = service.updateById(sysUser);
        //编辑权限
        if (update) {
            //调用feignclient编辑用户权限
            SysUserRoleDTO sysUserRoleDTO = new SysUserRoleDTO()
                    .setUserId(sysUser.getId())
                    .setRoleIds(sysUser.getRoles());
            systemSysUserRoleClient.saveByUser(sysUserRoleDTO);
        }
        return R.ok("用户表-修改成功", update);
    }

    /**
     * 用户编辑自己的信息
     */
    @AutoLog(value = "用户编辑自己的信息", operateType = OP_TYPE_EDIT, logType = LOG_TYPE_USER)
    @PostMapping("/updateFront")
    @ApiOperation(position = 5, value = "用户表-用户编辑自己的信息", notes = "传入sysUser")
    public R updateFront(@Valid @RequestBody SysUser sysUser, BindingResult bindingResult) {
        //只读属性不允许修改
        sysUser.setUsername(null).setWorkNo(null).setPhone(null).setEmail(null).setTenantId(null).setId(UserUtil.getCurrentUser().getId());
        boolean update = service.updateById(sysUser);
        if (update) {
            //更新用户缓存
            service.freshUserCache(UserUtil.getCurrentUser().getId());
        }
        return R.ok("资料更新成功", update);
    }


    /**
     * 删除 用户表
     */
    @AutoLog(value = "用户表-删除", operateType = 2)
    @DeleteMapping("/delete")
    @RequiresRoles("ADMIN")
    @RequiresPermissions("user:SysUser:DELETE")
    @ApiOperation(position = 8, value = "用户表-删除", notes = "传入ids")
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam(name = "ids") String ids) {
        //删除用户
        boolean remove = service.removeByIds(Arrays.asList(ids.split(",")));
        //删除用户角色
        systemSysUserRoleClient.deleteByUser(ids);
        return R.ok("用户表-删除成功", remove);
    }


    @AutoLog(value = "用户表-导出", operateType = 4)
    @GetMapping("/export")
    @RequiresPermissions("user:SysUser:EXPORT")
    @ApiOperation(position = 9, value = "用户表-导出", notes = "传入查询条件")
    public void export(SysUser sysUser,
                       @ApiParam("指定导出数据的id") @RequestParam(name = "selectedRowKeys", required = false) String selectedRowKeys,
                       @ApiParam("需要导出列") @RequestParam(name = "selectedColKeys", required = false) String selectedColKeys,
                       HttpServletRequest req) {
        QueryWrapper<SysUser> queryWrapper = StrUtil.isEmpty(selectedRowKeys) ? T4Query.initQuery(sysUser, req.getParameterMap()) : new QueryWrapper<SysUser>().in("id", selectedRowKeys.split(","));
        super.exportExl("用户表", selectedColKeys, queryWrapper);
    }


    @AutoLog(value = "用户表-导入", operateType = 1)
    @PutMapping("/import")
    @RequiresPermissions("user:SysUser:IMPORT")
    @ApiOperation(position = 10, value = "用户表-导入", notes = "传入查询条件")
    public R importXls(@ApiParam("文件对象") MultipartFile file) {
        return R.ok("用户表-导入成功", service.saveBatch(EasyPoiUtil.importExcel(file, SysUser.class)));
    }

    @AutoLog(value = "用户表-确认用户重复信息")
    @GetMapping("/check")
    @RequiresPermissions("user:SysUser:ADD")
    @ApiOperation(position = 11, value = "用户表-确认用户重复信息", notes = "传入查询属性名和属性值")
    public R check(
            @ApiParam("属性名") @RequestParam("key") String key,
            @ApiParam("属性值") @RequestParam("value") String value) {
        Boolean checkProp = checkProp(key, value);
        return R.ok(key + "：" + value + (checkProp ? "可用，无重名项" : "不可用，存在冲突"), checkProp);
    }

}
