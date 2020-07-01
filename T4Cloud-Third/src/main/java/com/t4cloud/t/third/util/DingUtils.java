package com.t4cloud.t.third.util;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.t4cloud.t.third.entity.ThirdConfig;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * 钉钉工具类
 * <p>
 * 处理钉钉相关接口
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/06/28 16:35
 */
@Slf4j
public class DingUtils {


    // ----------------------------------------------- 系统相关 -----------------------------------------------

    /**
     * 获取系统access-token
     *
     * @param thirdConfig 配置信息
     *                    <p>
     * @return java.lang.String
     * --------------------
     * @author TeaR
     * @date 2020/6/28 16:36
     */
    public static String getAccessToken(ThirdConfig thirdConfig) throws ApiException {
        DefaultDingTalkClient tokenClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey(thirdConfig.getAppId());
        request.setAppsecret(thirdConfig.getSecret());
        request.setHttpMethod("GET");
        OapiGettokenResponse response = tokenClient.execute(request);
        if (response.getErrcode() != 0) {
            throw new ApiException(response.getErrmsg());
        }
        return response.getAccessToken();
    }


    // ----------------------------------------------- 用户相关 -----------------------------------------------

    /**
     * 用户鉴权
     *
     * @param thirdConfig 配置信息
     * @param code        用户临时授权码
     *                    <p>
     * @return com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse.UserInfo
     * --------------------
     * @author TeaR
     * @date 2020/6/28 16:51
     */
    public static OapiSnsGetuserinfoBycodeResponse.UserInfo getUseroauth(ThirdConfig thirdConfig, String code) throws ApiException {
        DefaultDingTalkClient oauthClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/sns/getuserinfo_bycode");
        OapiSnsGetuserinfoBycodeRequest req = new OapiSnsGetuserinfoBycodeRequest();
        req.setTmpAuthCode(code);
        OapiSnsGetuserinfoBycodeResponse response = oauthClient.execute(req, thirdConfig.getAppId(), thirdConfig.getSecret());
        if (response.getErrcode() != 0) {
            throw new ApiException(response.getErrmsg());
        }
        return response.getUserInfo();

    }


    /**
     * 通过用户unionId，获取用户userId
     *
     * @param accessToken 系统token
     * @param unionId     用户unionId
     *                    <p>
     * @return java.lang.String
     * --------------------
     * @author TeaR
     * @date 2020/6/28 16:42
     */
    public static String getUserIdByUnionId(String accessToken, String unionId) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getUseridByUnionid");
        OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
        request.setUnionid(unionId);
        request.setHttpMethod("GET");
        OapiUserGetUseridByUnionidResponse response = client.execute(request, accessToken);
        if (response.getErrcode() != 0) {
            throw new ApiException(response.getErrmsg());
        }
        return response.getUserid();
    }

    /**
     * 获取用户详情
     *
     * @param accessToken 系统token
     * @param userId      钉钉内部userID
     *                    <p>
     * @return com.dingtalk.api.response.OapiUserGetResponse
     * --------------------
     * @author TeaR
     * @date 2020/6/28 16:45
     */
    public static OapiUserGetResponse getUserInfo(String accessToken, String userId) throws ApiException {
        DingTalkClient userInfoClient = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid(userId);
        request.setHttpMethod("GET");
        OapiUserGetResponse response = userInfoClient.execute(request, accessToken);
        if (response.getErrcode() != 0) {
            throw new ApiException(response.getErrmsg());
        }
        return response;
    }


    // ----------------------------------------------- 部门相关 -----------------------------------------------


}
