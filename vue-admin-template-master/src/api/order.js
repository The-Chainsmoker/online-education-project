import request from '@/utils/request'
export default {
  //分页条件查询order
  getOrderListPage(page, limit, OrderQuery) {
    return request({
      url: `/eduorder/order/pageOrder/${page}/${limit}`,
      method: 'post',
      data: OrderQuery
    })
  },

  //删除order
  deleteOrder(id) {
    return request({
      url: `/eduorder/order/remove/${id}`,
      method: 'delete'
    })
  }
}