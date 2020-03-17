package com.t4cloud.t.support.controller;

import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.utils.EmailUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
public class EmailController {

    /**
     * 修改密码
     */
    @AutoLog(value = "测试邮件发送" , operateType = 4, logType = 2)
    @GetMapping("/testEmail")
    @ApiOperation(position = 1, value = "测试邮件发送" , notes = "测试邮件发送")
    public R<String> testEmail() {
        EmailUtil.SendMsg("zqr.it@qq.com","测试邮件2343","<h1>测试内容123</h1>");
        return R.ok("邮件发送成功");
    }


}
