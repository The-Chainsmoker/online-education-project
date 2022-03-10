import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import cookie from 'js-cookie'
// 创建axios实例
const service = axios.create({
  // baseURL: 'http://localhost:9001', 
  baseURL: 'http://localhost:8222', // api的base_url
  timeout: 20000 // 请求超时时间
})

//请求拦截(网络请求前执行)
service.interceptors.request.use(
  config => {//confi是配网络置请求信息
    //debugger
    //把cookie的guli_token都放到请求头里
    if (cookie.get('guli_token')) {
      //headers是内置对象
      config.headers['token'] = cookie.get('guli_token')
    }
    return config //放行返回config
  },
  err => {
    return Promise.reject(err);
  })

//响应拦截(网络请求后执行)
service.interceptors.response.use(
  response => {
    //debugger
    // if (response.data.code == 28004) {
    //   console.log("response.data.resultCode是28004")
    //   // 返回 错误代码-1 清除ticket信息并跳转到登录页面
    //   //debugger
    //   window.location.href = "/login"
    //   return
    // } else {
    //   if (response.data.code !== 20000) {
    //     //25000：订单支付中，不做任何提示
    //     if (response.data.code != 25000) {
    //       Message({
    //         message: response.data.message || 'error',
    //         type: 'error',
    //         duration: 5 * 1000
    //       })
    //     }
    //   } else {
    //     return response.data //放行返回res
    //   }
    // }

    return response.data
  },
  err => {
  }
)

export default service