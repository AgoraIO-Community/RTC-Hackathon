import http from './interface'
const CryptoJS = require("crypto-js");

//手机号登录
export const login = (data) => {
    return http.request({
        url: '/home/v1/login',
		method:'POST',
        data,
    })
}

//退出登录
export const logout = () => {
    return http.request({
        url: '/home/v1/logout',
		method:'GET',
    })
}

//使用手机号注册账户
export const phoneReg = (data) => {
    return http.request({
        url: '/home/v1/reg/phone',
		method:'POST',
        data,
    })
}


/**
 * 修改密码
 * oldPwd	原密码
 * newPwd	新密码
 */
export const updatePassword = (data) => {
    return http.request({
        url: '/home/v1/password/update',
		method:'POST',
        data,
    })
}

/**
 * 密码验证
 * oldPwd	原密码
 * newPwd	新密码
 */
export const passwordValidation = (data) => {
    return http.request({
        url: '/home/v1/password/validation',
		method:'POST',
        data,
    })
}

/**
 * 检查是否登录
 * oldPwd	原密码
 * newPwd	新密码
 */
export const hasLogin = () => {
    return http.request({
        url: '/home/v1/user/islogin',
		method:'GET'
    })
}


/**
 * 更新手机号
 * phone	手机号
 * code		手机验证码
 */
export const updatePhone = (data) =>{
	return http.request({
	    url: '/home/v1/user/phone/update',
		method:'POST',
	    data,
	})
}

export default {
	login,
	logout,
	phoneReg,
	updatePassword,
	passwordValidation,
	updatePhone,
	hasLogin
}

