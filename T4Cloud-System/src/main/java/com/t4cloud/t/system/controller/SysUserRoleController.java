package com.t4cloud.t.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.RoleCacheEvict;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.client.SystemSysUserClient;
import com.t4cloud.t.feign.dto.SysUserDTO;
import com.t4cloud.t.system.entity.SysRole;
import com.t4cloud.t.system.entity.SysUserRole;
import com.t4cloud.t.system.service.ISysUserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户角色表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-02-27
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "用户角色表", tags = "用户角色表接口", position = 13)
@RequestMapping("/SysUserRole")
public class SysUserRoleController extends T4Controller<SysUserRole, ISysUserRoleService> {

    @Autowired
    private final SystemSysUserClient systemSysUserClient;

    /**
     * 根据用户名获取用户所有角色
     */
    @AutoLog(value = "用户角色表-根据用户名获取用户所有角色")
    @GetMapping("/getRoleList/{username}")
    @RequiresPermissions("system:SysUserRole:VIEW")
    @ApiOperation(position = 1, value = "用户角色表-根据用户名获取用户所有角色", notes = "传入用户名")
    public R<List<SysRole>> getRoleList(@ApiParam("用户名") @PathVariable String username) {
        List<SysRole> list = service.getUserRoleList(username);
        return R.ok("用户角色表-获取用户所有角色成功", list);
    }

    /**
     * 根据角色ID获取所有用户
     */
    @AutoLog(value = "用户角色表-根据角色ID获取所有用户")
    @GetMapping("/getUserList/{roleId}")
    @RequiresPermissions("system:SysUserRole:VIEW")
    @ApiOperation(position = 2, value = "用户角色表-根据角色ID获取所有用户", notes = "传入用户名")
    public R<List<SysUserDTO>> getUserList(@ApiParam("角色ID") @PathVariable String roleId,
                                           @ApiParam("用户名") @RequestParam(required = false) String username,
                                           @ApiParam("姓名") @RequestParam(required = false) String realname) {
        //查询所有绑定关系
        List<SysUserRole> list = service.lambdaQuery().eq(SysUserRole::getRoleId, roleId).list();

        List<SysUserDTO> userDTOList = new ArrayList<>();
        //调用feign获取userList
        if (CollectionUtil.isNotEmpty(list)) {
            SysUserDTO sysUserDTO = new SysUserDTO()
                    .setUsername(username)
                    .setRealname(realname)
                    .setId(StringUtils.join(
                            list.stream().map(SysUserRole::getUserId).collect(Collectors.toList()),
                            StrUtil.C_COMMA));
            R<List<SysUserDTO>> result = systemSysUserClient.list(sysUserDTO);
            if (result.isSuccess()) {
                userDTOList = result.getResult();
            }
        }
        return R.ok("用户角色表-获取所有用户成功", userDTOList);
    }


    /**
     * 保存 用户保存角色
     */
    @AutoLog(value = "保存 用户保存角色", operateType = 1)
    @PutMapping("/saveByUser")
    @RequiresPermissions("system:SysUserRole:EDIT")
    @ApiOperation(position = 3, value = "用户角色表-用户保存角色", notes = "传入用户ID和所有角色ID(,分割），注意，是所有")
    @RoleCacheEvict // TODO 这个注解有个问题，我偷懒了，任何一个用户编辑他的角色的时候，不仅会清空他自己的redis角色和权限信息，是清空所有人的.以后可以优化一下此处，暂时问题不大
    public R saveByUser(@RequestBody SysUserRole sysUserRole) {

        //查询之前的所有角色
        List<SysUserRole> sysUserRoleList = service.lambdaQuery().eq(SysUserRole::getUserId, sysUserRole.getUserId()).list();
        List<String> oldRoleIds = sysUserRoleList.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());

        //新的权限
        List<String> newRoleIds = StrUtil.isBlank(sysUserRole.getRoleIds()) ? new ArrayList<>() : Arrays.asList(sysUserRole.getRoleIds().split(","));

