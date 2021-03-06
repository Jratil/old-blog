import api from './api'
import qs from 'querystring'

import axios from 'axios'

const baseURL = ''
const instance = axios.create({
    baseURL
})

// 添加请求拦截器
instance.interceptors.request.use(
    function(config) {
        // 在发送请求之前做些什么
        return config
    },
    function(error) {
        // 对请求错误做些什么
        return Promise.reject(error)
    }
)

// 添加响应拦截器
instance.interceptors.response.use(
    function(response) {
        // 对响应数据做点什么
        if (response.status === 200) {
            //  修改字段名 message 为 msg，防止与 antd message 组件命名冲突
            const { message, ...otherData } = response.data
            return { msg: message, ...otherData }
        }

        return response
    },
    function(error) {
        // 对响应错误做点什么
        return Promise.reject(error)
    }
)

const services = {}

Object.keys(api).forEach(key => {
    let arr = api[key].split(' ')
    let isPost = arr[0] === 'POST'
    let method = isPost ? 'POST' : 'GET'
    let url = isPost ? arr[1] : arr[0]
    services[key] = payload => {
        return instance({ method, data: qs.stringify(payload), url })
    }
})

export default services
