import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import LeftNav from '@/components/LeftNav'
import Register from '@/components/Register'
import PulishMood from '@/components/PulishMood'
import PersonalMood from '@/components/PersonalMood'
import XingqiuMood from '@/components/XingqiuMood'
import Match from '@/components/Match'
import Message from '@/components/Message'
import Video from '@/components/video'
import Userinfo from '@/components/Userinfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'login',
      component: Login
    },
    {
      path: '/register',
      name: 'register',
      component: Register
    },
    {
      path: '/home',
      name: 'home',
      component: LeftNav
    },
    {
      path: '/initmes',
      name: 'initmes',
      component: Message,
    },
    {
      path: '/pulish',
      component: PulishMood
    },
    {
      path: '/personalMood',
      component: PersonalMood
    },
    {
      path: '/xingqiuMood',
      component: XingqiuMood
    },
    {
      path: '/matchList',
      component: Match,
      children: [
        {path:'mes/:id',name:'message',component: Message},
      ]
    },
    {
      path: '/video/:id',
      name:'video',
      component: Video
    },
    {
      path: '/showvideo/:username/:channel',
      name:'showvideo',
      component: Video
    },
    {
      path: '/userinfo',
      name:'userinfo',
      component: Userinfo
    },
  ]
})
