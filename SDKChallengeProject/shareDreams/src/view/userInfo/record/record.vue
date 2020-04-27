<template>
  <div class="record">
    <div class="intro">
      <p>视频录制</p>
    </div>
    <div class="body">
      <div class="setting">
        <div class="title">
          <p>视频标题</p><input type="text" placeholder="视频标题" v-model="title"/>
        </div>
        <el-divider></el-divider>
        <div class="open" @click="start()">
          <p>开启设备</p>
        </div>
        <el-divider></el-divider>
        <div class="start" @click="startR()">
          <p>开始录制</p>
        </div>
        <el-divider></el-divider>
        <div class="stop" @click="cancel()">
          <p>停止录制</p>
        </div>
        <el-divider></el-divider>
        <div class="group-btn">
          <div @click="see()" class="see"><p>查看</p></div>
          <div @click="deleteVideo()" class="delete">删除</div>
        </div>
      </div>
      <div class="default">
        <img  src="../../../../static/record.jpg"  v-if="!startRecord"/>
        <div class="agora-theme" v-else>
          <div class="video-grid" id="video">
            <div class="remote_video">
              <div id="remote_video_record" class="remote-video-placeholder"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import API from '../../../utils/API'
import AgoraRTC from 'agora-rtc-sdk'
export default {
  data () {
    return {
      startRecord: false, // 设备是否开启
      loginUser: this.$store.state.loginUser, // 用户信息
      settingOptions: { // 供录制的流的参数
        appID: this.$store.state.appId,
        channel: `recordChannel1${this.$store.state.loginUser._id}`, // 随机生成一个channel名
        codec: 'h264',
        mode: 'live',
        token: '', // 验证安全
        uid: `${Math.round(Math.random() * 1000000000)}`, // 随机生成 32位整数
        microphoneId: this.$store.state.recordSetting.microphoneId,
        cameraId: this.$store.state.recordSetting.cameraId
      },
      subscribeOptions: { // 供订阅的流的参数
        appID: this.$store.state.appId,
        channel: `recordChannel1${this.$store.state.loginUser._id}`, // 进入录制频道
        codec: 'h264',
        mode: 'live',
        token: '',
        uid: `${Math.round(Math.random() * 1000000000)}`, // 只支持32位整数，随机生成数
        microphoneId: this.$store.state.recordSetting.microphoneId,
        cameraId: this.$store.state.recordSetting.cameraId
      },
      rtc: {// 用于发流的rtc
        client: null,
        joined: false,
        published: false,
        localStream: null,
        remoteStreams: [],
        params: {}
      },
      recordRtc: { // 创建一个用于录制的rtc
        client: null,
        joined: false,
        published: false,
        localStream: null,
        remoteStreams: [],
        params: {}
      },
      cameraOptions: [], // 用户摄像头设备
      microphoneOptions: [], // 用户音频设备
      resourceId: '', // 用于开始录制的resourceid
      sid: '', // 用于查找录制的sid
      video: {}, // 录制成功后的video信息
      title: '', // 标题
      recording: false // 是否录制中
    }
  },
  methods: {
    start () { // 发布一个流，用于录制
      if (this.recording) {
        console.log('正在录制中')
        return
      }
      this.rtc.client = AgoraRTC.createClient({mode: this.settingOptions.mode, codec: this.settingOptions.codec}) // 创建一个用于发流的client
      this.rtc.params = this.settingOptions
      this.rtc.client.init(this.settingOptions.appID, () => { // 初始化一个client
        console.log('init success')
        this.rtc.client.join(this.settingOptions.token ? this.settingOptions.token : null, this.settingOptions.channel, this.settingOptions.uid ? +this.settingOptions.uid : null, (uid) => {
          this.rtc.joined = true
          this.rtc.localStream = AgoraRTC.createStream({
            streamId: this.rtc.params.uid,
            audio: true,
            video: true,
            screen: false,
            microphoneId: this.settingOptions.microphoneId,
            cameraId: this.settingOptions.cameraId
          })
          this.startRecord = true
          this.rtc.localStream.init(() => { // 初始化本地流
            this.rtc.localStream.play('remote_video_record') // 播放该流
            var oldState = this.rtc.published
            this.rtc.client.publish(this.rtc.localStream, (err) => { // 成功发流后对该流进行订阅
              this.rtc.published = oldState
              console.log('发流失败')
              console.log(err)
            })
            this.subscribe() // 对流进行订阅
            this.rtc.published = true // 流发布成功，更改publish状态
          }, (err) => {
            console.log('初始化本地流失败', err)
          })
        }, (err) => {
          console.log('client加入频道失败', err)
        })
      }, (err) => {
        console.log('client初始化失败', err)
      })
    },
    subscribe () { // 对流进行订阅，才能进行录制
      this.recordRtc.client = AgoraRTC.createClient({mode: this.subscribeOptions.mode, codec: this.subscribeOptions.codec}) // 创建一个用于录制的client
      this.recordRtc.client.init(this.settingOptions.appID, () => { // 初始化
        this.recordRtc.client.join(this.subscribeOptions.token ? this.subscribeOptions.token : null, this.subscribeOptions.channel, this.subscribeOptions.uid ? +this.subscribeOptions.uid : null, (uid) => {
          console.log(`加入频道成功，用户id为: ${uid}`)
          this.recordRtc.client.on('stream-added', (evt) => { // 监听是否存在已发布的流
            var stream = evt.stream
            this.recordRtc.client.subscribe(stream, (err) => { // 对该流进行订阅后进行录制
              if (err) {
                console.log('subscribe fail')
                console.log(err)
                this.cancel() // 订阅失败关闭流并清除两个rtc的client
              }
            })
          })
        })
      })
    },
    cancel () { // 停止录制（云端停止，流的关闭，房间的退出）
      if (!this.recording) {
        console.log('还没有开始录制')
        return
      }
      this.stopRecord()
      setTimeout(() => {
        this.rtc.published = false
        this.recordRtc.client.leave(() => { // 录制rtc退出频道
          this.recordRtc.client = null // 清除录制rtc的client
          console.log('清除发流数据成功')
        }, (err) => {
          console.log('退出频道失败')
          console.error(err)
        })
        this.rtc.client.leave(() => { // 发流rtc退出频道
          this.rtc.localStream.stop() // 停止本地流
          this.rtc.localStream.close() // 关闭本地流
          this.startRecord = false // 关闭设备
          this.rtc.client = null // 清除所有数据
          this.rtc.localStream = null
          this.rtc.join = false
          console.log('清除发流数据成功')
        }, (err) => {
          console.log('退出频道失败')
          console.error(err)
        })
      }, 0)
    },
    getId () { // 获取resourceId
      API.record.getResourceId({channelName: this.settingOptions.channel, uid: this.subscribeOptions.uid}, (responseData) => {
        if (responseData.code === 200) {
          this.resourceId = responseData.resourceId
          console.log('get resourceId: ', this.resourceId)
        }
      }, (err) => {
        console.log(err)
      })
    },
    startR () { // 开始录制
      if (this.recording) {
        console.log('正在录制中')
        return
      }
      API.record.startRecord({resourceId: this.resourceId, channelName: this.subscribeOptions.channel, uid: this.subscribeOptions.uid, recordId: this.settingOptions.uid, userId: this.loginUser._id}, (responseData) => {
        console.log(responseData)
        if (responseData.code === 200) {
          if (responseData.result.sid === '') { // resourceId过期
            this.$router.go(0) // 刷新页面重新获取
          } else {
            this.sid = responseData.result.sid
            this.recording = true
            console.log('get sid: ', this.sid)
            this.$toast('开始录制')
          }
        }
      }, (err) => {
        console.log(err)
      })
    },
    stopRecord () { // 停止录制
      API.record.stopRecording({sid: this.sid, resourceId: this.resourceId, channelName: this.subscribeOptions.channel, uid: this.subscribeOptions.uid, userId: this.loginUser._id}, (responseData) => {
        console.log(responseData)
        if (responseData.code === 200) {
          this.video = responseData.data // 返回录制的视频信息（已保存在数据库中）
          this.$toast('录制成功')
          this.recording = false
        }
      }, (err) => {
        console.log(err)
      })
    },
    see () { // 查看录制的视频
      if (this.video._id !== undefined) {
        API.record.updateVideoDetail({_id: this.video._id, title: this.title}, (responseData) => {
          if (responseData.code === 200) {
            this.$router.push({path: `/user/record/playvideo`, query: {id: this.video._id}})
          }
        }, (err) => {
          console.log(err)
          this.$toast('视频保存失败，无法查看')
        })
      }
    },
    deleteVideo () { // 删除刚刚录制的视频
      if (this.video._id !== undefined) {
        API.record.deleteVideo({_id: this.video._id}, (responseData) => {
          if (responseData.code === 200) {
            this.$toast('删除成功')
            this.$router.back()
          }
        }, (errorData) => {
          console.log(errorData)
        })
      }
    }
  },
  created () {
    this.getId() // 需要考虑到resourceId过期问题
  },
  beforeDestroy () { // 在关闭前检查是否退出录制
    if (this.recording) {
      this.stopRecord()
    }
  }
}
</script>
<style lang="less" scoped>
@import url('../../../assets/css/videoModal.css');
.record{
  padding-bottom: 20px;
  .intro{
    height: 50px;
    border-bottom:1px solid grey;
    display: flex;
    flex-direction: row;
    align-items: center;
    font-size: 13px;
    color: #0B8DD9;
    p{
      border-left: 2px solid #0B8DD9;
      padding-left: 10px;
      margin-left:20px;
    }
  }
  .body{
    margin-top: 10px;
    max-width: 100%;
    overflow-x: hidden;
    height:auto;
    background-color: white;
    flex: 1;
    display: flex;
    flex-direction: row;
    .setting{
      margin: 10px;
      width: 300px;
      .title{
        display: flex;
        flex-direction: row;
        height: 25px;
        input{
          background: transparent;
          color: rgb(66, 66, 66);
          width: 170px;
          margin: 10px 0 0 20px;
          height: 25px;
          font-weight: bold;
          font-size: 16px;
          border: none;
          position: relative;
          outline: none;
          border-bottom: 1px solid black;
        }
      }
      .open{
        cursor: pointer;
      }
      .start{
        cursor: pointer;
      }
      .stop{
        cursor: pointer;
      }
      .group-btn{
        margin-left: 50px;
        height: 40px;
        display: flex;
        flex-direction: row;
        color: white;
        font-size: 13px;
        .see{
          width: 50px;
          height: 30px;
          background-color: grey;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
        .delete{
          margin-left: 30px;
          padding: 0 10px;
          height: 30px;
          background-color: grey;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
      }
    }
    .default{
      margin: 20px;
      padding: 10px;
      width: 480px;
      height: 360px;
      box-shadow: 0 0 1px  0 rgb(0, 0, 0);
      img{
        width: 480px;
        height: 360px;
      }
      .agora-theme{
        .video-grid{
          width: 480px;
          height: 360px;
        }
      }
    }
  }
}
</style>
