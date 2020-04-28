import Vue from 'vue'
import App from './App'

import store from './store/index';
Vue.prototype.$store = store;

//配置
import config from './config.js'
Vue.prototype.$conf = config

Vue.prototype.$msg = (title, duration=1500, mask=false, icon='none')=>{
	//统一提示方便全局修改
	if(Boolean(title) === false){
		return;
	}
	uni.showToast({
		title,
		duration,
		mask,
		icon
	});
}

import api from './common/vmeitime-http/index.js'
Vue.prototype.$api = api

import appupgrade from './common/appupgrade.js'
Vue.prototype.$appUpgrade = appupgrade

Vue.config.productionTip = false

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
