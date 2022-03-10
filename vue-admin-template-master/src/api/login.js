import request from '@/utils/request'

// 登录
export function login(username, password) {
  // debugger
  return request({
    url: '/eduservice/user/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

// 获取用户信息
export function getInfo(token) {
  return request({
    url: '/eduservice/user/info',
    method: 'get',
    params: { token }
  })
}

//登出
export function logout() {
  return request({
    url: '/eduservice/user/logout',
    method: 'post'
  })
}