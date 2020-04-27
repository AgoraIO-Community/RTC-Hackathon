import Vue from 'vue'
import Router from 'vue-router'
import Login from '../view/login/index' // 登录
import Register from '../view/register/index' // 注册
import ForgetPassword from '../view/forgetPassword/index' // 忘记密码
import Index from '../view/index/index' // 主页 封面展示
import IndexPage from '../view/index/indexPage' // 首页
import Message from '../view/message/index.vue' // 信息列表

import BasicInfo from '../view/userInfo/card/basicInfoPage' // 用户信息
import Follows from '../view/userInfo/card/followsPage' // 用户的支持列表
import Supports from '../view/userInfo/card/supportsPage' // 支持用户的列表

import UserLive from '../view/userInfo/live/index' // 开启直播
import LiveRoom from '../view/liveRoom/liveroom' // 直播间

import Record from '../view/userInfo/record/recordPage' // 录制

import UploadVideos from '../view/userInfo/record/uploadPage' // 上传
import RecordVideos from '../view/userInfo/record/myvideospage' // 用户的视频
import RecordSetting from '../view/userInfo/record/settingPage' // 设置
import RecordVideoPlay from '../view/userInfo/record/playVideoPage' // 播放视频

import AllVideos from '../view/allVideos/index' // 所有视频
import AllLives from '../view/allLives/index' // 所有直播
import PlayVideo from '../view/playVideo/index' // 播放视频

Vue.use(Router)

const router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index,
      meta: {
        keepAlive: true
      }
    },
    {
      path: '/index',
      name: 'IndexPage',
      component: IndexPage,
      meta: {
        keepAlive: true
      }
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      meta: {
        keepAlive: true
      }
    },
    {
      path: '/register',
      name: 'Register',
      component: Register,
      meta: {
        keepAlive: true
      }
    },
    {
      path: '/forgetPassword',
      name: 'forgetPassword',
      component: ForgetPassword,
      meta: {
        keepAlive: true
      }
    },
    {
      path: '/message',
      name: 'message',
      component: Message,
      meta: {
        requireAuth: true,
        keepAlive: true
      }
    },
    {
      path: '/user/userinfo',
      name: 'userinfo',
      component: BasicInfo,
      meta: {
        requireAuth: true,
        keepAlive: true
      }
    },
    {
      path: '/user/follows',
      name: 'userFollows',
      component: Follows,
      meta: {
        requireAuth: true,
        keepAlive: true
      }
    },
    {
      path: '/user/supports',
      name: 'userSupports',
      component: Supports,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/user/live',
      name: 'userlive',
      component: UserLive,
      meta: {
        requireAuth: true,
        keepAlive: true
      }
    },
    {
      path: '/user/liveroom', // 添加id
      name: 'liveroom',
      component: LiveRoom
    },
    {
      path: '/user/record', // 视频录制
      name: 'record',
      component: Record,
      meta: {
        requireAuth: true,
        keepAlive: true
      }
    },
    {
      path: '/user/record/upload', // 录制的所有视频
      name: 'uploadVideos',
      component: UploadVideos,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/user/record/myvideos', // 录制的所有视频
      name: 'recordVideos',
      component: RecordVideos,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/user/record/setting', // 录制设置
      name: 'RecordSetting',
      component: RecordSetting,
      meta: {
        requireAuth: true,
        keepAlive: true
      }
    },
    {
      path: '/user/record/playvideo', // 录制的视频播放
      name: 'recordVideoPlay',
      component: RecordVideoPlay,
      meta: {
        requireAuth: true
      }
    },
    {
      path: '/allvideos',
      name: 'allvideos',
      component: AllVideos,
      meta: {
        keepAlive: true
      }
    },
    {
      path: '/alllives',
      name: 'alllives',
      component: AllLives,
      meta: {
        keepAlive: true
      }
    },
    {
      path: '/playVideo',
      name: 'playVideo',
      component: PlayVideo
    }
  ]
})
router.beforeEach((to, from, next) => {
  if (to.meta.requireAuth === true) {
    if (!localStorage.getItem('loginUser')) {
      next({
        path: '/login'
      })
    } else {
      return next()
    }
  } else {
    return next()
  }
})
export default router
