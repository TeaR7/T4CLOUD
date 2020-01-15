package com.t4cloud.t.common.exception;

import com.t4cloud.t.common.entity.dto.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 处理404异常
 *
 * --------------------
 * @author TeaR
 * @date 2020/1/15 16:08
 */
@Controller
public class NotFoundException implements ErrorController {
 
    @Override
    public String getErrorPath() {
        return "/error";
    }
 
    @RequestMapping(value = {"/error"})
    @ResponseBody
    public Object error(HttpServletRequest request) {
        return Result.error(404, "路径不存在，请检查路径是否正确");
    }
}