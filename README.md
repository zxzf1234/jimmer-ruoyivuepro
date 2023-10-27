# jimmer-ruoyivuepro

#### 介绍
基于jimmer、ruoyi-vue-pro的前后端分离脚手架

#### 软件架构
基于ruoyi-vue-pro改造的前后端分离脚手架，后端将MyBatis Plus替换成了jimmer，数据库暂时只支持mysql
后端架构
1. [ruoyi-vue-pro](https://doc.iocoder.cn/)
2. [jimmer](https://babyfish-ct.github.io/jimmer/zh/)
3. flyway

前端架构
1. [ruoyi-vue-pro](https://doc.iocoder.cn/)
2. [pure-admin](https://yiming_chang.gitee.io/pure-admin-doc/)
3. [vue3-context-menu](https://github.com/imengyu/vue3-context-menu)


#### 安装教程

1.  安装参考ruoyi-vue-pro 开发指南，可以省略的步骤是不用执行初始化sql

#### 使用说明

1.  和ruoyi-vue-pro相比，移除了租户管理、工作流程、支付流程、微信公众号等功能
2.  增加了数据库表、接口模块、系统接口等功能，可以直接在前端维护表和接口，直接生成升级sql、model、repository、service、controller、vo、convert代码，提高后端开发效率
3.  前端借鉴了pure-admin，基于pure-table重新封装了table，并且支持右键菜单

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


