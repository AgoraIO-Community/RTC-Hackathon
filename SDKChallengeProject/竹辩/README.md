# 作品介绍
辩论比赛在高校中一直是很火爆的一项活动。然而，由于每场线下比赛的名额有限以及成本相对高额，网络辩论已被越来越多辩手选择。
然而，网络辩论目前的解决方式都不是特别优秀，故开发竹辩系统以供网络辩论使用。

# 环境介绍
- 后端：
  - yesapi提供的serverless服务
  - 人脸识别后端koajs
  - mongoDB
- 前端：
  - vuexy(UI)
  - react
  - redux
- 声网：
  - rtc-ng
  - rtm

# 编译方式
1. 后端：

- 模型位于/yesapi_models | yesapi的导入模型需要vip权限（需付费）
- 修改/server.php 内22-24行，改为yesapi提供的接口网址与自己的apikey和secret

2. 人脸验证后端：

- 修改/debateFaceBackend/controllers/getEmotion.js:16行，将subscription_key修改为微软提供的人脸识别apikey
- 修改/debateFaceBackend/controllers/getEmotion.js:50行，UpToken 填写服务端获取的上传token
- 修改/debateFaceBackend/config.js 11-15行，填入MongoDB的对应数据，无需建表
- ```npm install```
- ``` npm start ```

2. 前端：
- 修改/src/views/pages/compShow/compShow.js:49行，将appId填为agora的appId。
- 将src/package.json 最后的   ```"testNeeded": "/"proxy/": /"https://puluter.cn/""``` 改为```"proxy":"你搭建yesapi中转后端的服务器地址"```
- 在src目录```npm install```
- 在src目录```npm start```
- 买一份vuexy的授权（测试不需要，如果要使用，则需要）https://themeforest.net/item/vuexy-vuejs-html-laravel-admin-dashboard-template/23328599
  - 将components/@vuexy 放入相同位置
  - 将assets文件 放入相同位置
  - vuexy源码我无权开源，如确需测试，请联系我获取测试build后版本

# 体验方式
提供体验账户：
- 用户名：agora
- 密码：testAGORA
- 访问：https://bamboo.puluter.cn
- 比赛房间密码为：12345

# 作者
于卓浩 - 重庆大学
王嘉维 - 伯明翰大学