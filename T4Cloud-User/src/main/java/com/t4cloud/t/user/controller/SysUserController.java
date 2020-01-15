package com.t4cloud.t.user.controller;

import com.t4cloud.t.common.annotation.AutoLog;
import com.t4cloud.t.common.controller.T4Controller;
import com.t4cloud.t.common.entity.dto.Result;
import com.t4cloud.t.common.exception.T4CloudException;
import com.t4cloud.t.common.utils.RedisUtil;
import com.t4cloud.t.user.entity.SysUser;
import com.t4cloud.t.user.mapper.SysUserMapper;
import com.t4cloud.t.user.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.t4cloud.t.common.constant.LogConstant.LOG_TYPE_ADMIN;
import static com.t4cloud.t.common.constant.LogConstant.OP_TYPE_QUERY;

/**
 * TestController
 * <p>
 * 测试用
 * <p>
 * ---------------------
 *
 * @author Terry
 * @date 2020/1/15 12:39
 */
@RestController
@RequestMapping("/test")
@Api(tags = "测试")
public class SysUserController extends T4Controller<SysUser, ISysUserService> {

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 测试接口
     * <p>
     * --------------------
     *
     * @author TeaR
     * @date 2020/1/15 18:12
     */
    @AutoLog(value = "测试")
    @ApiOperation(value = "测试方法", notes = "具体用来测试是否可以成功返回")
    @GetMapping("/403/{type}")
    public Result test(@PathVariable String type) {

        redisUtil.set("test", type);

        if (type.equalsIgnoreCase("1")) {
            throw new T4CloudException("测试异常d");
        }

        SysUser sysUser = service.getById("f5b910f2f895c1aca6662b38ea01aef6");

        redisUtil.get("test");
        redisUtil.set("test:" + type, sysUser);

        return Result.ok("测试成功" + type, sysUser);
    }

}
