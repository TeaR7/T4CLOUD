package com.t4cloud.t.user.controller;

import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.client.SystemSysRoleClient;
import com.t4cloud.t.feign.dto.SysRoleDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 登录相关功能 控制器
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-10
 */
@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/test")
@Api(value = "测试接口" , tags = "测试接口" , position = 0)
public class FeignDemoController {

    @Autowired
    private final SystemSysRoleClient systemSysRoleClient;

    /**
     * 修改密码
     */
    @AutoLog(value = "获取用户信息" , operateType = 4, logType = 2)
    @GetMapping("/testSysRoleList")
    @ApiOperation(position = 5, value = "获取用户信息" , notes = "已登录的用户信息")
    public R<List<SysRoleDTO>> userInfo(SysRoleDTO sysRole) {
        return systemSysRoleClient.list(sysRole);
    }


}
