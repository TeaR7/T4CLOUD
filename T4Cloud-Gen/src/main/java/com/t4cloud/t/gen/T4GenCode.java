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
        config.setModuleName("support");
        //设置表前缀，可以为空
        config.setTablePrefix("");
        //modal表单样式
//        config.setDrawer(true);
        //是否生成feignClient
        config.setFeignClient(true);

        //是否为树结构，如果是树结构，需要有parentId这个字段
        config.setTree(false);

        //设置需要生成的表名,数组形式，可以多个
//        String[] tableNames = new String[]{"sys_dict","sys_dict_value"};
//        String[] tableNames = new String[]{"sys_user"};
//        String[] tableNames = new String[]{"sys_log"};
//        String[] tableNames = new String[]{"sys_dict"};
//        String[] tableNames = new String[]{"sys_dict_value"};
//        String[] tableNames = new String[]{"sys_mock"};
//        String[] tableNames = new String[]{"sys_role", "sys_role_permission"};
//        String[] tableNames = new String[]{"sys_user_role"};
//        String[] tableNames = new String[]{"sys_resource"};
//        String[] tableNames = new String[]{"sys_message_template"};

//        String[] tableNames = new String[]{"sys_role"};
//        String[] tableNames = new String[]{"example_common"};
        String[] tableNames = new String[]{"sup_message"};

        config.setTableName(tableNames);

        //开始生成
        T4GenUtil.gen(config);

    }

}