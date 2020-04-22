package com.t4cloud.t.mock.controller;

import cn.hutool.json.JSONObject;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.mock.entity.SysMock;
import com.t4cloud.t.mock.service.ISysMockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mock/api")
@Api(value = "模拟数据接口" , tags = "MOCK接口" , position = 1)
public class MockController extends T4Controller<SysMock, ISysMockService> {


    @AutoLog(value = "MOCK-API")
    @RequestMapping("/{url}")
    @ApiOperation(position = 1, value = "MOCK-API" , notes = "传入MOCK的URL即可获取预先定义好的数据")
    public R<JSONObject> mock(HttpServletRequest request, @PathVariable String url) {
        String method = request.getMethod();
        return service.getData(url, method);
    }

}
