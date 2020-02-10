package com.t4cloud.t.gen;

import com.t4cloud.t.base.config.GenConfig;
import com.t4cloud.t.base.utils.T4GenUtil;

/**
 * 单表代码生成器
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/2/7 17:29
 */
public class T4GenCode {

    public static void main(String[] args) {

        //配置参数
        GenConfig config = new GenConfig();
        //设置模块的包名，可以为空
        config.setModuleName("service");
        //设置表前缀，可以为空
        config.setTablePrefix("");
        //设置需要生成的表名,数组形式，可以多个
//        String[] tableNames = new String[]{"sys_dict","sys_dict_value"};
        String[] tableNames = new String[]{"sys_user"};
        config.setTableName(tableNames);

        //开始生成
        T4GenUtil.gen(config);

    }

}