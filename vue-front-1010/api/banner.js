import request from '@/utils/request'
export default {
  //查询所有banner
  getList() {
    return request({
      url: '/educms/bannerfront/getAllBanner',
      method: 'get'
    })
  }
}