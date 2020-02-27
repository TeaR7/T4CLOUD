package com.t4cloud.t.mock.service;


import cn.hutool.json.JSONObject;
import com.t4cloud.t.base.entity.dto.R;
import com.t4cloud.t.mock.entity.SysMock;
import com.t4cloud.t.base.service.T4Service;

/**
 * Mock数据 服务类
 *
 * <p>
 * --------------------
 *
 * @author T4Cloud
 * @since 2020-02-14 
 */
public interface ISysMockService extends T4Service<SysMock> {

    /**
     * TODO
     *
     * @param url mock的接口
     * @param method mock的接口调用方法
     * <p>
     * @return cn.hutool.json.JSONObject
     * --------------------
     * @author Qiming
     * @date 2020/2/22 17:49
     */
    R<JSONObject> getData(String url, String method);

}
