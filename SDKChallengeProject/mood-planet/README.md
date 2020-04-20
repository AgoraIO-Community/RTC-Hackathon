# 作品简介
在线心情星球是为了解决难以在其他社交软件因为种种原因无法表达自己最直接情感的年轻人而生的一个心情匹配即时聊天的web网站，
支持多人一对一匹配，可文字可视频，发布动态，找寻失去的真我与快乐。

# 环境介绍
* 后端 : java / springboot / shiro / mybatis-generator(快速搭建mybatis模型层)
* 前端 : vue / element UI
* 数据库 ：mysql
* 文字即时通讯部分：websocket
* 音视频部分：AgoraRTCSDK-web（声网）

# 编译方式
1.数据库准备
  导入create_sql.sql
2.后台：使用idea导入vedio_rtc文件夹项目，(已有jdk，maven环境)直接启动VedioApplication，默认端口8081
3.前台：使用vscode导入vedio_rtc_vue文件夹项目，默认端口8080
	npm install 安装node包
	npm run dev 启动
除了登录注册，其他请求均需要登陆后操作，音视频在线聊天部分因为本地浏览器抢占摄像头原因，需要同浏览器，建议使用chrome，再ctrl+shift+N 新建会话，便可多用户体验
后台虚拟文件存储路径根据本地磁盘可在springboot的配置文件更改
	
# 项目成员（平行直角队）
daiziwen  294107504@qq.com

# 联系方式
qq: 294107504