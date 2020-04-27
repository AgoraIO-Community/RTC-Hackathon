# QiMing  Open Live Voice Only for Android  and  Miniprogram

该项目实现为盲人提供全面无障碍语音周边服务，包括APP和小程序，后期也会集成启明智能眼镜
这个开源示例项目演示了如何快速集成 Agora 音频 SDK，实现多人音频连麦直播。

## 产品进行全面无障碍处理，如果作为盲人角度体验现在手机辅助功能里面打开屏幕阅读的功能，如果没有读屏软件，可以点击进行下载免费读屏软件 [Talkback](https://sj.qq.com/myapp/detail.htm?apkName=com.google.android.marvin.talkback)
在启明新世界项目项目中包含了以下功能：

- 非常全面的无障碍处理优化，包括APP和小程序模块
- 加入通话和离开通话；
- 主播和观众模式切换；
- 静音和解除静音；
- 切换扬声器和听筒；
- 10个人同时连麦
- 10个人同时在线KTV


## 安卓APP运行环境
- Android Studio 3.0 +
- 真实 Android 设备 (Nexus 5X 或者其它设备)
- 部分模拟器会存在功能缺失或者性能问题，所以推荐使用真机

## 运行示例程序

这个段落主要讲解了如何编译和运行实例程序。

### 创建Agora账号并获取AppId

在编译和启动实例程序前，您需要首先获取一个可用的App ID:
1. 在[agora.io](https://dashboard.agora.io/signin/)创建一个开发者账号
2. 前往后台页面，点击左部导航栏的 **项目 > 项目列表** 菜单
3. 复制后台的 **App ID** 并备注，稍后启动应用时会用到它
4. 在项目页面生成临时 **Access Token** (24小时内有效)并备注，注意生成的Token只能适用于对应的频道名。

5. 将 AppID 填写进 "app/src/main/res/values/strings_config.xml"
  ```
  <string name="private_app_id"><#YOUR APP ID#></string>
  ```

### 集成 Agora 视频 SDK

集成方式有以下两种：
  - 通过JCenter集成：
    - 在项目对应的模块的 `app/build.gradle` 文件的依赖属性中加入通过 JCenter 自动集成 Agora 视频 SDK 的地址：
      ```
      implementation 'io.agora.rtc:full-sdk:3.0.0'
      ```
  - 手动集成：
    - 在 [Agora.io SDK](https://www.agora.io/cn/download/) 下载 **视频通话 + 直播 SDK**并解压，按以下对应关系将 **libs** 目录的内容复制到项目内。
      
      SDK目录|项目目录
      ---|---
      .jar file|**/apps/libs** folder
      **arm64-v8a** folder|**/app/src/main/jniLibs** folder
      **x86** folder|**/app/src/main/jniLibs** folder
      **armeabi-v7a** folder|**/app/src/main/jniLibs** folder
    - 若需要需要使用C++头文件，可以在解压SDK后将其中的 **libs**/**include** 文件夹下的 ***.h** 复制到本项目的 **app**/**src**/**main**/**cpp**/**agora** 下。


### 启动应用程序

用 Android Studio 打开该项目，连上设备，编译并运行。

也可以使用 `Gradle` 直接编译运行。

## 联系我们
- 团队：启明新世界
- 邮箱：jnzhou90@163.com
- 如果你遇到了困难，可以先参阅[常见问题](https://docs.agora.io/cn/faq)
- 如果你想了解更多官方示例，可以参考[官方SDK示例](https://github.com/AgoraIO)
- 如果你想了解声网SDK在复杂场景下的应用，可以参考[官方场景案例](https://github.com/AgoraIO-usecase)
- 如果你想了解声网的一些社区开发者维护的项目，可以查看[社区](https://github.com/AgoraIO-Community)
- 完整的 API 文档见 [文档中心](https://docs.agora.io/cn/)
- 若遇到问题需要开发者帮助，你可以到 [开发者社区](https://rtcdeveloper.com/) 提问
- 如果发现了示例代码的 bug，欢迎提交 [issue](https://github.com/AgoraIO/Basic-Audio-Broadcasting/issues)

## 启明直播小程序模块


启明小程序 基于 Agora Miniapp SDK 2.4.1 开发，能帮助开发者在微信小程序中实现视频通话及互动直播等功能。

本页演示如下内容：

* 集成 Agora Miniapp SDK
* 加入频道
* 推流
* 订阅远端流
* 离开频道

## 准备开发环境

1. 请确保本地已安装微信开发者工具
2. 请确保有一个支持 **live-pusher** 和 **live-player** 组件的微信公众平台账号。只有特定行业的认证企业账号才可使用这两个组件。详情请[点击这里](https://developers.weixin.qq.com/miniprogram/dev/component/live-player.html)
3. 请确保在微信公众平台账号的开发设置中，给予以下域名请求权限：

 * https://miniapp.agoraio.cn
 * https://uni-webcollector.agora.io
 * wss://miniapp.agoraio.cn

4. 若使用的是1.1.2 BETA后的版本，则需要额外添加以下域名
 * https://miniapp-1.agoraio.cn
 * https://miniapp-2.agoraio.cn
 * https://miniapp-3.agoraio.cn
 * https://miniapp-4.agoraio.cn

## 运行示例程序
 
1. 在 [Agora.io](http://dashboard.agora.io/signin/) 注册账号，并创建自己的测试项目，获取 App ID。如需获取 Token 或 Channel Key，请启用 App Certificate
2. 下载本页示例程序
3. 打开 *utils* 文件夹，在 *config.js* 文件中填入获取到的 App ID：

    	const APPID = 'abcdefg'
    	
4. 下载 [Agora Miniapp SDK](https://docs.agora.io/cn/Agora%20Platform/downloads)，并将 SDK 重新命名为 “mini-app-sdk-production.js"
5. 将更名后的 "mini-app-sdk-production.js" 文件保存在本示例程序的 *lib* 文件夹下
6. 启动微信开发者工具并导入该示例程序
7. 输入频道名，加入频道。邀请你的朋友加入同一个频道，就可以开始视频互通了。

**声网的 Native SDK 可以直接与小程序互通。**

## 关于 Token/Dynamic Key

如果启用了 App Certificate，还需要在服务端生成 Token 或 Dynamic Key 用于鉴权。将生成的 Token 或 Dynamic Key 填入如下方法中：

    	//...
    	client.join(<your key/access token here>, channel, uid, () => {
    	//...
    	
关于如何生成 Token 或 Dynamic Key 详见 [Token](https://docs.agora.io/cn/2.2/product/Video/Agora%20Basics/key_native?platform=Android) 或 [Dynamic Key](https://docs.agora.io/cn/2.2/product/Video/Agora%20Basics/key_web?platform=Web)。

## 联系我们

* 完整的 API 文档请参考 [开发者中心](http://docs.agora.io/cn/)
* 如果在集成中遇到问题，你可以到 [开发者社区](https://dev.agora.io/cn/) 提问
* 如果有售前咨询问题，可以拨打 400 632 6626，或加入官方 QQ 群 12742516
* 如果需要售后技术支持，你可以在 [Agora Dashboard](https://dashboard.agora.io/signin?next=%2F) 提交工单
* 如果发现该示例代码有问题，欢迎提交至 [issue](https://github.com/AgoraIO/Agora-Android-Tutorial-1to1/issues)

## 代码许可
The MIT License (MIT).
