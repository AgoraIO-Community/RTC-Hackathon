import http from './interface'

/**
 * 检查是否有新版本
 * {version:1}
 */
export const upgrade = (data) => {
    return http.request({
        url: '/home/v1/app/upgrade',
		method:'GET',
        data,
    })
}



export default {
	upgrade,
}

