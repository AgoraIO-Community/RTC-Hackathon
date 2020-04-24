import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import './assets/css/global.css'
import axios from 'axios'
import SvgIcon from '@/components/SvgIcon.vue'
import Timetable from '@/components/Timetable.vue'
import Notice from '@/components/Notice.vue'
import { vueBaberrage } from 'vue-baberrage'
// import Remotejs from '@/components/Remotejs.vue'

Vue.component('SvgIcon', SvgIcon)
Vue.component('Timetable', Timetable)
Vue.component('Notice', Notice)
Vue.use(vueBaberrage)

// Vue.component('remote-js', Remotejs)
axios.defaults.baseURL = 'https://mock.yonyoucloud.com/mock/5522/'
axios.interceptors.request.use(config => {
  config.headers.Authorization = window.sessionStorage.getItem('token')
  return config
})
Vue.config.productionTip = false
Vue.prototype.$http = axios

new Vue({
  router,
  // 路由挂载到vue实例
  render: h => h(App)
}).$mount('#app')
