package com.t4cloud.t.gen;

import com.t4cloud.t.base.config.GenModuleConfig;
import com.t4cloud.t.base.utils.T4GenUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * T4GenModule
 * <p>
 * 快速生成新的模块
 * <p>
 * ---------------------
 *
 * @author TeaR
 * @date 2020/4/4 20:20
 */
@Slf4j
public class T4GenModule {

    public static void main(String[] args) {

        //配置参数
        GenModuleConfig config = new GenModuleConfig();

        //配置项目名
        config.setArtifactId("T4Cloud-Testing");
        //填写描述 可为空
        config.setDescription("T4Cloud 测试相关模块");

        T4GenUtil.gen(config);

        log.info("pom文件，在modules标签中引入" + config.getArtifactId() + "模块即可");

    }

}
