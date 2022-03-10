<template>
  <div class="app-container">
    <el-form label-width="120px">

      <el-form-item label="Banner标题">
        <el-input v-model="banner.title" />
      </el-form-item>

      <el-form-item label="Banner排序">
        <!--min="0"默认排序为0(写入有警告)-->
        <el-input-number v-model="banner.sort" controls-position="right" />
      </el-form-item>

      <!-- Banner封面-->
      <el-form-item label="Banner封面">
        <el-upload :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" :action="BASE_API+'/eduoss/fileoss'" class="avatar-uploader">
          <img :src="banner.imageUrl" class="imgContain">
        </el-upload>
      </el-form-item>

      <el-form-item label="Banner链接地址">
        <!--min="0"默认排序为0(写入有警告)-->
        <el-input v-model="banner.linkUrl" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import bannerApi from '@/api/banner'

export default {
  data() {
    return {
      //对象不加属性也可以,因为v-model双向绑定可重新增加属性
      min: '0',
      banner: {
        title: '',
        imageUrl: '', //图片地址
        linkUrl: '', //链接地址
        sort: 0,
      },
      BASE_API: process.env.BASE_API, //获取dev.env.js里面地址

      saveBtnDisabled: false, // 保存按钮是否禁用
    }
  },
  created() {
    //初次使用组件的时候调用
    this.init()
  },
  watch: {
    //监听路由发生变化,方法就会执行(解决组件复用时,不再次创建的问题)
    $route(to, from) {
      this.init()
    },
  },
  methods: {
    //图片上传之前
    beforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isLt2M
    },
    //图片上传成功
    handleAvatarSuccess(res, file) {
      this.banner.imageUrl = res.data.url
    },

    //判断是否是添加Banner的路由,则清空表单
    init() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getInfo(id)
      } else {
        const imageUrl = '../../../static/snow.png'
        const sort = 0
        this.banner = {
          imageUrl,
          sort,
        }
      }
    },
    //判断分开执行添加和修改Banner功能
    saveOrUpdate() {
      if (this.banner.id) {
        //判断urlthis.teacher.id的值
        //修改
        this.updateBanner()
      } else {
        //添加讲师不能没内容
        if (Object.keys(this.banner).length !== 0) {
          this.saveBanner()
        }
      }
    },

    //添加讲师的方法
    saveBanner() {
      bannerApi
        .addBanner(this.banner)
        .then((res) => {
          //提示信息
          this.$message({
            type: 'success',
            message: '添加成功',
          })
          //返回列表界面
          this.$router.push({ path: '/banner/list' })
          //两种写法this.$router.push("/teacher/table")
        })
        .catch(() => {
          //提示框
          this.$message({
            type: 'warning',
            message: '请输入完整内容',
          })
        })
    },

    //找到Banner记录
    getInfo(id) {
      bannerApi.getByIdBanner(id).then((res) => {
        this.banner = res.data.item
      })
    },

    //修改Banner记录
    updateBanner() {
      bannerApi.updateBanner(this.banner).then((response) => {
        this.$message({
          type: 'success',
          message: '修改成功',
        })

        this.$router.push({ path: '/banner/list' })
      })
    },
  },
}
</script>

<style scoped>
.imgContain {
  width: 400px;
  height: 230px;
}

.tinymce-container {
  line-height: 29px;
}
</style>
