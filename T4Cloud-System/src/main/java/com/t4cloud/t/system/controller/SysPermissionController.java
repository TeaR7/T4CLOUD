package com.t4cloud.t.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.PermissionCacheEvict;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.BaseEntity;
import com.t4cloud.t.base.entity.LoginUser;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.query.T4Query;
import com.t4cloud.t.base.utils.UserUtil;
import com.t4cloud.t.system.entity.SysPermission;
import com.t4cloud.t.system.entity.SysRole;
import com.t4cloud.t.system.entity.SysRolePermission;
import com.t4cloud.t.system.service.ISysPermissionService;
import com.t4cloud.t.system.service.ISysRolePermissionService;
import com.t4cloud.t.system.service.ISysUserRoleService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-13
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "菜单权限表", tags = "菜单权限表接口", position = 10)
@RequestMapping("/SysPermission")
public class SysPermissionController extends T4Controller<SysPermission, ISysPermissionService> {

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;
    @Autowired
    private ISysUserRoleService sysUserRoleService;

    /**
     * 详情
     */
    @AutoLog(value = "菜单权限表-详情", operateType = 4)
    @GetMapping("/detail")
    @RequiresPermissions("system:SysPermission:VIEW")
    @ApiOperation(position = 1, value = "菜单权限表-详情", notes = "传入sysPermission")
    public R<SysPermission> detail(SysPermission sysPermission, HttpServletRequest req) {
        QueryWrapper<SysPermission> sysPermissionQueryWrapper = T4Query.initQuery(sysPermission, req.getParameterMap());
        SysPermission detail = service.getOne(sysPermissionQueryWrapper);
        return R.ok("菜单权限表-详情查询成功", detail);
    }

    /**
     * 全部列表 菜单权限表
     */
    @AutoLog(value = "菜单权限表-全部列表", operateType = 4)
    @GetMapping("/list")
    @RequiresPermissions("system:SysPermission:VIEW")
    @ApiOperation(position = 2, value = "菜单权限表-全部列表", notes = "传入sysPermission")
    public R<List<SysPermission>> list(SysPermission sysPermission, HttpServletRequest req) {
        QueryWrapper<SysPermission> sysPermissionQueryWrapper = T4Query.initQuery(sysPermission, req.getParameterMap());
        List<SysPermission> list = service.list(sysPermissionQueryWrapper);
        return R.ok("菜单权限表-全部列表查询成功", list);
    }

