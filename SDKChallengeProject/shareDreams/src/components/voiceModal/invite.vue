<template>
  <div class="voiceModal">
    <div class="content">
      <div class="top">
        <img :src="chatUser.userAvatar" />
      </div>
      <div class="bottom"  v-if="status">
        <div class="name">{{chatUser.userName}}</div>
        <div class="tips">邀请通话中</div>
        <div class="options">
          <div class="cancel" @click="inviteOptions('cancel')" v-if="!refuse">取消</div>
          <div class="refuse" @click="inviteOptions('refuse')" v-else>对方已拒绝</div>
        </div>
      </div>
      <div class="bottom"  v-else>
        <div class="tips">通话中</div>
        <div class="agora-theme">
          <div class="audio-grid" id="audio">
            <div class="remote_video">
              <div id="remote_video_123456" class="remote-video-placeholder"></div>
              <div id="remote_video_info_123456" class="video-profile"></div>
              <div id="video_autoplay_123456" class="autoplay-fallback hide"></div>
            </div>
          </div>
        </div>
        <Hangup @options="inviteOptions" :hangupStatus="hangupStatus" />
      </div>
    </div>
  </div>
</template>
<script>
import rtm from '../../utils/rtm/createClient'
import AgoraRTC from 'agora-rtc-sdk'
import Hangup from '../hangup' // 挂断通话组件
export default {
  components: {
    Hangup
  },
  data () {
    return {
      status: true, // 等待callee响应中
      loginUser: this.$store.state.loginUser,
      chatUser: this.$parent.chatUser, // 语音通话对象
      refuse: false, // 对方是否拒绝
      rtc: this.$store.state.rtc, // 一个对话只能有一个client，设置为全局只能有一个client
      settingOptions: { // 将所有数据统一放置在一个接口里边
        appID: this.$store.state.appId,
        channel: this.$store.state.loginUser._id, // 设置频道为caller的id
        codec: 'h264',
        mode: 'rtc',
        token: '', // 验证安全
        uid: this.$store.state.loginUser._id, // 用户
        microphoneId: this.$store.state.liveSetting.microphoneId,
        cameraId: ''
      },
      hangupStatus: true // 语音通话就不需要隐藏挂断提示
    }
  },
  methods: {
    inviteOptions (option) { // 邀请选项（取消邀请，对方拒绝）
      this.$emit('inviteOptions', option)
    }
  },
  created () {
    rtm.localInvitation.on('LocalInvitationAccepted', () => { // callee接受邀请
      this.status = false
      this.rtc.client.on('stream-added', (evt) => { // 监听callee的stream
        var stream = evt.stream
        this.$nextTick(() => { // 播放callee的stream
          stream.play('remote_video_123456', (err) => {
            console.log('播放失败')
            console.log(err)
          })
        })
        this.rtc.client.subscribe(stream, (err) => {
          if (err) {
            console.log('subscribe fail')
            console.log(err)
          }
        })
      })
      this.rtc.client.on('peer-leave', (evt) => { // 对方离开，触发关闭事件
        this.$emit('receiveOptions', 'hangup')
        console.log('对方离开')
      })
    })
    rtm.localInvitation.on('LocalInvitationCanceled', () => { // caller取消邀请
      console.log('取消邀请')
    })
    rtm.localInvitation.on('LocalInvitationFailure', () => { // callee长时间未响应
      console.log('对方未响应')
      this.$emit('inviteOptions', 'hangup')
    })
    rtm.localInvitation.on('LocalInvitationRefused', () => { // callee拒绝
      console.log('语音通话被拒绝了')
      this.$nextTick(() => {
        this.refuse = true
      })
      setTimeout(() => { // 先显示用户拒绝提醒，在触发refuse回调
        this.inviteOptions('refuse')
      }, 1000)
    })
    rtm.localInvitation.on('LocalInvitationReceivedByPeer', () => { // 判断callee是否接收到邀请
      console.log('对方接受到了语音邀请')
    })
    if (this.rtc.joined || this.rtc.client !== null) { // 需要判断client只有一个，也就是当直播或看着直播的时候不能进行语音通话。
      console.log('已经加入其他client，先清除数据')
    } else {
      this.$store.dispatch('emitRtcCreateClient', AgoraRTC.createClient({mode: this.settingOptions.mode, codec: this.settingOptions.codec})) // 当caller点击时就创建一个client
      this.$store.dispatch('emitRtcAddParams', this.settingOptions)
      this.rtc.client.init(this.settingOptions.appID, () => { // 当callee接受要邀请时就
        this.rtc.client.join(this.settingOptions.token ? this.settingOptions.token : null, this.settingOptions.channel, this.settingOptions.uid ? +this.settingOptions.uid : null, (uid) => {
          this.$store.dispatch('emitRtcJoin', true)
          this.$store.dispatch('emitRtcCreateLocalStream', AgoraRTC.createStream({
            streamId: this.rtc.params.uid,
            audio: true, // 只创建音流
            video: false,
            screen: false,
            microphoneId: this.settingOptions.microphoneId,
            cameraId: this.settingOptions.cameraId
          }))
          this.rtc.localStream.init(() => {
            var oldState = this.rtc.published
            this.rtc.client.publish(this.rtc.localStream, (err) => { // 发布之后才能远程用户才能接收
              this.$store.dispatch('emitRtcPublish', oldState)
              console.log('发布失败')
              console.log(err)
            })
            this.$store.dispatch('emitRtcPublish', true)
          }, (err) => {
            console.log('init local stream fail: ', err)
          })
        }, (err) => {
          console.log('client join fail: ', err)
        })
      }, (err) => {
        console.log('init failed: ', err)
      })
    }
  }
}
</script>
<style lang="less" scoped>
@import url('../../assets/css/videoModal.css');
.voiceModal{
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;

}
.content{
    width:300px;
    height: 400px;
    background: black;
    flex-direction: column;
    display: flex;
    border-radius: 5px;
    .top{
        height: 260px;
        background: transparent;
        display: flex;
        flex-direction: row;
        justify-content: center;
        align-items: center;
        img{
            width: 120px;
            height: 120px;
            border-radius: 50%;
        }
    }
    .bottom{
      flex: 1;
      position: relative;
    }
    .name{
        color: white;
        margin-bottom: 5px;
        text-align: center;
    }
    .tips{
        color: white;
        margin-bottom: 20px;
        text-align: center;
    }
    .agora-theme{
      width: 100px;
      height: 50px;
      position: absolute;
    }
    .options{
        flex: 1;
        display: flex;
        flex-direction: row;
        color: white;
        margin-bottom: 20px;
        justify-content: center;
        .cancel{
            width: 60px;
            height:30px;
            background-color: red;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            cursor: pointer;
        }
        .refuse{
            width: 180px;
            height:30px;
            background-color: red;
            display: flex;
            flex-direction: row;
            justify-content: center;
            align-items: center;
            border-radius: 5px;
            cursor: pointer;
        }
    }
}
</style>