        //处理新增的
        List<String> addRoleIds = newRoleIds.stream().filter(item -> StrUtil.isNotEmpty(item) && !oldRoleIds.contains(item)).collect(Collectors.toList());
        List<SysUserRole> addRoleList = new ArrayList<>();
        for (String roleId : addRoleIds) {
            SysUserRole userRole = new SysUserRole().setRoleId(roleId).setUserId(sysUserRole.getUserId());
            addRoleList.add(userRole);
        }
        service.saveBatch(addRoleList);
        //处理删除的
        List<String> deleteRoleIds = oldRoleIds.stream().filter(item -> StrUtil.isNotEmpty(item) && !newRoleIds.contains(item)).collect(Collectors.toList());
        service.removeByIds(sysUserRoleList.stream().filter(item -> deleteRoleIds.contains(item.getRoleId())).map(SysUserRole::getId).collect(Collectors.toList()));
        return R.ok("用户角色表-用户保存角色成功");
    }

    /**
     * 删除 清空用户角色
     */
    @AutoLog(value = "删除 清空用户角色", operateType = 2)
    @DeleteMapping("/deleteByUser")
    @RequiresPermissions("system:SysUserRole:EDIT")
    @ApiOperation(position = 4, value = "删除 清空用户角色", notes = "传入用户ID,可批量传入")
    @RoleCacheEvict // TODO 这个注解有个问题，我偷懒了，任何一个用户编辑他的角色的时候，不仅会清空他自己的redis角色和权限信息，是清空所有人的.以后可以优化一下此处，暂时问题不大
    public R<?> deleteByUser(@RequestParam String ids) {
        //查询之前的所有角色
        List<SysUserRole> sysUserRoleList = service.lambdaQuery().in(SysUserRole::getUserId, Arrays.asList(ids.split(","))).list();
        service.removeByIds(sysUserRoleList.stream().map(SysUserRole::getId).collect(Collectors.toList()));
        return R.ok("用户角色表-清空用户角色成功");
    }

    /**
     * 保存 角色添加用户（支持批量）
     */
    @AutoLog(value = "保存 角色添加用户", operateType = 1)
    @PutMapping("/saveByRole")
    @RequiresPermissions("system:SysUserRole:EDIT")
    @ApiOperation(position = 4, value = "用户角色表-角色添加用户", notes = "传入角色ID和需要新增的用户ID(,分割）")
    @RoleCacheEvict // TODO 这个注解有个问题，我偷懒了，任何一个用户编辑他的角色的时候，不仅会清空他自己的redis角色和权限信息，是清空所有人的.以后可以优化一下此处，暂时问题不大
    public R saveByRole(@Valid @RequestBody SysUserRole sysUserRole) {
        //查询之前的所有用户
        List<SysUserRole> sysUserRoleList = service.lambdaQuery().eq(SysUserRole::getRoleId, sysUserRole.getRoleId()).list();
        List<String> oldUserIds = sysUserRoleList.stream().map(SysUserRole::getUserId).collect(Collectors.toList());

        //新的用户
        List<String> newUserIds = Arrays.asList(sysUserRole.getUserIds().split(","));

        //处理新增的
        List<String> addUserIds = newUserIds.stream().filter(item -> StrUtil.isNotEmpty(item) && !oldUserIds.contains(item)).collect(Collectors.toList());
        List<SysUserRole> addUserList = new ArrayList<>();
        for (String userId : addUserIds) {
            SysUserRole userRole = new SysUserRole().setRoleId(sysUserRole.getRoleId()).setUserId(userId);
            addUserList.add(userRole);
        }
        service.saveBatch(addUserList);
        return R.ok("用户角色表-角色添加用户成功");
    }

    /**
     * 删除 角色删除用户（支持批量）
     */
    @AutoLog(value = "删除 角色删除用户", operateType = 2)
    @DeleteMapping("/deleteByRole")
    @RequiresPermissions("system:SysUserRole:EDIT")
    @ApiOperation(position = 5, value = "用户角色表-角色删除用 户", notes = "传入角色ID和需要删除的用户ID(,分割）")
    @RoleCacheEvict // TODO 这个注解有个问题，我偷懒了，任何一个用户编辑他的角色的时候，不仅会清空他自己的redis角色和权限信息，是清空所有人的.以后可以优化一下此处，暂时问题不大
    public R deleteByRole(
            @ApiParam("用户ID") @RequestParam String userIds,
            @ApiParam("角色ID") @RequestParam String roleId
    ) {
        //处理删除的
        List<String> deleteUserIds = Arrays.asList(userIds.split(","));
        service.lambdaUpdate().in(SysUserRole::getUserId, deleteUserIds).eq(SysUserRole::getRoleId, roleId).remove();
        return R.ok("用户角色表-角色删除用户成功");
    }


}
