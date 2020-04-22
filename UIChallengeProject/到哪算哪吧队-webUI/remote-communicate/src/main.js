import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import Message from './components/message/index'

Vue.prototype.$my_message = Message.install;

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
