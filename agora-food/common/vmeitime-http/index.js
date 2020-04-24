import store from '@/store/index';

import config from '@/config.js'

import http from './interface'

import carpool from './carpool.js'

import user from './user.js'

import code from './code.js'

import app from './app.js'
/**
 * 将业务所有接口统一起来便于维护
 * 如果项目很大可以将 url 独立成文件，接口分成不同的模块
 * 
 */

//实例
/* export const goods = (data) => {
	//设置请求前拦截器
	http.interceptor.request = (config) => {
		config.header = {
			token: '12345647sdfads'
		}
	}
	//设置请求结束后拦截器
	http.interceptor.response = (response) => {
		//判断返回状态 执行相应操作
		console.log(response)
		return response;
	}
    return http.request({
		//baseUrl: 'https://unidemo.dcloud.net.cn/',
        url: '/home/v1/goods',
		method:'POST',
		dataType: 'json',
        data,
    })
} */

//------------------------------------------------------------------------------------------
//---------------------------------------   拦截器  ----------------------------------
//------------------------------------------------------------------------------------------
//服务器地址
http.config.baseUrl = config.server

//设置请求前拦截器
http.interceptor.request = (config) => {
    //添加通用参数
    config.header = {
        "token": uni.getStorageSync('userData').token
    }
	
}

//设置请求结束后拦截器
http.interceptor.response = (res) => {
	
	console.log("拦截",res);
	let code = res.statusCode;
	let err = null;
	
	//未连接到网络
	if(!code){
		uni.showModal({
			title: '提示',
			content: 'APP服务端未响应!',
			confirmColor: '#3CC51F',
			showCancel: false,
			success: function (msg) {
				if (msg.confirm) {
					
				}
			}
		});
		return res;
	}
	if(code != 200){
		//判断网络请求状态 执行相应操作
		switch(true){
			case code>=200 && code<300:
				break;
			case code>=300 && code<400:
				break;
			case code>=400 && code<500:
				err = "客户端请求错误!"
				break;
			case code==504:
				err = "网络请求超时!"
				break;
			case code>=500 && code<600:
				err = "服务器响应错误!"
				break;
		}
		if(err){
			uni.showModal({
				title: '提示',
				content: err,
				confirmColor: '#3CC51F',
				showCancel: false,
				success: function (msg) {
					if (msg.confirm) {
						
					}
				}
			});
			return res;
		}
	}
	
	
	//服务器返回错误的格式
	if(typeof resData.code == "undefined"){
		uni.showModal({
			title: '提示',
			content: '服务器返回错误',
			confirmColor: '#3CC51F',
			showCancel: false,
			success: function (msg) {
				if (msg.confirm) {
					
				}
			}
		});
		return res;
	}
	
	
    //判断返回状态 执行相应操作
	switch(res.data.code){
		//未登录
		case -1:
			let txt = "您还未登录"
			//如果本地登录状态时true则提示登录信息过期
			//如果本地登录状态是false则提示登录
			if(store.state.hasLogin){
				txt = "登录过期,请重新登录"
				//取消登录状态
				store.dispatch("logout");
			}
			console.log();
			uni.showModal({
				title: '',
				content: txt,
				confirmText: "去登录",
				confirmColor: '#3CC51F',
				success: function (msg) {
					if (msg.confirm) {
						uni.navigateTo({
							url: '/pages/user/login/login'
						});
					} else if (msg.cancel) {
						
					}
				}
			});
			break;
		//异常
		case 9999:
			uni.showModal({
				title: '提示',
				content: '远程端返回一个错误',
				confirmColor: '#3CC51F',
				showCancel: false,
				success: function (msg) {
					if (msg.confirm) {
						
					}
				}
			});
			break;
		default:
			break;
	}
	
    return res;
}

// 默认全部导出  import api from '@/common/vmeitime-http/'
export default {
	user,
	code,
	carpool,
	app
	
}
