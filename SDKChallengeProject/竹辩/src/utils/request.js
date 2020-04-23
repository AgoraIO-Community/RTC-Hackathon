import axios from 'axios';

const config = {
    baseURL: 'http://127.0.0.1:3001',
    timeout: 30000,
    headers: {
        'Content-Type': 'application/json'
    }
};

const tokenWrapper = (data, headers) => {
    //if (localStorage.getItem('token')) {
    //    console.warn(localStorage.getItem('token'))
        headers.authorization = localStorage.getItem('token');
    //} else {
    //    console.warn('没有找到token');
    //}

    return JSON.stringify(data);
};

export const request = axios.create({
    ...config,
    transformRequest: [tokenWrapper]
});

export default request;
