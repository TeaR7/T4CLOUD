package com.t4cloud.t.user.controller;

import com.t4cloud.t.common.controller.T4Controller;
import com.t4cloud.t.common.entity.dto.Result;
import com.t4cloud.t.common.exception.T4CloudException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class TestController extends T4Controller {

    /**
     * @return
     * @Author 政辉
     */
    @ApiOperation(value = "测试方法", notes = "具体用来测试是否可以成功返回")
    @GetMapping("/403")
    public Result noauth() {
        throw new T4CloudException("测试异常d");
//        return Result.ok("测试成功");
    }

}
