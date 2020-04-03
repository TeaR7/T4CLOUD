package com.t4cloud.t.mock.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.base.service.impl.T4ServiceImpl;
import com.t4cloud.t.mock.entity.SysMock;
import com.t4cloud.t.mock.mapper.SysMockMapper;
import com.t4cloud.t.mock.service.ISysMockService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Mock数据 服务实现类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-14
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysMockServiceImpl extends T4ServiceImpl<SysMockMapper, SysMock> implements ISysMockService {

    @Override
    public R<JSONObject> getData(String url, String method) {
        //获取账号密码,查看是否有效账户
        SysMock one = lambdaQuery().eq(SysMock::getMethod, method).eq(SysMock::getUrl, url).one();
        if (one == null) {
            return R.error("该url的mock数据不存在，请在后台添加。");
        }
        String returnJsonData = one.getData();
        JSONObject jsonObject = null;
        jsonObject = JSONUtil.parseObj(returnJsonData);
        return R.ok("请求成功", jsonObject);
    }
}
