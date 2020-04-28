import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        userData:'',
        hasLogin: false
    },
    mutations: {
		login(state, provider) {
			state.hasLogin = true;
			state.userData = provider;
			 //缓存用户登陆状态
			uni.setStorageSync('userData',provider) 
		},
		logout(state) {
			state.hasLogin = false;
			state.userData = {};
			uni.removeStorageSync('userData')
		},
		historyUser(state, provider) {
			//缓存为历史登录用户
			uni.setStorageSync('historyUser',provider);
		},
    },
    actions:{
        setUserData:function(context,obj){
            context.commit('login',obj)
        },
		logout:function(context){
		    context.commit('logout')
		},
		setHistoryUser:function(context,obj){
		    context.commit('historyUser',obj)
		},
    }
})

export default store