package com.t4cloud.t.common.constant;

/**
 * 系统通用常量
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/1/15 13:04
 */
public interface ResultConstant {


    /**
     * 通用服务异常
     *
     * @param TODO
     * <p>
     * @return --------------------
     * @author TeaR
     * @date 2020/1/15 13:05
     */
    public static final Integer INTERNAL_SERVER_ERROR = 500;

    /**
     * HTTP/1.0 - RFC 1945
     * 通用服务正常
     * <p>
     * --------------------
     *
     * @author TeaR
     * @date 2020/1/15 13:06
     */
    public static final Integer OK = 200;

    /**
     * 访问权限认证未通过
     */
    public static final Integer NO_AUTHZ = 401;


    /**
     * token参数名
     */
    public static final String X_ACCESS_TOKEN = "X_ACCESS_TOKEN";


}
