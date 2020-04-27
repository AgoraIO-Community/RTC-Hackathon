<template>
  <div class="all-follows">
    <div class="intro">
      <p>我的直播</p>
    </div>
    <div class="body">
      <div class="left">
        <div class="instruc">
          <p>标题：</p>
          <input v-model="title" />
        </div>
        <div class="content">
          <img src="../../../../static/record.jpg"  v-if="!startLive"/>
          <div class="agora-theme" v-else>
            <div class="video-grid" id="video">
              <div class="video-view">
                <div id="local_stream" class="video-placeholder"></div>
                <div id="local_video_info" class="video-profile hide"></div>
                <div id="video_autoplay_local" class="autoplay-fallback hide"></div>
              </div>
            </div>
          </div>
        </div>
        <div class="settings">
          <div class="preview" @click="preview()"><p>开启设备</p></div>
          <div class="preview" @click="stopPreview()"><p>关闭设备</p></div>
          <div class="publish" @click="publish()"><p>开启直播</p></div>
          <div class="stop" @click="stop()"><p>关闭直播</p></div>
        </div>
      </div>
      <div class="right">
        <ChatInteract :roomId="settingOptions.channel"/>
      </div>
    </div>
  </div>
</template>
<script>
import AgoraRTC from 'agora-rtc-sdk'
import ChatInteract from '../../../components/chat-interact' // 聊天框
import API from '../../../utils/API'
export default {
  components: {
    ChatInteract
  },
  data () {
    return {
      title: '', // 直播间标题
      startLive: false, // 是否开启设备
      microphoneOptions: this.$store.state.userMediaDevices.microphoneOptions, // 音频
      cameraOptions: this.$store.state.userMediaDevices.cameraOptions, // 摄像头
      loginUser: this.$store.state.loginUser,
      settingOptions: {
        appID: this.$store.state.appId,
        channel: this.$store.state.live.roomId,
        codec: 'h264',
        mode: 'live',
        token: '', // 验证安全
        uid: this.$store.state.loginUser._id, // 用户
        microphoneId: this.$store.state.liveSetting.microphoneId,
        cameraId: this.$store.state.liveSetting.cameraId
      },
      rtc: this.$store.state.rtc
    }
  },
  methods: {
    preview () {
      this.$store.dispatch('emitRtcCreateClient', AgoraRTC.createClient({mode: this.settingOptions.mode, codec: this.settingOptions.codec}))
      this.$store.dispatch('emitRtcAddParams', this.settingOptions)
      this.rtc.client.init(this.settingOptions.appID, () => {
        console.log('init success')
        this.rtc.client.setClientRole('host', (err) => {
          if (!err) {
            console.log('set host success')
          } else {
            console.log('set host fail')
          }
        })
        this.rtc.client.join(this.settingOptions.token ? this.settingOptions.token : null, this.settingOptions.channel, this.settingOptions.uid ? +this.settingOptions.uid : null, (uid) => {
          this.$store.dispatch('emitRtcJoin', true)
          this.$store.dispatch('emitRtcCreateLocalStream', AgoraRTC.createStream({
            streamId: this.rtc.params.uid,
            audio: true,
            video: true,
            screen: false,
            microphoneId: this.settingOptions.microphoneId,
            cameraId: this.settingOptions.cameraId
          }))
          this.startLive = true
          this.rtc.localStream.init(() => {
            this.rtc.localStream.play('local_stream', {fit: 'cover'})
            console.log(this.rtc.localStream)
          }, (err) => {
            console.log('init local stream fail: ', err)
          })
        }, (err) => {
          console.log('client join fail: ', err)
        })
      }, (err) => {
        console.log('init failed: ', err)
      })
    },
    stopPreview () {
      if (!this.rtc.client) {
        return
      }
      if (!this.rtc.joined) {
        return
      }
      this.rtc.client.leave(() => { // 离开频道
        this.rtc.localStream.stop()
        this.rtc.localStream.close()
        this.startLive = false
        this.saveStatus()
        this.$store.dispatch('emitRtcCreateLocalStream', null) // 清除所有数据
        this.$store.dispatch('emitRtcCreateClient', null)
        this.$store.dispatch('emitRtcPublish', false)
        this.$store.dispatch('emitRtcJoin', false)
        console.log('成功离开频道')
      }, (err) => {
        console.log('channel leave fail')
        console.error(err)
      })
    },
    publish () {
      if (!this.rtc.client) {
        console.log('没有创建client')
        this.$toast('请先开启设备')
        return
      }
      var oldState = this.rtc.published
      this.rtc.client.publish(this.rtc.localStream, (err) => {
        this.$store.dispatch('emitRtcPublish', oldState)
        console.log('publish failed')
        console.log(err)
      })
      this.$store.dispatch('emitRtcPublish', true)
      API.Live.startLiving({roomId: this.settingOptions.channel, hostId: this.loginUser._id, time: new Date().getTime(), title: this.title}, (responseData) => {
        if (responseData.code === 200) {
          console.log(responseData)
          console.log('开播信息保存成功')
          this.$toast('开播成功')
        } else {
          console.log(responseData)
          this.$toast('开播信息保存失败')
        }
      }, (err) => {
        console.log(err)
      })
    },
    stop () {
      if (!this.rtc.client) { // 未点击开启设备
        return
      }
      if (!this.rtc.published) {
        return
      }
      var oldState = this.rtc.published
      this.rtc.client.unpublish(this.rtc.localStream, (err) => {
        this.$store.dispatch('emitRtcPublish', oldState)
        console.log('unpublish failed')
        console.log(err)
      })
      this.$toast('关播成功')
      this.$store.dispatch('emitRtcPublish', false)
      setTimeout(() => { // 点击关闭直播就默认关闭设备
        this.stopPreview()
      }, 500)
    },
    saveStatus () {
      API.Live.stopLiving({hostId: this.loginUser._id}, (responseData) => {
        if (responseData.code === 200) {
          console.log(responseData)
        } else {
          console.log(responseData)
        }
      }, (err) => {
        console.log(err)
      })
    }
  },
  created () {
    API.Live.getMyLiveRoomDetail({hostId: this.loginUser._id}, (responseData) => { // 获取上次直播的信息
      if (responseData.code === 200) {
        console.log(responseData)
        if (responseData.data !== null) { // 已开通权限，且上次直播过，获取上次直播的主题
          this.title = responseData.data.title
        }
      } else {
        console.log(responseData)
      }
    }, (errorData) => {
      console.log(errorData)
    })
  },
  beforeDestroy () {
    if (this.rtc.client) { // 未关闭直播就直接退出
      this.stop()
      setTimeout(() => {
        this.stopPreview()
      }, 100)
    }
  }
}
</script>
<style lang="less" scoped>
@import url('../../../assets/css/common.css');
.all-follows{
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
    display: flex;
    flex-direction: row;
    .left{
      margin: 20px;
      flex: 2;
      display: flex;
      flex-direction: column;
      .instruc{
        border-radius: 5px 5px 0 0 ;
        background:rgb(27, 27, 36);
        width: 640px;
        color: white;
        display: flex;
        flex-direction: row;
        p{
          margin-left: 20px;
          width: 60px;
          font-size: 20px;
        }
        input{
          margin-top: 20px;
          width: 350px;
          border: none;
          background-color: transparent;
          border-bottom: 1px solid white;
          outline: none;
          height: 25px;
          color: white;
          font-size: 20px;
          font-weight: bold;
        }
      }
      .content{
        img{
          width: 640px;
          height: 480px;
        }
        .agora-theme{
          width: 640px;
          height: 480px;
          background: black;
          .video-grid{
            .video-view{}
          }
        }
      }
      .settings{
        margin-top: 10px;
        flex-direction: row;
        display: flex;
        color: white;
        .preview{
          margin-left: 10px;
          padding: 0 10px;
          height: 30px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
        .publish{
          margin-left: 10px;
          padding: 0 10px;
          height: 30px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
        .stop{
          margin-left: 10px;
          padding: 0 10px;
          height: 30px;
          background-color: #0B8DD9;
          border-radius: 5px;
          flex-direction: row;
          display: flex;
          justify-content: center;
          align-items: center;
          cursor: pointer;
        }
      }
    }
    .right{
      flex:1;
      margin: 5px;
      background-color: rgb(27, 27, 36);
      border-radius: 5px;
      color: white;
    }
  }
}
</style>