    /**
     * 新增 菜单权限表
     */
    @AutoLog(value = "菜单权限表-新增", operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("system:SysPermission:ADD")
    @ApiOperation(position = 4, value = "菜单权限表-新增", notes = "传入sysPermission")
    public R save(@Valid @RequestBody SysPermission sysPermission, BindingResult bindingResult) {

        return R.ok("菜单权限表-新增成功", service.save(sysPermission));
    }

    /**
     * 修改 菜单权限表
     */
    @AutoLog(value = "菜单权限表-修改", operateType = 3)
    @PostMapping("/update")
    @RequiresPermissions(value = {"system:SysPermission:ADD", "system:SysPermission:EDIT"}, logical = Logical.OR)
    @ApiOperation(position = 5, value = "菜单权限表-修改", notes = "传入sysPermission")
    @PermissionCacheEvict
    public R update(@Valid @RequestBody SysPermission sysPermission, BindingResult bindingResult) {
        if (StringUtils.isEmpty(sysPermission.getParentId())) {
            sysPermission.setParentId("");
        }
        return R.ok("菜单权限表-修改成功", service.updateById(sysPermission));
    }


    /**
     * 删除 菜单权限表
     */
    @AutoLog(value = "菜单权限表-删除", operateType = 2)
    @DeleteMapping("/delete")
    @RequiresRoles("ADMIN")
    @RequiresPermissions("system:SysPermission:DELETE")
    @ApiOperation(position = 8, value = "菜单权限表-删除", notes = "传入ids")
    @PermissionCacheEvict
    public R delete(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        List<String> all = new ArrayList<>(idList);
        //寻找子权限
        service.findChildren(idList, all);
        //移除权限本身
        service.removeByIds(all);
        //移除角色授权
        List<SysRolePermission> sysRolePermissions = sysRolePermissionService.lambdaQuery().in(SysRolePermission::getPermissionId, idList).list();
        List<String> sysRolePermissionList = sysRolePermissions.stream().map(BaseEntity::getId).collect(Collectors.toList());
        sysRolePermissionService.removeByIds(sysRolePermissionList);
        return R.ok("菜单权限表-删除成功");
    }


    /**
     * 详情
     */
    @AutoLog(value = "获取用户菜单权限")
    @GetMapping("/userPermission")
    @ApiOperation(position = 10, value = "菜单权限表-获取用户菜单权限", notes = "通过TOKEN获取用户菜单权限")
    public R<List<SysPermission>> userPermission() {
        LoginUser currentUser = UserUtil.getCurrentUser();

        //查询用户有的所有权限
        List<SysPermission> metaList = service.queryByUserId(currentUser.getId());

        //判断是否超级管理员
        List<SysRole> sysRoleList = sysUserRoleService.getUserRoleList(currentUser.getId());
        List<String> roleCodeList = sysRoleList.stream().map(SysRole::getRoleCode).collect(Collectors.toList());
        if (roleCodeList.contains("SUPER-ADMIN")) {
            metaList = service.list();
        }

        //添加首页路由
        boolean hasHome = false;
        for (SysPermission sysPermission : metaList) {
            if ("首页".equals(sysPermission.getName())) {
                hasHome = true;
                break;
            }
        }
        if (!hasHome) {
            metaList.add(0, SysPermission.getHomePage());
        }

        // 将数据处理成树状结构

        //获取根目录
        /** 这样有一个问题，就是会丢失独立的按钮权限
         * List<SysPermission> menuList = metaList.stream().filter(item -> StringUtils.isEmpty(item.getParentId())).collect(Collectors.toList());
         * -by TeaR  -2020/4/28-10:15
         */

        //获取ID合集，判断喝几盅是否存在父ID，若不存在则为独立根节点

        List<String> finalMetaIdList = metaList.stream().map(SysPermission::getId).collect(Collectors.toList());
        List<SysPermission> menuList = metaList.stream().filter(item -> !finalMetaIdList.contains(item.getParentId())).collect(Collectors.toList());

        //排序
        if (menuList == null || menuList.size() == 0) {
            menuList = new ArrayList<>();
        } else {
            menuList = menuList.stream().sorted(Comparator.comparing(SysPermission::getSortNo)).collect(Collectors.toList());
        }
        service.tree(menuList, metaList);

        return R.ok("用户权限查询成功", menuList);
    }


    /**
     * 全部列表（树状列表） 菜单权限表
     */
    @AutoLog(value = "菜单权限表-全部列表（树状列表）", operateType = 4)
    @GetMapping("/tree")
    @RequiresPermissions("system:SysPermission:VIEW")
    @ApiOperation(position = 11, value = "菜单权限表-全部列表（树状列表）", notes = "无需参数")
    public R<List<SysPermission>> tree() {
        //获取所有的权限
        List<SysPermission> metaList = service.list();

        //获取根目录
        /** 这样有一个问题，就是会丢失独立的按钮权限
         * List<SysPermission> menuList = metaList.stream().filter(item -> StringUtils.isEmpty(item.getParentId())).collect(Collectors.toList());
         * -by TeaR  -2020/4/28-10:15
         */

        //获取ID合集，判断喝几盅是否存在父ID，若不存在则为独立根节点

        List<String> finalMetaIdList = metaList.stream().map(SysPermission::getId).collect(Collectors.toList());
        List<SysPermission> menuList = metaList.stream().filter(item -> !finalMetaIdList.contains(item.getParentId())).collect(Collectors.toList());

        //排序
        if (menuList == null || menuList.size() == 0) {
            menuList = new ArrayList<>();
        } else {
            menuList = menuList.stream().sorted(Comparator.comparing(SysPermission::getSortNo)).collect(Collectors.toList());
        }
        //整理树状结构
        service.tree(menuList, metaList);
        return R.ok("菜单权限表-全部列表（树状列表）查询成功", menuList);
    }


}
