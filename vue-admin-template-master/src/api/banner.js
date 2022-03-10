import request from '@/utils/request'
export default {
  //分页条件查询banner
  getBannerListPage(page, limit, BannerQuery) {
    return request({
      url: `/educms/banneradmin/pageBanner/${page}/${limit}`,
      method: 'post',
      data: BannerQuery
    })
  },
  //添加banner
  addBanner(banner) {
    return request({
      url: `/educms/banneradmin/addBanner`,
      method: 'post',
      data: banner
    })
  },
  //根据id查询banner
  getByIdBanner(id) {
    return request({
      url: `/educms/banneradmin/get/${id}`,
      method: 'get'
    })
  },
  //修改banner
  updateBanner(banner) {
    return request({
      url: `/educms/banneradmin/update`,
      method: 'post',
      data: banner
    })
  },
  //删除banner
  deleteBanner(id) {
    return request({
      url: `/educms/banneradmin/remove/${id}`,
      method: 'delete'
    })
  }
}