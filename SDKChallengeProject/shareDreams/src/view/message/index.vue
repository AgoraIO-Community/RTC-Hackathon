<template>
  <div class="message">
    <Header />
    <div class="chat">
      <div class="chat-body">
        <div class="chat-left">
          <div class="chat-header">
              <input type="text" v-model="name" disabled/>
          </div>
          <div class="person-list" >
            <div v-if="friendList.length!==0">
              <div class="per-person" v-for="(item,index) in friendList" :key="index" @click="chatWith(item,index)">
                <div :class="index===selected?'selected':'unselected'"></div>
                <el-badge :value="item.__v" class="item" :hidden="item.__v===0" type="primary">
                  <div class="top">
                    <img :src="item.id1===loginUser._id?item.user2.userAvatar:item.user1.userAvatar" />
                    <p class="userName">{{item.id1===loginUser._id?item.user2.userName:item.user1.userName}}</p>
                  </div>
                  <p class="per-message">{{item.lastMessage}}</p>
                </el-badge>
              </div>
            </div>
            <div v-else class="left-none"><p>暂无聊天对象</p></div>
          </div>
        </div>
        <div class="chat-right" v-if="friendList.length!==0">
          <div class="message-header">
            <div class="intro" >
              <img :src="chatUser.userAvatar" />
              <div class="tips">
              <p class="userName">{{chatUser.userName}}</p>
              <p class="onlineStatus">{{status}}</p>
            </div>
            </div>
            <div class="talk">
              <div class="phone" :class="activePhoneInvite||activePhoneReceive?'activePhone':''" @click="phoneCall()"><i class="el-icon-phone"></i></div>
              <div class='video' :class="activeVideoInvite||activeVideoReceive?'activeVideo':''" @click="videoCall()"><i class="el-icon-video-camera-solid"></i></div>
              <div class='setting' :class="activeSetting?'activeSetting':''"><i class="el-icon-s-tools"></i></div>
            </div>
          </div>
          <div class="message-list">
            <div v-for="(item,index) in messageList" :key="index" :class="item.toId===loginUser._id?'per-message-left':'per-message-right'" >
              <div class="trangle"></div>
              <div class="message">{{item.message}}</div>
            </div>
          </div>
          <div class="send-bar">
            <textarea class="input-value" v-model="text" />
            <div class="button-group">
              <button @click="text===''">取消</button>
              <button @click="send()" :disabled="text===''">发送</button>
            </div>
          </div>
        </div>
        <div class="right-none" v-else><p>暂无信息</p></div>
      </div>
    </div>
    <PhoneInvite v-if="activePhoneInvite" @inviteOptions="getPhoneInvite" :chatUser="chatUser"/>
    <PhoneReceive v-if="activePhoneReceive" @receiveOptions="getPhoneReceive" :chatUser="caller" :channel="phoneChannel"/>
    <VideoInvite v-if="activeVideoInvite" @inviteOptions="getVideoInvite" :chatUser="chatUser"/>
    <VideoReceive v-if="activeVideoReceive" @receiveOptions="getVideoReceive" :chatUser="caller"/>
  </div>
</template>

