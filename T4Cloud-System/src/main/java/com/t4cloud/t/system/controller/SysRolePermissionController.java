package com.t4cloud.t.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.PermissionCacheEvict;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.system.entity.SysRolePermission;
import com.t4cloud.t.system.service.ISysRolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色权限表 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-21
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "角色权限表", tags = "角色权限表接口", position = 12)
@RequestMapping("/SysRolePermission")
public class SysRolePermissionController extends T4Controller<SysRolePermission, ISysRolePermissionService> {

    /**
     * 全部列表 角色权限表
     */
    @AutoLog(value = "角色权限表-全部列表")
    @GetMapping("/list")
    @RequiresPermissions("system:SysRolePermission:VIEW")
    @ApiOperation(position = 1, value = "角色权限表-全部列表", notes = "传入sysRolePermission")
    public R<List<SysRolePermission>> list(SysRolePermission sysRolePermission, HttpServletRequest req) {
        QueryWrapper<SysRolePermission> sysRolePermissionQueryWapper = T4Query.initQuery(sysRolePermission, req.getParameterMap());
        List<SysRolePermission> list = service.list(sysRolePermissionQueryWapper);
        return R.ok("角色权限表-全部列表查询成功", list);
    }

    /**
     * 保存 角色授权
     */
    @AutoLog(value = "保存 角色授权", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("system:SysRolePermission:EDIT")
    @ApiOperation(position = 4, value = "保存 角色授权", notes = "传入sysRolePermission")
    @PermissionCacheEvict
    public R<?> save(@Valid @RequestBody SysRolePermission sysRolePermission, BindingResult bindingResult) {
        //查询之前的所有权限
        List<SysRolePermission> sysRolePermissions = service.lambdaQuery().eq(SysRolePermission::getRoleId, sysRolePermission.getRoleId()).list();
        List<String> oldPermissionIds = sysRolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
        //新的权限
        List<String> newPermissionIds = Arrays.asList(sysRolePermission.getPermissionIds().split(","));
        //处理新增的
        List<String> addPermissionIds = newPermissionIds.stream().filter(item -> StringUtils.isNotEmpty(item) && !oldPermissionIds.contains(item)).collect(Collectors.toList());
        List<SysRolePermission> addPermissionIdList = new ArrayList<>();
        for (String permissionId : addPermissionIds) {
            SysRolePermission rolepms = new SysRolePermission().setRoleId(sysRolePermission.getRoleId()).setPermissionId(permissionId);
            addPermissionIdList.add(rolepms);
        }
        service.saveBatch(addPermissionIdList);
        //处理删除的
        List<String> deletePermissionIds = oldPermissionIds.stream().filter(item -> StringUtils.isNotEmpty(item) && !newPermissionIds.contains(item)).collect(Collectors.toList());
        service.removeByIds(sysRolePermissions.stream().filter(item -> deletePermissionIds.contains(item.getPermissionId())).map(SysRolePermission::getId).collect(Collectors.toList()));
        return R.ok("角色授权表-保存成功");
    }


}
