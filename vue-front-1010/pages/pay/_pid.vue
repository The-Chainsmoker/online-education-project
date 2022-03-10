<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container  pay">
      <div class="checkout-tit">
        <h4 class="fl tit-txt"><span class="success-icon"></span><span class="success-info">订单提交成功，请您及时付款！订单号：{{payObj.out_trade_no}}</span>
        </h4>
        <span class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{payObj.total_fee}}</em></span>
        <div class="clearfix"></div>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <p class="red">请使用微信扫一扫。</p>
          <div class="fl code">
            <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
            <!-- <qriously value="weixin://wxpay/bizpayurl?pr=R7tnDpZ" :size="338"/> -->
            <qriously :value="payObj.code_url" :size="338" />
            <div class="saosao">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>

          </div>

        </div>
        <div class="clearfix"></div>
        <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->

      </div>
    </div>
  </div>
</template>

<script>
import ordersApi from '@/api/order'
import courseApi from '@/api/course'
export default {
  //生成支付二维码
  asyncData({ params, error }) {
    return ordersApi.createNatvie(params.pid).then((response) => {
      console.log(response)
      return {
        payObj: response.data,
      }
    })
  },
  data() {
    return {
      timer1: '', //定时器
      coureInfo: {}, //课程信息
    }
  },
  //每隔三秒调用一次查询订单状态的方法
  mounted() {
    this.timer1 = setInterval(() => {
      //设置定时器
      this.queryPayStatus(this.payObj.out_trade_no)
    }, 2000)
  },
  destroyed() {
    //离开页面清除定时器
    clearInterval(this.timer1)
  },

  methods: {
    //
    queryPayStatus(orderNo) {
      ordersApi.queryPayStatus(orderNo).then((response) => {
        if (response.data.map.trade_state == 'SUCCESS') {
          //支付成功，清除定时器
          clearInterval(this.timer1)
          //增加课程的购买数量
          this.updateCourse()

          //提示
          this.$message({
            type: 'success',
            message: '支付成功!',
            duration: 3 * 1000,
          })

          //跳转回到课程详情页面
          this.$router.push({ path: '/course/' + this.payObj.course_id })
        }
      })
    },
    //增加课程的购买数量
    updateCourse() {
      courseApi.getCourseInfoFront(this.payObj.course_id).then((response) => {
        this.coureInfo = response.data.CourseInfo
        this.coureInfo.buyCount++
        courseApi.updateCourseInfoFront(this.coureInfo).then((response) => {})
      })
    },
  },
}
</script>

<style  scoped>
</style>
