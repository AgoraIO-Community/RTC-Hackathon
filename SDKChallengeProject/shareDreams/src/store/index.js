import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const state = { // 定义数据
  appId: '',
  phoneVerifyExpireTime: 0,
  loginUser: {
    userName: ''
  },
  token: '',
  rtc: {
    client: null,
    joined: false,
    published: false,
    localStream: null,
    remoteStreams: [],
    params: {}
  },
  live: { // 用户直播房间号
    roomId: ''
  },
  userMediaDevices: { // 保存用户设备信息
    cameraOptions: [],
    microphoneOptions: []
  },
  liveSetting: { // 用户直播选用设备信息
    cameraId: '',
    microphoneId: ''
  },
  recordSetting: { // 用户录制选用设备信息
    cameraId: '',
    microphoneId: ''
  },
  rtm: { // 实时消息
    phoneCall: { // rtm 语音通话
      remoteInvitation: null // 远程邀请
    }
  }
}

const mutations = { // 定义如何改变state
  saveAppId (state, status) {
    state.appId = status
  },
  savePhoneVerifyExpireTime (state, status) {
    state.phoneVerifyExpireTime = status
  },
  save (state, user) {
    state.loginUser = user
  },
  clear (state) {
    state.loginUser = {
      userName: ''
    }
  },
  rtcCreateClient (state, client) { // create rtc client and join in(host)
    state.rtc.client = client
  },
  rtcPublish (state, status) { // publish rtc stream,so other can join in(audio)
    state.rtc.published = status
  },
  rtcAddParams (state, params) {
    state.rtc.params = params
  },
  rtcJoin (state, status) {
    state.rtc.joined = status
  },
  rtcCreateLocalStream (state, localStream) {
    state.rtc.localStream = localStream
  },
  rtcClearRemoteStreams (state) {
    state.rtc.remoteStreams = []
  },
  saveRoomId (state, status) {
    state.live.roomId = status
  },
  saveLiveSetting (state, status) { // 保存用户直播选用的设备
    state.liveSetting.cameraId = status.cameraId
    state.liveSetting.microphoneId = status.microphoneId
  },
  deleteLiveSetting (state) { // 删除用户直播选用的设备
    state.liveSetting.cameraId = ''
    state.liveSetting.microphoneId = ''
  },
  saveRecordSetting (state, status) { // 保存用户录制视频选用的设备
    state.recordSetting.cameraId = status.cameraId
    state.recordSetting.microphoneId = status.microphoneId
  },
  deleteRecordSetting (state) { // 删除用户录制视频选用的设备
    state.recordSetting.cameraId = ''
    state.recordSetting.microphoneId = ''
  },
  addRtmRemoteInvitation (state, status) {
    state.rtm.phoneCall.remoteInvitation = status
  },
  deleteRtmRemoteInvitation (state) {
    state.rtm.phoneCall.remoteInvitation = null
  },
  addUserMicroDevices (state, status) { // 添加用户音频设备
    state.userMediaDevices.microphoneOptions.push(status)
  },
  addUserCameraDevices (state, status) { // 添加用户视频设备
    state.userMediaDevices.cameraOptions.push(status)
  },
  updateUserMediaOptions (state, status) {
    state.userMediaDevices = status
  }

}
const actions = { // 触发mutations
  emitSaveAppId (context, status) {
    context.commit('saveAppId', status)
  },
  emitSavePhoneVerifyExpireTime (context, status) {
    context.commit('savePhoneVerifyExpireTime', status)
    console.log(status)
  },
  saveUser (context, user) {
    context.commit('save', user)
  },
  clearUser (context) {
    context.commit('clear')
  },
  emitRtcCreateClient (context, client) { // update client
    context.commit('rtcCreateClient', client)
  },
  emitRtcPublish (context, status) { // upload client publish status
    context.commit('rtcPublish', status)
  },
  emitRtcAddParams (context, params) {
    context.commit('rtcAddParams', params)
  },
  emitRtcJoin (context, status) {
    context.commit('rtcJoin', status)
  },
  emitRtcCreateLocalStream (context, localStream) {
    context.commit('rtcCreateLocalStream', localStream)
  },
  emitRtcClearRemoteStreams (context) {
    context.commit('rtcClearRemoteStreams')
  },
  emitSaveRoomId (context, params) { // 保存直播房间号，用于初步判断用户是否有权限，是否可以路由开启？
    context.commit('saveRoomId', params)
  },
  emitSaveLiveSetting (context, params) { // 保存直播媒体设置
    context.commit('saveLiveSetting', params)
  },
  emitDeleteLiveSetting (context) { // 删除直播设置
    context.commit('deleteLiveSetting')
  },
  emitSaveRecordSetting (context, params) { // 保存录制媒体设置
    context.commit('saveRecordSetting', params)
  },
  emitDeleteRecordSetting (context) { // 删除录制设置
    context.commit('deleteRecordSetting')
  },
  emitAddRtmRemoteInvitation (context, params) { // rtm 新增远程语音通话（解决用户刷新页面问题）
    context.commit('addRtmRemoteInvitation', params)
  },
  emitDeleteRtmRemoteInvitation (context) { // rtm 清除远程语音通话
    context.commit('addRtmRemoteInvitation')
  },
  emitAddUserMicroDevices (context, params) { // 添加音频设备
    context.commit('addUserMicroDevices', params)
  },
  emitAddUserCameraDevices (context, params) { // 添加视频设备
    context.commit('addUserCameraDevices', params)
  },
  emitUpdateUserMediaOptions (context, params) {
    context.commit('updateUserMediaOptions', params)
  }
}
const store = new Vuex.Store({
  state,
  mutations,
  actions
})

export default store
