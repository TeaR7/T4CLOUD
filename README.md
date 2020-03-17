![T4CLOUD](https://git.t4cloud.com/img/favicon.png "T4CLOUD")


# T4CLOUD  

Spring Cloud 快速开发脚手架项目，同时支持单体BOOT快速部署，也支持复杂模式的CLOUD开发。专业代码生成器一键生成前后端代码，无需考虑支撑功能，拥有方便的鉴权及权限体系，自动化的日志记录系统，让您更好的专注于业务开发即可。

===============

当前最新版本： 0.0.4（发布日期：2020.03.16）

> 源码获取
 + Github：   https://github.com/TeaR7/T4CLOUD
 + Gitee：    https://gitee.com/TRCloud/T4CLOUD
 
```
【当前进度更新】

【2020.03.16】
后台
-a 提供feign解决方案，并集成ribbon提供负载均衡
-a 字典组件功能，并支持缓存
-a 增加RSA工具，实现基于注解的自动解密接口参数（支持param和body）
-a 增加基础邮件推送功能
-a 标准查询接口支持字典相关高级查询
-u 生成器对feign的支持
-u 生成器对vue-modal的支持
-u 生成器对vue-list(tree)的支持
-u 优化重复属性校验方法
-f 修复自定义异常入库丢失的问题
-f 修复刷新token前端无法获取的问题
-f 修复redis监控数据的异常

前台
-a log工具类
-a 新增角色验证属性v-role和方法hasRole
-a 基础表格基础按钮组，提供刷新、导入、导出（支持自定义列和自定义行）
-a list增加排序参数
-a redis监控完成 && 服务器信息监控完成
-a 新增图片选择控件 && 字典组件完成
-a 新增角色管理基础页面 && 字典和字典详情管理界面
-u modal基础方法抽离
-u 权限校验支持数组
-u 基础表格优化，支持自定义列
-u 搜索组件支持字典搜索
-u token自动刷新
-u 操作按钮批量操作列表也支持扩展
-u 基础表格slot插槽实现，表头和内容都允许以slot插槽的方式实现
-u 用户编辑重复校验完善
-f 菜单编辑树状选择框异常修复
-f 刷新出现的横向白框修复

其他

【计划】

1.完成权限管理功能（授权/角色）
2.系统运维监控功能（tomcat/jvm/request）
3.字典管理功能
4.邮件模板管理&邮件推送

```


## 技术架构

【==服务端==】

- 基础框架：Spring Boot & Spring Cloud & Spring Cloud Alibaba

- 网关：Spring Cloud Gateway

- 注册 & 配置中心： Alibaba Nacos 

- 服务调用：Feign & Ribbon & Sentinel

- 持久层框架：Mybatis-plus & Mybatis

- 安全框架：Apache Shiro & Jwt

- 数据库连接池：Druid

- 缓存框架：Redis

- 定时任务框架：XXL-Job

- 日志打印：logback

- 其他：Jackson & Easypoi & Swagger & lombok等

  

【==前端==】

1. 基础框架： VUE
2. 数据状态：VUEX
3. UI组件：Element
4. 通讯：Axios



## 开发环境

- 语言： Java 8

- IDE： IDEA & WebStorm

- 依赖管理： Maven

- 数据库： MySQL5.7

- 缓存： Redis

- 注册 & 配置中心： Nacos

- 消息队列： Rocket-MQ


## 技术文档


- 在线演示 ：  [https://cloud.t4cloud.com](https://cloud.t4cloud.com)

- 在线文档：  [http://doc.t4cloud.com](http://doc.t4cloud.com)

- 常见问题：  暂无整理

- 问题反馈：   [zqr.it@t4cloud.com](zqr.it@t4cloud.com)

- Element：  [Element-ui文档](https://element.eleme.cn/#/zh-CN/component/installation)

- Mybatis-Plus：  [Mybatis-Plus](https://mp.baomidou.com/)

## 代码生成器

> 功能说明：   简单易用一键生成基础代码，提供Cloud和Boot两种模式（包括：controller、service、dao、mapper、entity、feign client、dto、vue）

```

在code.properties中按提示配置数据库相关信息，然后运行T4CodeGen的main方法即可

```

## 项目规划图

![T4CLOUD](https://ccnu-mooc.oss-cn-shanghai.aliyuncs.com/TeaR/T4CLOUD%E5%BE%AE%E6%9C%8D%E5%8A%A1%E8%84%9A%E6%89%8B%E6%9E%B6.png "T4CLOUD")

> 蓝色节点为已完成，黄色节点为正在进行，其他节点为已规划计划


## 作者

感谢几位伙伴共同努力

> + [yuxiuyu<18896591232@163.com>](mailto:18896591232@163.com)
> + [启明<lindagewu@foxmail.com>](mailto:lindagewu@foxmail.com)
> + [黄焖小鸡<18702114679@163.com>](mailto:18702114679@163.com)
> + [TeaR<zqr.it@t4cloud.com>](mailto:zqr.it@t4cloud.com)

## 写在最后
> + 本项目由现有商业项目中抽离并经过重新梳理设计并完成，欢迎大家入群指正我的错误或提出开发意见，任何想法都可以
> + 有朋友加入一起完善就更好了，给各自的简历加一些重量！
> + 适合用作个人项目，个人私活，中小型公司项目的快速开发
> + [zqr.it@t4cloud.com](zqr.it@t4cloud.com)


