function sendRequest (url, successCallback, errorCallback, args) { // 发送请求
  const { method, params } = args
  url = `${url}`
  fetch(url, {
    method: method,
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(params)
  })
    .then((response) => response.json())
    .then((responseData) => {
      if (responseData.error) { errorCallback(responseData) } else { successCallback(responseData) }
    })
    .catch((error) => {
      errorCallback(error)
    })
}
function getData (url, successCallback, errorCallback, args) { // 获取数据
  const { method } = args
  url = `${url}`
  fetch(url, {
    method: method,
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    }
  })
    .then((response) => response.json())
    .then((responseData) => {
      if (responseData.error) { errorCallback(responseData) } else { successCallback(responseData) }
    })
    .catch((error) => {
      errorCallback(error)
    })
}
function sendImage (url, successCallback, errorCallback, args) { // 上传头像
  const { method, params } = args
  url = `${url}`
  let formData = new FormData()
  formData.append('img', params)
  fetch(url, {
    method: method,
    headers: {
      // 'Content-Type': 'multipart/form-data'
    },
    body: formData
  })
    .then((response) => response.json())
    .then((responseData) => {
      if (responseData.error) { errorCallback(responseData) } else { successCallback(responseData) }
    })
    .catch((error) => {
      errorCallback(error)
    })
}
function sendPoster (url, successCallback, errorCallback, args) { // 上传视频封面
  const { method, params } = args
  url = `${url}`
  let formData = new FormData()
  formData.append('img', params.poster)
  formData.append('title', params.title)
  formData.append('_id', params._id)
  fetch(url, {
    method: method,
    headers: {
    },
    body: formData
  })
    .then((response) => response.json())
    .then((responseData) => {
      if (responseData.error) { errorCallback(responseData) } else { successCallback(responseData) }
    })
    .catch((error) => {
      errorCallback(error)
    })
}
function uploadVideo (url, successCallback, errorCallback, args) { // 上传视频
  const { method, params } = args
  url = `${url}`
  let formData = new FormData()
  formData.append('video', params.video)
  formData.append('userId', params.userId)
  fetch(url, {
    method: method,
    headers: {
      // 'Content-Type': 'multipart/form-data'
    },
    body: formData
  })
    .then((response) => response.json())
    .then((responseData) => {
      if (responseData.error) { errorCallback(responseData) } else { successCallback(responseData) }
    })
    .catch((error) => {
      errorCallback(error)
    })
}
const users = {
  uploadVideo: (params, successCallback, errorCallback) => { // 上传视频
    uploadVideo('/api/users/uploadVideo', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getAppId: (params, successCallback, errorCallback) => { // 获取appId
    getData('/api/users/getAppId', successCallback, errorCallback, {
      method: 'GET',
      params: params
    })
  },
  getEmailVerify: (params, successCallback, errorCallback) => { // 获取邮箱验证码
    sendRequest('/api/users/emailVerify', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getCaptcha: (params, successCallback, errorCallback) => { // 获取验证码
    getData('/api/users/verifyCode', successCallback, errorCallback, {
      method: 'GET',
      params: params
    })
  },
  register: (params, successCallback, errorCallback) => { // 注册
    sendRequest('/api/users/register', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  resetPassword: (params, successCallback, errorCallback) => { // 密码重置
    sendRequest('/api/users/resetPassword', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  login: (params, successCallback, errorCallback) => { // 登录
    sendRequest('/api/users/login', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  saveChatUser: (params, successCallback, errorCallback) => { // 保存聊天对象
    sendRequest('/api/users/chat/saveChatUser', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getChatList: (params, successCallback, errorCallback) => { // 获取聊天对象列表
    sendRequest('/api/users/chat/getChatList1', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  uploadMessage: (params, successCallback, errorCallback) => { // 上传聊天
    sendRequest('/api/users/chat/uploadMessage', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  readMessage: (params, successCallback, errorCallback) => { // 读取信息
    sendRequest('/api/users/chat/readMessage', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getMessageHistory: (params, successCallback, errorCallback) => { // 获取聊天历史纪录
    sendRequest('/api/users/chat/getMessageHistory', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  saveUserInfo: (params, successCallback, errorCallback) => { // 保存用户信息
    sendRequest('/api/users/saveUserInfo', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  uploadUserAvatar: (params, successCallback, errorCallback) => { // 上传用户头像
    sendImage('/api/users/upload', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  support: (params, successCallback, errorCallback) => { // 支持该追梦人
    sendRequest('/api/users/support', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  unSupport: (params, successCallback, errorCallback) => { // 取消支持该追梦人
    sendRequest('/api/users/unsupport', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getSupportUserList: (params, successCallback, errorCallback) => { // 获取支持者列表
    sendRequest('/api/users/getMySupports', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getBackupUserList: (params, successCallback, errorCallback) => { // 获取支持用户的列表
    sendRequest('/api/users/getMyBackups', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  }
}
const record = {
  getResourceId: (params, successCallback, errorCallback) => { // 获取resourceId进行录制准备
    sendRequest('/api/users/getResourceId', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  startRecord: (params, successCallback, errorCallback) => { // 开始录制
    sendRequest('/api/users/startRecord', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  queryStatus: (params, successCallback, errorCallback) => { // 查询录制状态，获取视频url
    sendRequest('/api/users/queryRecordStatus', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  stopRecording: (params, successCallback, errorCallback) => { // 停止录制
    sendRequest('/api/users/stopRecording', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getRecordVideos: (params, successCallback, errorCallback) => { // 获取录制视频的信息
    sendRequest('/api/users/getRecordVideos', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  searchVideo: (params, successCallback, errorCallback) => { // 根据id查找某个视频
    sendRequest('/api/users/searchVideo', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  searchAllVideos: (params, successCallback, errorCallback) => { // 根据关键字查询
    sendRequest('/api/videos/searchAllVideos', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  updateVideoDetail: (params, successCallback, errorCallback) => { // 更新视频信息
    sendRequest('/api/users/updateVideoDetail', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  updateVideoDetailWithPoster: (params, successCallback, errorCallback) => { // 更新视频封面
    sendPoster('/api/users/updateVideoDetailWithPoster', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  publishVideo: (params, successCallback, errorCallback) => { // 发布视频
    sendRequest('/api/users/publishVideo', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  unpublishVideo: (params, successCallback, errorCallback) => { // 取消发布
    sendRequest('/api/users/unpublishVideo', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  deleteVideo: (params, successCallback, errorCallback) => { // 删除视频
    sendRequest('/api/users/deleteVideo', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  }
}
const Video = {
  getAllVideos: (params, successCallback, errorCallback) => { // 获取所有视频
    sendRequest('/api/videos/getAllVideos', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getRecommandVideos: (params, successCallback, errorCallback) => { // 获取推荐视频
    sendRequest('/api/videos/getRecommandVideos', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  comment: (params, successCallback, errorCallback) => { // 评论视频
    sendRequest('/api/video/comment', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getAllComments: (params, successCallback, errorCallback) => { // 获取评论
    sendRequest('/api/video/getAllComments', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getCarouselVideo: (params, successCallback, errorCallback) => { // 获取评论
    sendRequest('/api/videos/getCarouselVideo', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  }
}
const Live = {
  startLiving: (params, successCallback, errorCallback) => { // 保存开播信息
    sendRequest('/api/live/startLiving', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  stopLiving: (params, successCallback, errorCallback) => { // 保存关播信息
    sendRequest('/api/live/stopLiving', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  createLiveRoom: (params, successCallback, errorCallback) => { // 开通直播权限，获取房间号
    sendRequest('/api/users/live/createLiveRoom', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getLiveRoomId: (params, successCallback, errorCallback) => { // 开通直播权限，获取房间号
    sendRequest('/api/users/live/getLiveRoomId', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getRecommandLive: (params, successCallback, errorCallback) => { // 获取推荐直播
    sendRequest('/api/live/getRecommandLive', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getMyLiveRoomDetail: (params, successCallback, errorCallback) => { // 获取直播间信息
    sendRequest('/api/live/getMyLiveRoomDetail', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getLiveRoomDetail: (params, successCallback, errorCallback) => { // 获取直播间信息
    sendRequest('/api/live/getLiveRoomDetail', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  saveLiveRoomMessage: (params, successCallback, errorCallback) => { // 保存直播间聊天信息
    sendRequest('/api/live/saveLiveRoomMessage', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getLiveRoomMessage: (params, successCallback, errorCallback) => { // 获取直播间聊天信息
    sendRequest('/api/live/getLiveRoomMessage', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  getAllLives: (params, successCallback, errorCallback) => { // 获取所有直播
    sendRequest('/api/live/getAllLives', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  },
  searchAllLives: (params, successCallback, errorCallback) => { // 获取所有直播
    sendRequest('/api/live/searchAllLives', successCallback, errorCallback, {
      method: 'POST',
      params: params
    })
  }
}
const API = {
  users, record, Live, Video
}
export default API
