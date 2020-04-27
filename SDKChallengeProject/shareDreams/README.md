# ShareDreams(享梦)

> 我们这个创意来自于美国达人秀（American's got Talent）这档节目，亮相的表演者通过多样化的表演形式展现了美国文化的核心所在。在我们看来，我们中国人比较含蓄，不愿去分享自己的梦想，我们希望能够提供一个平台，去让那些为梦想不断努力的人们能够感受到一丝温暖，能够得到其他追梦人的不断支持，在这个平台里，没有质疑、嘲讽、恶意，有的只是相互鼓励，相互扶持。同样，人们也能在这个平台里看到许多追梦人分享自己为梦想不断努力的励志故事。

**在线体验地址：**   [https://share.leopord.cn/](https://share.leopord.cn/)

### 团队介绍
**团队名称：** leo
**团队成员：** 
 刘希鹏（队长），负责项目的代码开发工作，微信：14718006289
 刘洋，负责项目的设计工作，微信：yang875714853

### 使用的SDK
- 语音通话/音频互动直播 SDK
- 视频通话/视频互动直播 SDK
- 实时消息 SDK
- 云录制


### 主要功能
- 注册：用户通过邮箱获取验证码进行注册。
- 视频录制功能：用户可以直接通过web端进行录制视频。
- 视频上传功能：用户也可以通过上传自己录制的视频（mp4格式）
- 直播功能：用户可以在web端进行在线直播展示自己的才能。
- 语音通话：用户在点击聊天进入聊天房间后可以与聊天对象进行语音通话。
- 视频通话：用户在点击聊天进入聊天房间后也可以与聊天对象进行视频通话。

### 技术实现
web端采用了Vue全家桶+ElementUI实现。
使用xgplayer以及xgplayer-hls.js库实现对不同格式的视频进行播放。

后端则是使用 express框架 + mongoDB + redis ，使用pm2对进程进行管理。

### 图片展示
主页
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200423220014453.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyODgwNzE0,size_16,color_FFFFFF,t_70)

首页
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200423221430794.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyODgwNzE0,size_16,color_FFFFFF,t_70)

注册页面：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200423215402538.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyODgwNzE0,size_16,color_FFFFFF,t_70)

录制页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200423220135353.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyODgwNzE0,size_16,color_FFFFFF,t_70)

开启直播页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200423220325218.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyODgwNzE0,size_16,color_FFFFFF,t_70)

语音通话页面
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020042322130382.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQyODgwNzE0,size_16,color_FFFFFF,t_70)


### 具体使用
**需具备node环境以及vue-cli脚手架工具**

下载文件到本地后，

进入文件根目录执行下方命令进行依赖文件的安装
```bash
npm install  #安装对应依赖文件
```

安装完成后，执行
```bash
npm run dev 
```
在浏览器中访问 [localhost:8080](http://localhost:8080) 即可。

项目中的API目前均可使用。