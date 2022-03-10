import request from '@/utils/request'
export default {
  //分页查讲师
  getPlayAuth(id) {
    return request({
      url: `/eduvod/video/getPlayAuth/${id}`,
      method: 'get'
    })
  },
}