// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios';
import store from '@/vuex/store'
import { mapState,mapMutations,mapGetters,mapActions } from "vuex";

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.prototype.HOST = 'http://localhost:8081';
Vue.prototype.api= '/api';

//前端的axios添加withCredentials属性
axios.defaults.withCredentials=true;
Vue.prototype.$axios = axios;

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store,
}).$once('initUser',function(){
  this.$store.commit('setUser');
  console.log("initUser");
}).$once('initSocket',function(){
  this.$store.commit('newSocket');
  console.log("initSocket");
})
.$emit('initUser').$emit('initSocket')
