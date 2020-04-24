# RTC-Hackthon
该实例为盲人在线KTV、盲人会议进行全面服务，产品里面进行全面的无障碍案处理
这个开源示例项目演示了 Agora 音频 SDK，实现多人音频连麦直播。

在这个示例项目中包含了以下功能：

加入通话和离开通话；
主播和观众模式切换；
静音和解除静音；
切换扬声器和听筒；
运行环境
Android Studio 3.0 +
真实 Android 设备 (Nexus 5X 或者其它设备)
部分模拟器会存在功能缺失或者性能问题，所以推荐使用真机
运行示例程序
这个段落主要讲解了如何编译和运行实例程序。

创建Agora账号并获取AppId
在编译和启动实例程序前，您需要首先获取一个可用的App ID:

在agora.io创建一个开发者账号
前往后台页面，点击左部导航栏的 项目 > 项目列表 菜单
复制后台的 App ID 并备注，稍后启动应用时会用到它
在项目页面生成临时 Access Token (24小时内有效)并备注，注意生成的Token只能适用于对应的频道名。
将 AppID 填写进 “app/src/main/res/values/strings_config.xml”
<string name="private_app_id"><#YOUR APP ID#></string>
集成 Agora 视频 SDK
集成方式有以下两种：

通过JCenter集成：
在项目对应的模块的 app/build.gradle 文件的依赖属性中加入通过 JCenter 自动集成 Agora 视频 SDK 的地址：
implementation 'io.agora.rtc:full-sdk:3.0.0'
手动集成：
在 Agora.io SDK 下载 视频通话 + 直播 SDK 并解压，按以下对应关系将 libs 目录的内容复制到项目内。
SDK目录	项目目录
.jar file	/apps/libs folder
arm64-v8a folder	/app/src/main/jniLibs folder
x86 folder	/app/src/main/jniLibs folder
armeabi-v7a folder	/app/src/main/jniLibs folder
若需要需要使用C++头文件，可以在解压SDK后将其中的 libs / include 文件夹下的 * .h 复制到本项目的 app / src / main / cpp / agora 下。
启动应用程序
用 Android Studio 打开该项目，连上设备，编译并运行。

也可以使用 Gradle 直接编译运行。

联系我们
如果你遇到了困难，可以先参阅常见问题
如果你想了解更多官方示例，可以参考官方SDK示例
如果你想了解声网SDK在复杂场景下的应用，可以参考官方场景案例
如果你想了解声网的一些社区开发者维护的项目，可以查看社区
完整的 API 文档见 文档中心 3
若遇到问题需要开发者帮助，你可以到 开发者社区 提问
如果发现了示例代码的 bug，欢迎提交 issue
代码许可
The MIT License (MIT).

Github传送门 
