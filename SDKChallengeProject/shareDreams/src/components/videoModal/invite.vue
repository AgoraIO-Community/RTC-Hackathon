<template>
  <div class="videoModal">
    <div class="content" ref="content">
      <div class="agora-theme">
        <div class="video-grid" id="video">
          <div class="remote_video">
            <div id="remote_video_123456" class="remote-video-placeholder"></div>
            <div id="remote_video_info_123456" class="video-profile"></div>
            <div id="video_autoplay_123456" class="autoplay-fallback hide"></div>
          </div>
          <div class="video-view">
            <div id="local_stream" class="video-placeholder"></div>
            <div id="local_video_info" class="video-profile hide"></div>
            <div id="video_autoplay_local" class="autoplay-fallback hide"></div>
          </div>
        </div>
      </div>
      <div class="bottom"  ref="bottom">
        <div v-if="status">
          <div class="tips">邀请视频聊天中</div>
          <div class="options">
            <div class="cancel" @click="inviteOptions('cancel')" v-if="!refuse">取消</div>
            <div class="refuse" @click="inviteOptions('refuse')" v-else>对方已拒绝</div>
          </div>
        </div>
        <Hangup @options="inviteOptions" :hangupStatus="hangupStatus" v-else/>
      </div>
    </div>
  </div>
</template>
<script>
import rtm from '../../utils/rtm/createClient'
import Hangup from '../../components/hangup'
import AgoraRTC from 'agora-rtc-sdk'
export default {
  components: {
    Hangup
  },
  data () {
    return {
      status: true, // callee是否接受
      hangupStatus: true, // 挂断提示是否显示
      loginUser: this.$store.state.loginUser, // caller
      chatUser: this.$parent.chatUser, // callee
      refuse: false, // callee是否拒绝
      rtc: this.$store.state.rtc,
      settingOptions: { // 将所有数据统一放置在一个接口里边
        appID: this.$store.state.appId,
        channel: 'videochannel123456',
        codec: 'h264',
        mode: 'rtc',
        token: '', // 验证安全
        uid: this.$store.state.loginUser._id, // 用户
        microphoneId: this.$store.state.liveSetting.microphoneId,
        cameraId: this.$store.state.liveSetting.cameraId
      }
    }
  },
  methods: {
    inviteOptions (option) { // caller 邀请操作
      this.$emit('inviteOptions', option)
    }
  },
  created () {
    rtm.localInvitation.on('LocalInvitationAccepted', () => { // callee接受邀请
      this.status = false
      this.rtc.client.on('stream-added', (evt) => { // 监听remote stream是否存在
        var stream = evt.stream
        this.$nextTick(() => {
          stream.play('remote_video_123456', (err) => { // 播放远程流（聊天对象）
            console.log('remote stream error')
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
      this.rtc.client.on('peer-leave', (evt) => { // when clientRole is host , only host leave can emit this
        this.$emit('receiveOptions', 'hangup') // 对方离开，触发关闭事件
        console.log('peer-leave')
      })
    })
    rtm.localInvitation.on('LocalInvitationCanceled', () => { // caller取消邀请
      console.log('invitation canceled')
    })
    rtm.localInvitation.on('LocalInvitationFailure', () => { // 发起邀请失败
      console.log('invitation fail')
      this.$emit('inviteOptions', 'hangup')
    })
    rtm.localInvitation.on('LocalInvitationRefused', () => { // caller拒绝邀请
      console.log('invitation refused')
      this.$nextTick(() => {
        this.refuse = true
      })
      setTimeout(() => {
        this.inviteOptions('refuse')
      }, 1000)
    })
    rtm.localInvitation.on('LocalInvitationReceivedByPeer', () => { // callee是否接收到邀请
      console.log('invitation received')
    })
    if (this.rtc.joined || this.rtc.client !== null) { // 需要判断client只有一个，也就是当直播或看着直播的时候不能进行语音通话。
      console.log('already joined')
    } else { // 可以封装起来
      this.$store.dispatch('emitRtcCreateClient', AgoraRTC.createClient({mode: this.settingOptions.mode, codec: this.settingOptions.codec}))
      this.$store.dispatch('emitRtcAddParams', this.settingOptions)
      this.rtc.client.init(this.settingOptions.appID, () => {
        console.log('init success')
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
          this.rtc.localStream.init(() => { // 初始化一个流
            this.rtc.localStream.play('local_stream', (err) => { // 播放本地流
              console.log(err)
            })
            var oldState = this.rtc.published
            this.rtc.client.publish(this.rtc.localStream, (err) => {
              this.$store.dispatch('emitRtcPublish', oldState) // 流发布失败，publish状态不变
              console.log('publish failed')
              console.log(err)
            })
            this.$store.dispatch('emitRtcPublish', true) // 流发布成功，更改publish状态
          }, (err) => {
            console.log('init local stream fail: ', err)
          })
          this.rtc.client.on('peer-leave', (evt) => { // 监听callee，如果callee离开就退出视频通话
            console.log('peer leaveeeeeeeeeeeeeee')
            console.log(evt)
            this.$emit('inviteOptions', 'hangup') // callee离开，触发关闭事件
          })
        }, (err) => {
          console.log('client join fail: ', err)
        })
      }, (err) => {
        console.log('init failed: ', err)
      })
    }
  },
  mounted () {
    this.$refs.content.addEventListener('mouseover', (e) => { // 显示提示
      this.$refs.bottom.style.opacity = 1
    })
    this.$refs.content.addEventListener('mouseout', (e) => { // 隐藏提示
      this.$refs.bottom.style.opacity = 0
    })
  }
}
</script>
<style lang="less" scoped>
@import url('../../assets/css/videoModal.css');
.videoModal{
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
    z-index: 10;
}
.content{
    position: relative;
    width:300px;
    height: 400px;
    .agora-theme{
        width: 100%;
        height: 100%;
        background: black;
        border-radius: 5px;
        z-index: 10;
    .video-grid{
      .video-view{
        position: absolute;
        top: 10px;
        right: 10px;
        width: 100px;
        height: 100px;
        background: blanchedalmond;
       z-index: 99;
      }
      }
    }
    .bottom{
      opacity: 0;
      position: absolute;
      bottom: 0;
      left: 50%;
      transform: translate(-50%,0);
      z-index: 999;
    .tips{
        color: white;
        margin-bottom: 20px;
        text-align: center;
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
}
</style>
