<template>
  <client-only>
    <div class="main">
      <div class="title">
        <a class="active" href="/login">登录</a>
        <span>·</span>
        <a href="/register">注册</a>
      </div>

      <div class="sign-up-container">
        <el-form ref="userForm" :model="user">

          <el-form-item class="input-prepend restyle" prop="mobile" :rules="[{ required: true, message: '请输入手机号码', trigger: 'blur' },{validator: checkPhone, trigger: 'blur'}]">
            <div class="content1">
              <el-input type="text" placeholder="手机号" v-model="user.mobile" />
              <i class="iconfont icon-phone" />
            </div>
          </el-form-item>

          <el-form-item class="input-prepend" prop="password" :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
            <div class="content2">
              <el-input type="password" placeholder="密码" v-model="user.password" />
              <i class="iconfont icon-password" />
            </div>
          </el-form-item>

          <div class="btn">
            <input type="button" class="sign-in-button" value="登录" @click="submitLogin()">
          </div>
        </el-form>
        <!-- 更多登录方式 -->
        <div class="more-sign">
          <h6>社交帐号登录</h6>
          <ul>
            <li><a id="weixin" class="weixin" target="_blank" href="http://localhost:8160/api/ucenter/wx/login"><i class="iconfont icon-weixin" /></a></li>
            <li><a id="qq" class="qq" target="_blank" href="#"><i class="iconfont icon-qq" /></a></li>
          </ul>
        </div>
      </div>
    </div>
  </client-only>

</template>

<script>
import '~/assets/css/sign.css'
import '~/assets/css/iconfont.css'
import cookie from 'js-cookie'
import loginApi from '@/api/login'
export default {
  layout: 'sign',

  data() {
    return {
      //储存登录手机号码和密码对象
      user: {
        mobile: '',
        password: '',
      },
      //用户信息用来访问其他服务
      loginInfo: {},
    }
  },

  methods: {
    //登录方法
    submitLogin() {
      //调用登录接口
      loginApi.submitLogin(this.user).then((response) => {
        //返回是成功数据时
        if (response.success) {
          //第一步 调用接口进行登录，返回token字符串(后端JWT加密的字符串)
          const token = response.data.token
          //第二步 获取token字符串放到cookie里面
          //第一个参数cookie名称，第二个参数值，第三个参数作用范围
          cookie.set('guli_token', token, { domain: 'localhost' })
          //第三步在拦截器把cookie的guli_token都放到请求头里

          //调用接口发送数据带有请求头获取用户个人信息放到cookie里
          loginApi.getLoginUserInfo().then((response) => {
            this.loginInfo = response.data.userInfo
            //把json转为string防止隐式调用Object对象的toString()方法出错
            let stringloginInfo = JSON.stringify(this.loginInfo)
            //将用户信息记录cookie
            cookie.set('guli_ucenter', stringloginInfo, { domain: 'localhost' })
            //提示注册成功
            this.$message({
              type: 'success',
              message: '登录成功',
            })
            // 跳转页面(原生js跳转)
            window.location.href = '/'
          })
        } else {
          //返回是失败数据时
          this.$message({
            type: 'error',
            message: response.message,
          })
        }
      })
    },
    checkPhone(rule, value, callback) {
      //debugger
      if (!/^1[34578]\d{9}$/.test(value)) {
        return callback(new Error('手机号码格式不正确'))
      }
      return callback()
    },
  },
}
</script>
<style scoped>
.el-form-item__error {
  z-index: 9999999;
}
.content1 {
  line-height: 23px;
}
.content2 {
  line-height: 20px;
}
</style>