package com.t4cloud.t.user.authc;

import com.t4cloud.t.base.authc.aop.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置类，在此处放过本模块无需校验的接口
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/2/9 12:34
 */
@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * Filter Chain定义说明
     * <p>
     * 1、一个URL可以配置多个Filter，使用逗号分隔
     * 2、当设置多个过滤器时，全部验证通过，才视为通过
     * 3、部分过滤器可指定参数，如perms，roles
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        // 配置不会被拦截的链接 顺序判断

        // ----------------------------------------------- 资源相关（主要是SWAGGER） -----------------------------------------------
        filterChainDefinitionMap.put("/", "anon");
        filterChainDefinitionMap.put("/doc.html", "anon");
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.html", "anon");
        filterChainDefinitionMap.put("/**/*.svg", "anon");
        filterChainDefinitionMap.put("/**/*.pdf", "anon");
        filterChainDefinitionMap.put("/**/*.jpg", "anon");
        filterChainDefinitionMap.put("/**/*.png", "anon");
        filterChainDefinitionMap.put("/**/*.ico", "anon");
        filterChainDefinitionMap.put("/**/*.mp4", "anon");
        filterChainDefinitionMap.put("/**/*.ttf", "anon");
        filterChainDefinitionMap.put("/**/*.woff", "anon");
        filterChainDefinitionMap.put("/csrf", "anon"); // swagger-ui 需要，不然会抱该接口需要登录
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger**/**", "anon");
        filterChainDefinitionMap.put("/webjars/**", "anon");
        filterChainDefinitionMap.put("/v2/**", "anon");

        //性能监控
        filterChainDefinitionMap.put("/actuator/metrics/**", "anon");
        filterChainDefinitionMap.put("/actuator/env/**", "anon");
        filterChainDefinitionMap.put("/actuator/httptrace/**", "anon");
        filterChainDefinitionMap.put("/actuator/redis/**", "anon");
        filterChainDefinitionMap.put("/druid/**", "anon");

        // ----------------------------------------------- 系统业务相关 -----------------------------------------------
        //登录接口排除
        filterChainDefinitionMap.put("/login/checkCode", "anon"); // 图片验证码获取接口
        filterChainDefinitionMap.put("/login/loginByPwd", "anon"); // 账号密码登录
//
//        filterChainDefinitionMap.put("/sys/getCode", "anon");//前台获取验证码
//        filterChainDefinitionMap.put("/sys/loginByCaptcha", "anon");//前台验证码登录/注册接口
//
//        filterChainDefinitionMap.put("/sys/user/checkOnlyUser", "anon");//校验用户是否存在
//        filterChainDefinitionMap.put("/sys/user/register", "anon");//用户注册
//        filterChainDefinitionMap.put("/sys/user/querySysUser", "anon");//根据手机号获取用户信息
//        filterChainDefinitionMap.put("/sys/user/phoneVerification", "anon");//用户忘记密码验证手机号
//        filterChainDefinitionMap.put("/sys/user/passwordChange", "anon");//用户更改密码
//        filterChainDefinitionMap.put("/auth/2step-code", "anon");//登录验证码
//        filterChainDefinitionMap.put("/sys/common/view/**", "anon");//图片预览不限制token
//        filterChainDefinitionMap.put("/sys/common/download/**", "anon");//文件下载不限制token
//        filterChainDefinitionMap.put("/sys/common/pdf/**", "anon");//pdf预览
//        filterChainDefinitionMap.put("/generic/**", "anon");//pdf预览需要文件

        //测试接口排除
//        filterChainDefinitionMap.put("/SysUser/exportXls", "anon");

        //websocket排除
        filterChainDefinitionMap.put("/websocket/**", "anon");

        // 添加过滤器
        Map<String, Filter> filterMap = new HashMap<String, Filter>(1);
        filterMap.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        // <!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边
        filterChainDefinitionMap.put("/**", "jwt");

        shiroFilterFactoryBean.setUnauthorizedUrl("/401");
        shiroFilterFactoryBean.setLoginUrl("/401");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

}
