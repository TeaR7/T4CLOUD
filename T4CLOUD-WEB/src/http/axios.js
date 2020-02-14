import axios from 'axios';

axios.defaults.timeout = 10000;

//请求拦截
axios.interceptors.request.use(config => {
    return config
}, error => {
    return Promise.reject(error)
})

//响应拦截
axios.interceptors.response.use(response => {
    return response.data;
}, error => {
    // 请求失败
    if (error && error.response) {
        switch (error.response.status) {
            case 400:
                console.log('status:' + error.response.status);
                break
            case 401:
                console.log('status:' + error.response.status);
                break
            default:
                return Promise.reject(error);
        }
    }

})