<script>
import PhoneInvite from '../../components/voiceModal/invite'
import PhoneReceive from '../../components/voiceModal/receive'
import VideoInvite from '../../components/videoModal/invite'
import VideoReceive from '../../components/videoModal/receive'
import Header from '../../components/header'
import API from '../../utils/API'
import rtm from '../../utils/rtm/createClient'
import rtmMethods from './index'
export default {
  components: {
    Header, PhoneInvite, PhoneReceive, VideoInvite, VideoReceive
  },
  data () {
    return {
      messageList: [], // 聊天信息列表
      name: '', // 搜索用户名，暂时没写这个功能
      selected: 0, // 选择聊天对象
      text: '',
      loginUser: this.$store.state.loginUser,
      friendList: [], // 聊天对象列表
      rtc: this.$store.state.rtc,
      params: { // rtc 参数
        appId: this.$store.state.appId,
        accountName: this.$store.state.loginUser.userName,
        channelName: '' //
      },
      chatUser: {}, // 聊天对象
      caller: {}, // 发起语音获取视频通话用户的信息
      phoneChannel: '', // 语音通话进入的通道
      activePhoneInvite: false, // 语音通话(邀请)
      activePhoneReceive: false, // 语音通话（接受）
      activeVideoInvite: false, // 视频通话(邀请)
      activeVideoReceive: false, // 视频通话（接受）
      activeSetting: false, // 设置按钮
      joinChannel: false, // 是否已经加入聊天房间中
      status: 'offline', // 聊天对象的在线状态
      exist: false // 通过视频详情页面点击进来，判断视频上传用户是否在聊天用户的聊天列表中
    }
  },
  methods: {
    phoneCall () { // 发起一个语音通话
      setTimeout(() => { // 缓冲创建一个邀请
        rtmMethods.rtmCreateLocalInvitation({calleeId: this.chatUser._id}, rtm) // 发起邀请
      }, 500)
      setTimeout(() => {
        this.activePhoneInvite = !this.activePhoneInvite
        rtmMethods.rtmSendInvitation(JSON.stringify({userAvatar: this.loginUser.userAvatar, userName: this.loginUser.userName, method: 'voice'}), this.loginUser._id, rtm) // channelId为用户_id
      }, 1000)
    },
    getPhoneInvite (option) { // caller语音邀请的各种操作
      if (option === 'cancel') { // caller取消通话
        this.activePhoneInvite = false
        rtmMethods.rtmCancelInvitation(rtm) // 取消邀请
      } else if (option === 'hangup') { // caller挂断语音
        this.activePhoneInvite = false
        console.log('通话结束')
      } else { //
        this.activePhoneInvite = false
        console.log('对方拒绝')
      }
      this.leave()
    },
    getPhoneReceive (option) { // 接受到语音通话
      this.$notify.closeAll() // 关闭全局提示div
      if (option === 'refuse') {
        this.activePhoneReceive = false
        rtm.remoteInvitation = null
      } else if (option === 'hangup') {
        this.activePhoneReceive = false
        console.log('通话结束')
        this.leave()
      }
    },
    videoCall () { // 发起一个视频通话
      setTimeout(() => { // 缓冲创建一个邀请
        rtmMethods.rtmCreateLocalInvitation({calleeId: this.chatUser._id}, rtm)
      }, 500)
      setTimeout(() => {
        this.activeVideoInvite = !this.activeVideoInvite
        rtmMethods.rtmSendInvitation(JSON.stringify({userAvatar: this.loginUser.userAvatar, userName: this.loginUser.userName, method: 'video'}), this.loginUser._id,rtm) // channelId为用户_id
      }, 1000)
    },
    getVideoInvite (option) { // caller视频通话邀请操作
      if (option === 'cancel') {
        this.activeVideoInvite = false
        rtmMethods.rtmCancelInvitation() // 取消邀请
        console.log(rtm.localInvitation.state)
      } else if (option === 'hangup') {
        this.activeVideoInvite = false
        rtm.remoteInvitation = null // 删除远程邀请
        console.log('通话结束')
        this.leave()
      } else {
        this.activeVideoInvite = false
        console.log('对方拒绝')
        this.leave()
      }
    },
    getVideoReceive (option) { // 接收到视频邀请
      this.$notify.closeAll() // 清除全局提示
      if (option === 'refuse') {
        this.activeVideoReceive = false
        // rtm.remoteInvitation.refuse()
        console.log(rtm.remoteInvitation.state)
        rtm.remoteInvitation = null
      } else if (option === 'hangup') {
        this.activeVideoReceive = false // 隐藏视频接收div
        rtm.remoteInvitation = null // 删除远程邀请
        console.log('通话结束')
        this.rtc.client.leave(() => { // 离开rtc
          this.startLive = false
          this.$store.dispatch('emitRtcCreateClient', null)
          this.$store.dispatch('emitRtcJoin', false)
          console.log('leave success')
        }, (err) => {
          console.log('channel leave fail')
          console.error(err)
        })
      }
    },
    leave () { // 离开频道，清除所有信息
      var oldState = this.rtc.published // 跟通话结束一样需要断开连接
      this.rtc.client.unpublish(this.rtc.localStream, (err) => { // 取消发布
        this.$store.dispatch('emitRtcPublish', oldState)
        console.log('unpublish failed')
        console.log(err)
      })
      this.$store.dispatch('emitRtcPublish', false)
      this.rtc.client.leave(() => { // 用户离开rtc
        if (this.activeVideoInvite || this.activeVideoReceive) {
          this.rtc.localStream.stop()
          this.rtc.localStream.close()
        }

        this.$store.dispatch('emitRtcCreateLocalStream', null)
        this.$store.dispatch('emitRtcCreateClient', null)
        this.$store.dispatch('emitRtcJoin', false)
        console.log('leave success')
      }, (err) => {
        console.log('channel leave fail')
        console.error(err)
      })
    },
    send () { // 发送信息，
      if ((this.text).trim() !== '') {
        rtmMethods.rtmSendChannelMessage({ // 发送至1v1房间内
          appId: this.params.appId,
          accountName: this.params.accountName,
          channelName: this.params.channelName,
          channelMessage: this.text
        }, rtm)
        rtmMethods.rtmSendPeerMessage({ // 用户在线发送至用户
          appId: this.params.appId,
          accountName: this.params.accountName,
          peerId: this.chatUser._id,
          peerMessage: this.text
        }, rtm)
        let params = {channelName: this.params.channelName, toId: this.chatUser._id, userName: this.chatUser.userName, userAvatar: this.chatUser.userAvatar, message: this.text, fromId: this.loginUser._id, fromUserName: this.loginUser.userName, fromUserAvatar: this.loginUser.userAvatar, time: new Date().getTime()}
        this.messageList.push(params) // 将发送的信息添加到信息列表中
        var currentFriend = this.friendList[this.selected]
        currentFriend.lastMessage = this.text // 改变当前聊天对象的最后一条信息
        this.$nextTick(() => { // 使用nextTick()更新dom
          var ele = document.getElementsByClassName('message-list')[0]
          ele.scrollTop = ele.scrollHeight
          this.friendList.splice(this.selected, 1) // 将当前聊天对象放置在聊天对象列表的第一个
          this.friendList.unshift(currentFriend)
          this.selected = 0
        })
        this.text = '' // 清空信息
        API.users.uploadMessage(params, (responseData) => { // 保存信息
          if (responseData.code === 200) {
            this.text = ''
          }
        }, (error) => {
          console.log(error)
        })
      }
    },
    chatWith (item, index) { // 点击对应用户清除未读信息以及加入聊天房间
      this.selected = index
      this.chatUser = item
      if (item.id1 === this.loginUser._id) {
        this.chatUser = {
          _id: item.id2,
          userName: item.user2.userName,
          userAvatar: item.user2.userAvatar
        }
      } else {
        this.chatUser = {
          _id: item.id1,
          userName: item.user1.userName,
          userAvatar: item.user1.userAvatar
        }
      }
      this.params.channelName = `${item.id1}${item.id2}` // 设置房间频道为两个用户的id组成
      this.friendList[index].__v = 0 // __v表示未读信息的条数
      if (this.joinChannel) { // 如果用户已经加入其他频道（与他人进行聊天），离开当前频道
        rtmMethods.rtmLeave(this.params, rtm)
        this.joinChannel = false
        this.$nextTick(() => {
          this.messageList = []
        })
      }
      API.users.getMessageHistory({channelName: this.params.channelName}, (responseData) => { // 获取与当前聊天对象的聊天信息
        if (responseData.code === 200) {
          console.log(responseData)
          if (responseData.data.length === 0) {
          } else {
            this.messageList = responseData.data
            this.$nextTick(() => { // 使用nextTick()更新dom
              var ele = document.getElementsByClassName('message-list')[0]
              ele.scrollTop = ele.scrollHeight
            })
          }
        }
      }, (err) => {
        console.log(err)
      })
      API.users.readMessage({fromId: this.chatUser._id, toId: this.loginUser._id}, (responseData) => { // 读取所有未读信息
        console.log(this.chatUser)
        if (responseData.code === 200) {
          console.log('read all messages')
        }
      }, (err) => {
        console.log('read fail')
        console.log(err)
      })
      setTimeout(() => { // 加入与该对象聊天的频道
        rtmMethods.rtmJoin(this.params, rtm)
        this.joinChannel = true
        rtm.queryPeersOnlineStatus(this.chatUser._id).then((res) => { // 查询聊天对象的在线状态
          let result = res[this.chatUser._id]
          this.$nextTick(() => {
            this.status = result ? 'online' : 'offline'
          })
        }).catch((err) => {
          console.log(err)
          console.log('error')
        })
      }, 1000)
    },
    receiveInvitation (invitation) { // 接收到邀请，展示邀请对象的信息
      var content = JSON.parse(invitation.content)
      this.caller = {
        userAvatar: content.userAvatar,
        userName: content.userName
      }
      this.phoneChannel = invitation.channelId
      if (content.method === 'voice') { // 通话方式（语音，视频）
        this.activePhoneReceive = true
      } else {
        this.activeVideoReceive = true
      }
    }
  },
  created () {
    window.addEventListener('keyup', (e) => {
      if (e.keyCode === 13) {
        this.send()
      }
    })
    API.users.getChatList({toId: this.loginUser._id}, (responseData) => { // 获取聊天对象列表
      if (responseData.code === 200) {
        console.log(responseData.data.length)
        if (responseData.data.length !== 0) { // 已经存在聊天对象
          var friendList = responseData.data
          if (this.$route.params.chatUser !== undefined) { // 判断是否通过视频详情等页面跳转过来，路由中携带的聊天对象信息
            let i = 0
            while (i < friendList.length) { // 判断聊天对象列表中是否存在该对象
              if (friendList[i].id1 === this.$route.params.chatUser._id || friendList[i].id2 === this.$route.params.chatUser._id) {
                var currentFriend = friendList[i]
                friendList.splice(i, 1)
                friendList.unshift(currentFriend) // 存在的话就将该对象移动到聊天对象列表的第一个
                this.friendList = friendList
                this.exist = true // 存在
                break
              }
              i++
            }
            setTimeout(() => {
              if (!this.exist) { // 不存在
                let user1 = {
                  userName: this.$route.params.chatUser.userName,
                  userAvatar: this.$route.params.chatUser.userAvatar
                }
                let user2 = {
                  userName: this.loginUser.userName,
                  userAvatar: this.loginUser.userAvatar
                }
                let friend = {id1: this.$route.params.chatUser._id, user1: user1, id2: this.loginUser._id, lastMessage: '', time: '', user2: user2}
                this.friendList.unshift(friend) // 聊天对象列表中不存在路由携带对象，就将该对象添加在聊天对象的第一个
              }
            }, 0)
          } else {
            this.friendList = responseData.data
          }
        } else { // 不存在聊天对象就将路由中的对象添加至聊天对象
          if (this.$route.params.chatUser !== undefined) {
            let user1 = {
              userName: this.$route.params.chatUser.userName,
              userAvatar: this.$route.params.chatUser.userAvatar
            }
            let user2 = {
              userName: this.loginUser.userName,
              userAvatar: this.loginUser.userAvatar
            }
            let friend = {id1: this.$route.params.chatUser._id, user1: user1, id2: this.loginUser._id, lastMessage: '', time: '', user2: user2}
            this.friendList.push(friend)
          }
        }
      }
    }, (err) => {
      console.log(err)
    })
    setTimeout(() => { // 将这段代码放在获取用户聊天列表后执行
      if (this.friendList.length !== 0) {
        this.chatWith(this.friendList[0], 0) // 默认选择第一个聊天
        rtm.on('ChannelMessage', ({channelName, args}) => { // 获取1v1聊天信息
          const [message, memberId] = args
          let params = {toId: this.loginUser._id, userName: this.loginUser.userName, userAvatar: this.loginUser.userAvatar, message: message.text, fromId: memberId, time: new Date().getTime()}
          this.messageList.push(params) // 将信息展示在页面中
          this.text = ''
          this.$nextTick(() => { // 使用nextTick()更新dom
            var ele = document.getElementsByClassName('message-list')[0]
            ele.scrollTop = ele.scrollHeight
          })
        })
        rtm.on('RemoteInvitationReceived', (remoteInvitation) => { // 当发起语音或者视频聊天时，callee在message页面
          this.receiveInvitation(remoteInvitation)
        })
      }
    }, 1500)
  },
  mounted () {
    if (rtm.remoteInvitation !== null) { // callee通过全局提示点击跳转至message页面，先得判断是否存在邀请
      this.receiveInvitation(rtm.remoteInvitation)
    }
  },
  beforeDestroy () {
    rtmMethods.rtmLeave(this.params, rtm) // 离开频道
    if (this.rtc.client !== null) { // 存在rtc client，全部清除
      this.leave()
    }
  }
}
</script>
<style lang="less" scoped>
.message{
  background: black;
  min-height: 100%;
}
.chat{
  margin-top: 20px;
  width:80%;
  display: flex;
  display: -webkit-flex;
  flex-direction: column;
  margin-left: 10%;
}
.chat-body{
  flex: 7;
  display: flex;
  flex-direction: row;
.chat-left{
  //flex:1;
  height: 520px;
  display: flex;
  flex-direction: column;
  font-size: 13px;
  background-color: rgb(27, 27, 36);//white;
  border-radius: 5px 0 0 5px;
  .chat-header{
    height: 40px;
    flex: 1;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    input{
        line-height: 25px;
        background-color: rgb(66, 66, 66);
        border: none;
        width: 200px;
        color: white;
        border-radius: 5px;
        outline: none;
    }
  }
  .left-none{
    color:rgb(212, 212, 212);
    height: 470px;
    width: 230px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
  }
  .person-list{
    margin-top: 10px;
    height: 470px;
    width: 230px;
    overflow-y: scroll;
    overflow-x: hidden;
    .per-person{
      margin-top:5px;
      flex: 1;
      display: flex;
      height: 70px;
      //flex-direction: row;
      //background-color: white;
      color: rgb(212, 212, 212);
      cursor: pointer;
      border-bottom: 1px solid rgb(112, 112, 112);
      .selected{
          height: 100%;
          width: 3px;
          background: #0B8DD9;
      }
      .unselected{
          height: 100%;
          width: 3px;
          background: transparent;
      }
      .item{
          //width: 100%;
          width: 210px;
          margin: 5px;
          //padding-top: 5px;
      }
      .top{
        height: 40px;
        display: flex;
        flex-direction: row;
        img{
          width: 50px;
          height: 50px;
          background: black;
          border-radius: 50%;
          margin:5px 5px 0 5px;
        }
        p{
          margin-left: 5px;
          width:170px;
          height: 40px;
          font-size: 15px;
          font-weight: bold;
          text-overflow: ellipsis;
          overflow:hidden;
          white-space:nowrap;
          margin-top: 7px;
        }
      }
      .per-message{
        width:150px;
        padding-left: 45px;
        text-overflow: ellipsis;
        overflow:hidden;
        white-space:nowrap;
        margin: 0 0 0 20px;
        color:gray;

      }
    }
  }
  .person-list::-webkit-scrollbar {
    display: none;
}
}
.chat-right{
  flex:3;
  height:520px;
  background-color:rgb(231, 231, 231);//#717171;
  flex-direction: column;
  display: flex;
  border-radius: 0 5px 5px 0;
  //margin-left: 20px;
  .message-header{
    height: 80px;
    flex: 1;
    display: flex;
    flex-direction: row;
    border-bottom: 1px solid white;
    .intro{
      flex: 1;
      flex-direction: row;
      display: flex;
      margin-left: 40px;
      margin-top: 10px;
      color: black;
      img{
        width: 70px;
        height: 70px;
        border-radius: 50%;
      }
      .tips{
        margin-left: 10px;
        width: 200px;
        .userName{
          margin-top: 5px;
          font-size: 20px;
        font-weight: bold;
        }
        .onlineStatus{
          color: rgb(90, 90, 90);
          font-size: 10px;
          margin-top: -5px;
        }
      }
    }
    .talk{
      width: 200px;
      display: flex;
      flex-direction: row;
      //margin-top: 20px;
      justify-content: center;
      align-items: center;
      .phone{
        display: flex;
        height: 30px;
        width: 50px;
        justify-content: center;
        align-items: center;
        background: #717171;
        color: white;
        font-size: 15px;
        border-radius: 5px;
        cursor: pointer;
      }
      .activePhone{
        background: #0B8DD9;
      }
      .video{
        margin-left: 10px;
        display: flex;
        height: 30px;
        width: 50px;
        justify-content: center;
        align-items: center;
        background: #717171;
        color: white;
        font-size: 15px;
        border-radius: 5px;
        cursor: pointer;
      }
      .activeVideo{
        background: #0B8DD9;
      }
      .setting{
        margin-left: 10px;
        display: flex;
        height: 30px;
        width: 50px;
        justify-content: center;
        align-items: center;
        background: #717171;
        color: white;
        font-size: 15px;
        border-radius: 5px;
        cursor: pointer;
      }
      .activeSetting{
        background: #0B8DD9;
      }
    }
  }
  .message-list{
    height: 320px;
    width: 98%;
    margin-left: 1%;
    overflow-y: scroll;
    overflow-x: hidden;
    font-size: 10px;
    flex: 3;
    background-color:rgb(231, 231, 231);//#717171;
    border-bottom: 1px solid white;
    .per-message-left{
      min-height: 40px;
      flex: 1;
      display: flex;
      flex-direction: row;
      margin:0 5px 5px 0;
      img{
        width: 35px;
        height:35px;
        border-radius: 50%;
        margin-left: 10px;
        margin-right: 5px;
      }
      .trangle{
        width: 0;
        height: 0;
        margin-top: 10px;
        border-top:5px solid transparent;
        border-left:5px solid transparent;
        border-bottom:5px solid transparent;
        border-right:5px solid white;//lightblue;
      }
      .message{
        max-width: 70%;
        background-color: white;//lightblue;
        margin-top: 10px;
        padding: 7px;
        border-radius: 0 5px 5px 5px;
        text-align: justify;
        text-justify: newspaper;
        word-break: break-all;
      }
    }
    .per-message-right{
      min-height: 40px;
      flex: 1;
      display: flex;
      flex-direction: row-reverse;
      margin: 5px 0;
      img{
        width: 35px;
        height:35px;
        background-color: red;
        border-radius: 50%;
        margin-right: 10px;
        //margin-right: 5px;
      }
      .trangle{
        width: 0;
        height: 0;
        margin-top: 10px;
        border-top:5px solid transparent;
        border-right:5px solid transparent;
        border-bottom:5px solid transparent;
        border-left:5px solid lightgreen;
      }
      .message{
        max-width: 70%;
        background-color: lightgreen;
        margin-top: 10px;
        padding: 7px;
        border-radius:  5px 0 5px 5px;
        text-align: justify;
        text-justify: newspaper;
        word-break: break-all;
      }
    }
  }
  .message-list::-webkit-scrollbar {
    display: none;
  }
  .send-bar{
    width: 100%;
    //margin-left: 1%;
    flex:1;
    background-color: rgb(231, 231, 231);
    border-radius: 5px;
    .input-value{
      width: 99%;
      height: 80px;
      border:none;
      outline: none;
      background-color: transparent;
    }
    .input-value::-webkit-scrollbar {
    display: none;
  }
    .button-group{
      float: right;
      font-size: 10px;
      margin-right: 2%;
      button{
        //width: 50px;
        font-size: 10px;
        margin-left: 5px;
        //border-radius: 10px;
        //border:none;
        //height:27px;
        //background: #0B8DD9;
        //color: white;
        //font-size: 20px;
        cursor: pointer;
      }
    }
  }
}
.right-none{
  flex:3;
  height:520px;
  background-color:rgb(231, 231, 231);//#717171;
  flex-direction: column;
  display: flex;
  border-radius: 0 5px 5px 0;
  justify-content: center;
  align-items: center;
  color: rgb(27, 27, 36);
}
}
</style>
