package com.t4cloud.t.third.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.t4cloud.t.base.annotation.AutoLog;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.feign.client.WechatUserClient;
import com.t4cloud.t.feign.dto.SysUserThirdDTO;
import com.t4cloud.t.third.entity.ThirdConfig;
import com.t4cloud.t.third.service.IThirdConfigService;
import com.t4cloud.t.third.util.WxUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * 微信用户登录 控制器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @since 2020-04-07
 */
@RestController
@AllArgsConstructor
@Slf4j
@Api(value = "微信用户操作接口", tags = "微信用户", position = 0)
@RequestMapping("/WeUser")
public class WeUserController implements WechatUserClient {

    @Autowired
    private IThirdConfigService thirdConfigService;


    /**
     * 微信登录
     */
    @Override
    @AutoLog(value = "微信第三方注册或登录(扫码回调)")
    @PostMapping("/oauth")
    @ApiOperation(position = 1, value = "微信第三方注册或登录", notes = "微信第三方注册或登录(公众号CODE回调）")
    public R<SysUserThirdDTO> userOauth(
            @ApiParam(required = true, name = "code", value = "返回的code值")
            @RequestParam(value = "code") String code
    ) {
        //登录方式
        Integer loginType = 1;
        log.info("进入授权回调,code:" + code + ",loginType:" + loginType);

        if (StrUtil.isBlank(code)) {
            return R.error("code不允许为空！");
        }
        // 获取租户的微信配置信息
        ThirdConfig thirdConfig = thirdConfigService.lambdaQuery()
                .eq(ThirdConfig::getCode, loginType)
                .eq(ThirdConfig::getStatus,1)
                .one();
        if (thirdConfig == null) {
            return R.error("无公众号API配置！");
        }

        //1.通过code换取openid以及access_token
        String response = WxUtils.getAccessToken(code, thirdConfig);
        JSONObject jsonObject = JSONUtil.parseObj(response);
        log.info("用户从微信端获取的信息:" + response);
        if (null != jsonObject.get("errcode")) {
            return R.error("微信扫描通过code换取openid以及access_token失败");
        }
        String openid = jsonObject.getStr("openid");
        String accessToken = jsonObject.getStr("access_token");
        String expires = jsonObject.getStr("expires_in");

        //2.通过access_token和openid获取用户信息
        String userInfomation = WxUtils.getUserInformation(accessToken, openid);
        log.info("从微信端获取通过access_token和openid获取用户信息:", userInfomation);
        JSONObject userInfo = JSONUtil.parseObj(userInfomation);
        if (null != userInfo.get("errcode")) {
            return R.error("微信扫描通过access_token和openid获取用户信息失败");
        }
        log.info("用户的信息userInfoObject:{}", userInfo);
        //唯一标识unionId
        String unionId = userInfo.getStr("unionid");
        if (StrUtil.isBlank(unionId)) {
            unionId = openid;
        }
        //头像
        String headimgurl = userInfo.getStr("headimgurl");
        if (headimgurl.startsWith("http:")) {
            headimgurl = headimgurl.replace("http:", "https:");
        }
        //昵称
        String nickname = userInfo.getStr("nickname");
        //表情替換
//        nickname = EmojiParser.removeAllEmojis(nickname);

        //性别
        String sex = userInfo.getStr("sex");

        //组装用户第三方信息对象
        SysUserThirdDTO userThird = new SysUserThirdDTO();

        userThird.setOutId(unionId);
        userThird.setAccessToken(accessToken);
        userThird.setExpress(new Date(System.currentTimeMillis() + Long.parseLong(expires)));

        userThird.setImg(headimgurl);
        userThird.setNickName(nickname);
        userThird.setGender(sex);
        userThird.setLoginType(loginType);

        return R.ok("微信用户鉴权成功", userThird);
    }

}
