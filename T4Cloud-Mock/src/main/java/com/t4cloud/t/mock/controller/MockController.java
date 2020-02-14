package com.t4cloud.t.mock.controller;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.netflix.ribbon.proxy.annotation.Http;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.controller.T4Controller;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.mock.entity.SysMock;
import com.t4cloud.t.mock.service.ISysMockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.bouncycastle.util.Strings;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/mock/api")
@Api(value = "模拟数据接口", tags = "MOCK接口")
public class MockController extends T4Controller<SysMock, ISysMockService> {


    @AutoLog(value = "MOCK-API")
    @RequestMapping("/{url}")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "MOCK-API", notes = "传入MOCK的URL即可获取预先定义好的数据")
    public R<JSONObject> mock(HttpServletRequest request, @PathVariable String url){
        String method = request.getMethod();
        return selectData(url,method);
    }


    public R<JSONObject> selectData(String url, String method){
        //获取账号密码,查看是否有效账户
        SysMock one = service.lambdaQuery().eq(SysMock::getMethod, method).eq(SysMock::getUrl, url).one();
        if(one == null){
            return R.error("该url的mock数据不存在，请在后台添加。");
        }
        String returnJsonData = one.getData();
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(returnJsonData);
            return R.ok("请求成功",jsonObject);
        }catch (JSONException e){
            return R.error("返回内容不是标准json格式，请在后台修改。");
        }
    }
}
