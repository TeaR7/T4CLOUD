package com.t4cloud.t.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.annotation.cache.DataRuleCacheEvict;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.client.UserSysUserClient;
import com.t4cloud.t.feign.dto.SysUserDTO;
import com.t4cloud.t.system.entity.SysUserCompany;
import com.t4cloud.t.system.service.ISysUserCompanyService;
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
 * 用户公司表 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-15
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "用户公司表" , tags = "用户公司表接口" , position = 10)
@RequestMapping("/SysUserCompany")
public class SysUserCompanyController extends T4Controller<SysUserCompany, ISysUserCompanyService> {

    @Autowired
    private final UserSysUserClient userSysUserClient;

    /**
     * 根据公司ID获取所有用户
     */
    @AutoLog(value = "用户公司表-根据公司ID获取所有用户")
    @GetMapping("/getUserList/{companyId}")
    @RequiresPermissions("system:SysUserCompany:VIEW")
    @ApiOperation(position = 2, value = "用户公司表-根据公司ID获取所有用户" , notes = "传入用户名")
    public R<List<SysUserDTO>> getUserList(@ApiParam("公司ID") @PathVariable String companyId,
                                           @ApiParam("用户名") @RequestParam(required = false) String username,
                                           @ApiParam("姓名") @RequestParam(required = false) String realname) {
        //查询所有绑定关系
        List<SysUserCompany> list = service.lambdaQuery().eq(SysUserCompany::getCompanyId, companyId).list();

        List<SysUserDTO> userDTOList = new ArrayList<>();
        //调用feign获取userList
        if (CollectionUtil.isNotEmpty(list)) {
            SysUserDTO sysUserDTO = new SysUserDTO()
                    .setUsername(username)
                    .setRealname(realname)
                    .setId(StringUtils.join(
                            list.stream().map(SysUserCompany::getUserId).collect(Collectors.toList()),
                            StrUtil.C_COMMA));
            R<List<SysUserDTO>> result = userSysUserClient.list(sysUserDTO);
            if (result.isSuccess()) {
                userDTOList = result.getResult();
            }
        }
        return R.ok("用户公司表-根据公司ID获取所有用户成功" , userDTOList);
    }


    /**
     * 用户公司表 公司添加用户（支持批量）
     */
    @AutoLog(value = "用户公司表 公司添加用户" , operateType = 1)
    @PutMapping("/save")
    @RequiresPermissions("system:SysUserCompany:EDIT")
    @ApiOperation(position = 4, value = "用户公司表-公司添加用户" , notes = "传入公司ID和需要新增的用户ID(,分割）")
    @DataRuleCacheEvict
    public R save(@Valid @RequestBody SysUserCompany sysUserCompany) {
        //查询之前的所有用户
        List<SysUserCompany> sysUserCompanies = service.lambdaQuery().eq(SysUserCompany::getCompanyId, sysUserCompany.getCompanyId()).list();
        List<String> oldUserIds = sysUserCompanies.stream().map(SysUserCompany::getUserId).collect(Collectors.toList());

        //新的用户
        List<String> newUserIds = Arrays.asList(sysUserCompany.getUserIds().split(","));

        //处理新增的
        List<String> addUserIds = newUserIds.stream().filter(item -> StrUtil.isNotEmpty(item) && !oldUserIds.contains(item)).collect(Collectors.toList());
        List<SysUserCompany> addUserList = new ArrayList<>();
        for (String userId : addUserIds) {
            SysUserCompany userRole = new SysUserCompany().setCompanyId(sysUserCompany.getCompanyId()).setUserId(userId);
            addUserList.add(userRole);
        }
        service.saveBatch(addUserList);
        return R.ok("用户公司表-公司添加用户成功");
    }

    /**
     * 用户公司表 公司删除用户（支持批量）
     */
    @AutoLog(value = "用户公司表 公司删除用户" , operateType = 2)
    @DeleteMapping("/delete")
    @RequiresPermissions("system:SysUserCompany:EDIT")
    @ApiOperation(position = 5, value = "用户公司表-公司删除用户" , notes = "传入公司ID和需要删除的用户ID(,分割）")
    @DataRuleCacheEvict
    public R delete(
            @ApiParam("用户ID") @RequestParam String userIds,
            @ApiParam("公司ID") @RequestParam String companyId
    ) {
        //处理删除的
        List<String> deleteUserIds = Arrays.asList(userIds.split(","));
        service.lambdaUpdate().in(SysUserCompany::getUserId, deleteUserIds).eq(SysUserCompany::getCompanyId, companyId).remove();

        return R.ok("用户公司表-公司删除用户成功");
    }

}
