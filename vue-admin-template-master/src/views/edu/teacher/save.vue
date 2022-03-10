<template>
  <div class="app-container">
    <el-form label-width="120px">

      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>

      <el-form-item label="讲师排序">
        <!--min="0"默认排序为0(写入有警告)-->
        <el-input-number v-model="teacher.sort" controls-position="right" />
      </el-form-item>

      <el-form-item label="讲师头衔">

        <el-select v-model="teacher.level" clearable placeholder="请选择">
          <!--
            数据类型一定要和取出的json中的一致，否则没法回填
            因此，这里value使用动态绑定的值，保证其数据类型是number
          -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>

      </el-form-item>

      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>

      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>

      <!-- 讲师头像：TODO -->
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">

        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
        </el-button>

        <!--
           v-show：是否显示上传组件
           :key：类似于id，如果一个页面多个图片上传控件，可以做区分
           :url：后台上传的url地址
           @close：关闭上传组件
           @crop-upload-success：上传成功后的回调 
           <input type="file" name="file"/>
      -->
        <image-cropper v-show="imagecropperShow" :width="300" :height="300" :key="imagecropperKey" :url="BASE_API+'/eduoss/fileoss'" field="file" @close="close" @crop-upload-success="cropSuccess" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
  components: {
    ImageCropper,
    PanThumb,
  },
  data() {
    return {
      //对象不加属性也可以,因为v-model双向绑定可重新增加属性
      min: '0',
      teacher: {
        name: '',
        sort: 0,
        level: 1,
        career: '',
        intro: '',
        avatar: '',
      },
      imagecropperShow: false, //上传弹框组件是否显示
      imagecropperKey: 0, //上传组件key值
      BASE_API: process.env.BASE_API, //获取dev.env.js里面地址

      saveBtnDisabled: false, // 保存按钮是否禁用
    }
  },
  created() {
    //初次使用组件的时候调用
    this.init()
  },
  watch: {
    //监听路由发生变化,方法就会执行(解决组件复用时,不再次创建)
    $route(to, from) {
      this.init()
    },
  },
  methods: {
    //关闭上传头像弹框
    close() {
      this.imagecropperShow = false

      //上传的组件初始化,防止停留在上传加载中
      this.imagecropperKey++
    },
    cropSuccess(data) {
      this.imagecropperShow = false
      //上传之后返回图片地址
      this.teacher.avatar = data.url

      //上传的组件初始化,防止停留在上传加载中
      this.imagecropperKey++
    },

    //判断是否是添加讲师的路由,则清空表单
    init() {
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id
        this.getInfo(id)
      } else {
        const avatar = 'https://guli-file-190513.oss-cn-beijing.aliyuncs.com/avatar/default.jpg'
        const sort = 0
        this.teacher = { avatar, sort }
      }
    },
    //判断分开执行添加和修改讲师功能
    saveOrUpdate() {
      if (this.teacher.id) {
        //判断urlthis.teacher.id的值
        //修改
        this.updateTeacher()
      } else {
        //添加

        //添加讲师不能没内容
        this.saveTeacher()
      }
    },

    //添加讲师的方法
    saveTeacher() {
      teacherApi
        .addTeacherList(this.teacher)
        .then((res) => {
          //提示信息
          this.$message({
            type: 'success',
            message: '添加成功',
          })
          //返回列表界面
          this.$router.push({ path: '/teacher/table' })
          //两种写法this.$router.push("/teacher/table")
        })
        .catch(() => {
          //提示框
          this.$message({
            type: 'warning',
            message: '请输入完整的内容',
          })
        })
    },

    //找到讲师记录
    getInfo(id) {
      teacherApi.getTeacherInfo(id).then((res) => {
        this.teacher = res.data.teacher
        // console.log(res)
      })
    },

    //修改讲师方法
    updateTeacher() {
      teacherApi.updateTeacherInfo(this.teacher).then((response) => {
        this.$message({
          type: 'success',
          message: '修改成功',
        })

        this.$router.push({ path: '/teacher/table' })
      })
    },
  },
}
</script>

<style>
</style>
