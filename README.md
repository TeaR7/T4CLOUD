![T4CLOUD](https://git.t4cloud.com/img/favicon.png "T4CLOUD")


# T4CLOUD  

Spring Cloud 快速开发脚手架项目，同时支持单体Boot快速部署，也支持复杂模式的Cloud开发。专业代码生成器一键生成前后端代码，无需考虑支撑功能，拥有方便的鉴权及权限体系，自动化的日志记录系统，让您更好的专注于业务开发即可。

===============

当前最新版本： 0.0.5（发布日期：2020.04.03）

> 源码获取
 + Github：   https://github.com/TeaR7/T4CLOUD
 + Gitee：    https://gitee.com/TRCloud/T4CLOUD
 
```
【当前进度更新】

## RELEASE-VERSION:0.0.5
【2020.04.03】

服务端

+ -a 集成ribbon
+ -a 增加接口防重校验
+ -a 增加租户ID解析器
+ -a 增加消息中心 && 模板配置 && 支持批量
+ -a 实现邮件消息
+ -a 访问量统计
+ -a 用户量统计
+ -a 增加开发示例
+ -a 增加导出对图片资源的处理
+ -u 用户详情角色获取&&编辑
+ -u Feign client超时配置 && Feign统一异常处理
+ -u 生成器增加抽屉式的modal
+ -u 优化定时任务的日志记录
+ -u 增加监控属性
+ -u 增强生成器对时间累心的优化处理
+ -u 增强生成器对长文本的处理
+ -u 字典的重构
+ -f 树结构删除修正
+ -f 角色权限保存异常
+ -f 修正feign client调用异常
+ -r 重构高级查询接口

前台

+ -a 角色管理、授权、用户完成
+ -a 提供公共验重接口
+ -a 实时监控（服务器、redis、容器信息、请求）
+ -a modal提供刷新加载
+ -a 前端权限控制完善
+ -a 提供Select选择器
+ -a 增加RSA工具类
+ -a 支持外链打开
+ -a 增加高级查询组件
+ -a 日志监控功能
+ -a 首页数据图表完成
+ -a 图片组件（客家仔本地资源和网络资源）
+ -u Modal提供可选的浮动样式
+ -u 用户头像展示修正


【计划】

服务端
+ 图片导入
+ 增加部门功能
+ 增加职位功能
+ 集成flowable工作流
+ 查询接口支持多个sort参数
+ 请求校验的时候，可配置需要放过的接口
+ 高级查询支持 like && eq 区分

前端
+ 资源上传组件
+ 表格sort功能
+ label只能resize调整宽度
+ vue config功能
+ 高级查询支持 like && eq 区分
+ 时间组件优化手机适配

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

- 工作流框架：Flowable

- 定时任务框架：XXL-Job

- 日志打印：logback

- 其他：Jackson & Easypoi & Swagger & lombok等

  

【==前端==】

- 基础框架： VUE

- 脚手架：VUE-Cli

- 路由：VUE-Router

- 数据状态：VUEX

- UI组件：ElementUI

- 通讯：Axios

- 图表：Echarts

- 多语言：Vue-i18n

- 扩展滚动：Swiper

- 加密：Jsencrypt


## 开发环境

- 语言： Java-8 & Nodejs-10

- IDE： IDEA & WebStorm & VsCode

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


