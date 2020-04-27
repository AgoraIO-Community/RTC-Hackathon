# Blog_Connect_Service DEMO

#### 介绍
该新项目demo主要是为了解决许多新手程序员看不懂网上的文本教程，需要博主上传教学课程甚至实时视频教学的问题所开发的。
团队：欧阳白唯  联系电话：13689036856 QQ:630155515

Blog_Connect_Service的主要功能是提供上传教学视频，生成静态页面，创建/加入房间实时教学的功能

#### 在线预览
<a href="https://bvblog.maptoface.com/699560023210065920/single-video.html">web demo</a>

#### 使用的SDK
agora-rtc-sdk（web版声网sdk）<br>
agora-rtm-sdk（web版声网实时消息sdk）<br>
white-web-sdk（netless官方白板sdk）<br>

#### 所用技术
Java <br>
SpringBoot<br>
freemarker<br>

#### 相关展示
改项目是为适应不同项目，提供单一功能的接口项目。理想状态分为博客系统、上传系统、Blog_Connect_Service系统。<br>

首先博主通过博客系统发布博客，然后通过上传系统上传视频博客，然后通过Blog_Connect_Service系统进行在线交流。

步骤：
1.博主发布博客（个人博客地址：blog.maptoface.com）
<img src="https://img-blog.csdnimg.cn/20200414101644245.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMjkzNTc1,size_16,color_FFFFFF,t_70">

2.通过上传息系统上传教学视频（个人项目地址：img.maptoface.com）
<img src="https://img-blog.csdnimg.cn/20200414101842287.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMjkzNTc1,size_16,color_FFFFFF,t_70">

3.进入Blog_Connect_Service进行视频查看
<img src="https://img-blog.csdnimg.cn/20200414102450852.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMjkzNTc1,size_16,color_FFFFFF,t_70">

4.观看视频用户可以通过邮箱预约或者QQ预约联系博主，约定时间进行在线视频交流，然后博主可以创建房间进行实时沟通。（点击加入房间）
<img src="https://img-blog.csdnimg.cn/20200414102750714.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMjkzNTc1,size_16,color_FFFFFF,t_70">

5.点击创建房间后将房间号分享给用户，用户可以通过输入房间号进行加入。
<img src="https://img-blog.csdnimg.cn/20200414102945153.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMjkzNTc1,size_16,color_FFFFFF,t_70">
<img src="https://img-blog.csdnimg.cn/20200414103052734.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMjkzNTc1,size_16,color_FFFFFF,t_70">

6.加入房间后进行实时交流
<img src="https://img-blog.csdnimg.cn/20200414141917732.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzMxMjkzNTc1,size_16,color_FFFFFF,t_70">

#### 部署说明
1.(推荐)内嵌到项目中部署。

(1)sql：static/sql

(2)static/pages为生成静态页面。需要在application静态页面中指定uploadVideoFilePar路径到它当中。

(3)部署时：将pages中静态文件部署到nginx当中即可

2.接口模块部署：使用/static/service/blog_connect_service.rar
提供上传视频博客、实时房间的功能。



#### 待完成

1.整合声网实时消息sdk

2.整合netless实时视频播放插件

3.作为微服务与博客系统相整合
