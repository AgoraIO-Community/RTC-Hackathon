//所有获取手机验证码的接口

import http from './interface'


/**
 * 获取更新手机号的验证码
 * phone	手机号
 */
export const updatePhone = (data) => {
    return http.request({
        url: '/home/v1/code/update_phone',
		method:'get',
        data,
    })
}

export default {
	updatePhone
}
