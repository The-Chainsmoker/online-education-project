import request from '@/utils/request'
export default {
  //生成订单
  createOrders(courseId) {
    return request({
      url: `/eduorder/order/createOrder/${courseId}`,
      method: 'post'
    })
  },
  //根据订单orderId查询订单信息
  getOrdersInfo(orderId) {
    return request({
      url: `/eduorder/order/getOrderInfo/${orderId}`,
      method: 'get',
    })
  },
  //根据订单orderId生成二维码
  createNatvie(orderNo) {
    return request({
      url: `/eduorder/paylog/createNative/${orderNo}`,
      method: 'get',
    })
  },
  //查询订单状态的方法
  queryPayStatus(orderNo) {
    return request({
      url: '/eduorder/paylog/queryPayStatus/' + orderNo,
      method: 'get'
    })
  }
}