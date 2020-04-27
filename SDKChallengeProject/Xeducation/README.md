# Xeducation

- 项目名称：Xeducation
- 开发者：Harry Kwok，rm -rf /*，四叶千寻，WJY，枍
- 项目概述：Xeducation是一套一站式云课堂解决方案

## 0.预览

[![](img/product.png)](https://www.bilibili.com/video/BV1Cz411B7sq)

您可以点击上图或者点击文末的链接观看一分钟介绍视频！

## 1.项目介绍

Xeducation是一套一站式云课堂解决方案，主要面向学生等有在线学习需求的群体。Xeducation融合了课堂直播、课程回放、资料下载等基础功能，并增加了全新和直观的强大功能，推出了搭配Markdown笔记、在线教材浏览、课堂交互的课堂新模式，同时，结合移动端App、Web端浏览信息和人像识别算法的综合信息，我们可以为您提供详细的专注度检测报告。

## 2.解决方案

Xeducation在现有会议软件的基础上，引入了课堂互动和课堂笔记的新方法，添加了人脸识别算法支持，新增了专注度检测，为用户带来全新的云课堂学习方式。

#### 通知

首页放置课程表、通知栏、Deadline栏，使接下来的学习安排一目了然，并为用户提供到时自动提醒上课服务，让上课迟到不再发生。

#### 课程内容

区别于传统教育资源平台，我们采用课程资料高密度排布，免去多级菜单跳转困扰，对于视频回放，Xeducation支持时间戳，用户可通过点击上课发言，快速定位视频位置。

#### 直播

重新设计的页面排布能容纳更多的课堂拓展功能，我们将传统软件的聊天发言区优化为弹幕形式，并支持自定义透明度调节与隐藏，在直观便捷之外，得以在原先的右侧开发更多新的功能，比如支持Markdown格式的笔记页、实时查询的浏览页、以及小功能区的多元化类课堂工具，满足签到、测试等自定义用户需求。

#### 专注度

结合手机端App检测手机使用信息、Web端浏览器信息、摄像头对人头朝向的信息综合判断当前专注度，Xeducation将为用户生成每日专注度报告反馈，包含时间与课程之间的横纵向比对，为您管理您的专注时间。

## 3.技术实现

Xeducation的主体为使用Vue搭建的Web应用，辅以基于Weex原生应用，后端采用了Django。在专注度检测上，除了手机端和PC端的使用情况的数据外，我们使用了Deep Learning 及 Computer Vision的相关技术，对采集的画面进行分析。

### 应用部分

团队在了解赛题后就使用Axure RP完成了Web端的原型设计，基于Vue2进行了开发，页面除少部分组件使用Element组件外，布局与组件均围绕“高效、清晰”的原则设计与自主搭建，开发中主要用到的技术栈有：Vue2、Vue-router、Axios、Less、ECharts、Element等。Mobile端应用使用Weex搭建，使用了Weex-UI。

#### 实时通信

直播服务采用了Agora（声网）提供的SDK，课堂弹幕与直播回放功能拟采用Agora的实时消息模块和直播实时录制服务，这一与后端交接的部分正在进行测试。

### 专注度算法部分

此部分通过采集到的用户画面和Web与Mobile端的用户数据分析专注度，通过使用OpenCV与TensorFlow来达到专注度分析的目的，基本流程如下：

1. 面部识别 - 使用OpenCV中DNN模块
2. 面部标记 - 使用TensorFlow中68点估计
3. 姿态估计 - PnP算法
4. 稳定化 - Kalman filter
5. 专注度分析

## 4.团队介绍及进度

团队名称：Femto X

| 名称       | 邮箱                    |
| ---------- | ----------------------- |
| Harry Kwok | g1135480626@outlook.com |
| rm -rf /*  | me@shenjunda.com        |
| 四叶千寻   | nuolianqikaaa@126.com   |
| WJY        | 1178603548@qq.com       |
| 枍         | 2745941940@qq.com       |

疫情期间的远程授课模式，虽然实现了基本的信息传递，但在线课堂在设计与功能上的缺失也给学习带来不便，无人督导的环境也对自制力提出了要求，作为学子的我们深有体会，这也是促使我们五个学子开发这款软件的重要动力。

在知晓这次比赛的主题后，我们立即以此为主题，进行了多次的会议讨论，经历需求分析、竞品调研、UI设计、产品开发与算法验证等环节，完成了宣传视频的制作、宣传网页的搭建和在线Demo的部署等工作。

目前，该项目的整体功能设计与用户界面构建已经完成，专注度算法方面也完成了验证。下一步，我们将进一步整合声网（Agora）提供的优质SDK资源与完善后端服务，实现所有设计目标。

您可以通过邮箱或QQ（1135480626）联系Harry Kwok。

## 5.编译与运行指南

### web应用

#### 环境需求

- Node.js
- Vue CLI

#### 安装依赖项

```
npm install
```

#### 运行项目

```
npm run serve
```

### Mobile应用

#### 环境需求

- Node.js
- Weex CLI

#### 安装依赖项

```
npm install
```

#### 运行项目

```
npm start
```

您需要在您的手机上下载Weex Playground app，并确保您的手机与运行该项目的PC处于同一局域网下，使用Weex Playground app扫码即可体验。

请确保您的网络配置中关闭了“对此网络使用随机地址”的选项，否则app会显示网络错误。如果仍然存在问题，请在控制面板的“网络连接”中禁用虚拟网络适配器，再运行项目。

如果出现端口报错的情况，通常是因为运行的本地端口已经被占用，你可以关闭占用改端口的进程或者修改配置文件中的端口解决该问题。

### 专注度算法

#### 环境需求

- OpenCV
- TensorFlow
- Python 3
- 本项目已在Ubuntu 18.04完成测试

#### 安装

```bash
git clone https://github.com/MercurialJD/Concentration-Detection.git
```

#### 使用

本项目可以识别视频文件或摄像头输入数据。在无参数或有摄像头参数的情况下将自动调用摄像头；如指明视频文件，将对视频文件进行分析。

#### 视频模式

对于可被OpenCV识别的视频格式:

```bash
python3 concentration_estimation.py --video /path/to/your/video.mp4
```

#### 摄像头模式

调用默认摄像头：

```
python3 concentration_estimation.py
```

或指定摄像头:

```bash
python3 concentration_estimation.py --cam 0
```

#### 输出设置

请根据需求对`concentration_estimation.py`作相应调整：

- 如需获取处理后的视频，请将以下代码前的`#`去除：

  ```
  # fourcc = cv2.VideoWriter_fourcc(*'mp4v')
  # out = cv2.VideoWriter('output.mp4', fourcc, 20.0, (width, height))
  ...
  # cv2.putText(frame, "FACING", (height//50, width//50), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 0), 1)
  ...
  # cv2.putText(frame, "UNFACED", (height//50, width//50), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 0), 1)
  ...
  # out.write(frame)
  ...
  # out.release()
  ```

- 如需实时预览，请将以下代码前的`#`去除：

  ```
  # cv2.imshow("Preview", frame)
  ```

- 如需自定义面部识别框，请修改：

  ```
  pose_estimator.draw_annotation_box(...)
  ```

- 如需自定义姿态轴，请修改：

  ```
  pose_estimator.draw_axis(...)
  ```

## 6.更多信息

方案介绍页：https://www.xeducation.top/

Web端在线体验：https://app.xeducation.top/

Mobile端在线体验：https://mobile.xeducation.top/

一分钟介绍视频：https://www.bilibili.com/video/BV1Cz411B7sq

## 鸣谢

- [**Fine-Grained Head Pose Estimation Without Keypoints (CVPR 2018)**](https://arxiv.org/abs/1710.00925) by Ruiz, Nataniel and Chong, Eunji and Rehg, James M.
- **Head pose estimation by TensorFlow and OpenCV** by [Yin Guobing]