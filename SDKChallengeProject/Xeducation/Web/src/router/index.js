import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../components/Login.vue'
import Home from '../components/Home.vue'
import Nav from '../components/Nav.vue'
import Course from '../components/Course.vue'
import Blackboard from '../components/Blackboard.vue'
import Attention from '../components/Attention.vue'
import Me from '../components/Me.vue'
import Live from '../components/Livebroadcast.vue'
import Video from '../components/Video.vue'
Vue.use(VueRouter)

const routes = [
  { path: '/', redirect: '/login' },
  { path: '/login', component: Login },
  {
    path: '/nav',
    component: Nav,
    redirect: '/home',
    children: [
      // "主页"
      { path: '/home', component: Home },
      // "课程"
      {
        path: '/course',
        component: Course,
        redirect: '/blackboard/CS285',
        children: [
          { path: '/blackboard/:id', name:"blackboard", component: Blackboard },
        ]
      },
      // “专注”
      { path: '/attention', component: Attention },
      // “我的”
      { path: '/me', component: Me },
    ]
  },
  // "直播"
  { path: '/livebroadcast', name:"livebroadcast", component:Live},
  // "视频"
  { path: '/video',component:Video}
]

const router = new VueRouter({
  routes
})

router.beforeEach((to, from, next) => {
  if (to.path === '/login') return next()
  const tokenStr = window.sessionStorage.getItem('token')
  if (!tokenStr) return next('/login')
  return next()
})

export default router
