import http from './interface'


//用户名登录
export const carpool = (data) => {
    return http.request({
        url: '/home/v1/carpool',
		method:'POST',
        data,
    })
}

export default {
	carpool
}
