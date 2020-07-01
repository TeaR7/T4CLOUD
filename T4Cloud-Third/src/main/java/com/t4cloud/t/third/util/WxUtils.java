package com.t4cloud.t.third.util;

import cn.hutool.http.HttpUtil;
import com.t4cloud.t.third.entity.ThirdConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * WxUtils
 * <p>
 * 处理微信相关接口
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2019/10/8 11:54
 */
@Slf4j
public class WxUtils {


    /**
     * 处理微信用户登录
     *
     * @param code   转跳携带的code
     * @param config 微信的配置信息
     *               <p>
     * @return java.lang.String
     * --------------------
     * @author TeaR
     * @date 2019/10/8 12:06
     */
    public static String getAccessToken(String code, ThirdConfig config) {
        String url = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appId=%s&secret=%s&code=%s&grant_type=authorization_code", config.getAppId(), config.getSecret(), code);
        return HttpUtil.get(url);
    }

    /**
     * 通过用户授权，获取用户微信信息
     *
     * @param accessToken 用户授权码
     * @param openId      用户ID
     *                    <p>
     * @return java.lang.String
     * --------------------
     * @author TeaR
     * @date 2019/10/8 12:11
     */
    public static String getUserInformation(String accessToken, String openId) {
        String url = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s", accessToken, openId);
        return HttpUtil.get(url);
    }

}
