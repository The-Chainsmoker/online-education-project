<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例；机器学习项目课；从基础到搭建项目视频课程。专业名称注意大小写" />
      </el-form-item>

      <!-- 所属分类 TODO -->
      <el-form-item label="课程分类">
        <el-select v-model="courseInfo.subjectParentId" placeholder="一级分类" @change="subjectLevelOneChanged">
          <!-- 选中:value="subject.id"加入到courseInfo.subjectParentId  v-model是el-select绑定显示的内容的数据-->
          <el-option v-for="subject in subjectOneList" :key="subject.id" :label="subject.title" :value="subject.id" />

        </el-select>

        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option v-for="subject in subjectTwoList" :key="subject.id" :label="subject.title" :value="subject.id" />
        </el-select>

      </el-form-item>

      <!-- 课程讲师 TODO -->
      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">

          <el-option v-for="teacher in teacherList" :key="teacher.id" :label="teacher.name" :value="teacher.id" />

        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数" />
      </el-form-item>

      <!-- 课程简介 TODO -->
      <el-form-item label="课程简介">
        <el-input v-model="courseInfo.description" placeholder=" " />
      </el-form-item>

      <!-- 课程封面 TODO -->
      <!-- 课程封面-->
      <el-form-item label="课程封面">

        <el-upload :show-file-list="false" :on-success="handleAvatarSuccess" :before-upload="beforeAvatarUpload" :action="BASE_API+'/eduoss/fileoss'" class="avatar-uploader">
          <img :src="courseInfo.cover" class="imgContain">
        </el-upload>

      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元" /> 元
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'
export default {
  name: 'info',
  data() {
    return {
      saveBtnDisabled: false,
      courseInfo: {
        title: '',
        subjectId: '', //二级分类id
        subjectParentId: '', //一级分类id
        teacherId: '',
        lessonNum: 0,
        description: '',
        cover: '../../../static/snow.png',
        price: 0,
        courseId: '', //课程id
      },
      BASE_API: process.env.BASE_API, // 接口API地址
      teacherList: [], //讲师列表数据
      subjectOneList: [], //一级分类
      subjectTwoList: [], //二级分类
    }
  },
  components: {},
  watch: {
    //监听路由发生变化,方法就会执行(解决组件复用时,不再次创建)
    $route(to, from) {
      this.cleanCourseInfo()
    },
  },
  created() {
    //回显页面的时候this.$route.params.id才有值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      //调用根据id查询课程的方法
      this.getInfo()
    } else {
      //查询所有讲师记录
      this.getListTeacher()
      //查询所有的一级分类(有二级分类的数据)
      this.getOneSubject()
    }
  },
  methods: {
    //根据课程id查询(回显页面的时候)
    getInfo() {
      course.getCourseInfoId(this.courseId).then((response) => {
        //根据课程id查询在courseInfo课程基本信息(包含一级分类id 和 二级分类id)
        this.courseInfo = response.data.courseInfoVo //取得二级分类id的值最终由v-model双向绑定显示指定的内容
        //1 查询所有的分类，包含一级和二级
        subject.getSubjectList().then((response) => {
          //2获取所有一级分类
          this.subjectOneList = response.data.list
          //courseInfo课程的一级分类id等于所有的分类某个一级分类id,并把那一项取出来
          let oneCourslist = this.subjectOneList.filter(
            (item) => item.id === this.courseInfo.subjectParentId
          )
          //获取一级分类所有的二级分类(那一项的children赋给subjectTwoList)
          this.subjectTwoList = oneCourslist[0].children
        })
        //初始化所有讲师(查询所有讲师记录)
        this.getListTeacher()
      })
    },
    //查询所有讲师记录
    getListTeacher() {
      course.getListTeacher().then((response) => {
        this.teacherList = response.data.item
      })
    },
    //查询所有的一级分类(有二级分类的数据)
    getOneSubject() {
      subject.getSubjectList().then((response) => {
        this.subjectOneList = response.data.list
      })
    },

    //切换某个一级分类，触发change，显示对应二级分类
    subjectLevelOneChanged(value) {
      // 封装参数value是subject.id (封装参数el-option的value值)
      //遍历所有的分类，包含一级和二级
      for (var i = 0; i < this.subjectOneList.length; i++) {
        //每个一级分类
        var oneSubject = this.subjectOneList[i]
        //判断：所有一级分类id 和 点击一级分类id是否一样
        if (value === oneSubject.id) {
          //从一级分类获取里面所有的二级分类
          this.subjectTwoList = oneSubject.children
          //把二级分类id值清空
          this.courseInfo.subjectId = ''
        }
      }
    },
    //图片上传之前
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!')
      // }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      // return isJPG && isLt2M
      return isLt2M
    },
    //图片上传成功
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url
    },
    //添加课程信息返回课程id
    addCourse() {
      course.addCourseInfo(this.courseInfo).then((response) => {
        //提示
        this.$message({
          type: 'success',
          message: '添加课程信息成功!',
        })
        //跳转到第二步
        this.$router.push({ path: '/course/chapter/' + response.data.courseId })
        //response.data.courseId是后端自动生成的courseId(19位)
      })
    },
    //修改课程信息
    updateCourse() {
      course.updateCourseInfo(this.courseInfo).then((response) => {
        this.$message({
          type: 'success',
          message: '修改课程信息成功!',
        })
        //跳转到第二步
        this.$router.push({ path: '/course/chapter/' + this.courseId })
      })
    },

    //保存并下一步
    saveOrUpdate() {
      if (!this.courseInfo.id) {
        //添加
        this.addCourse()
      } else {
        //修改
        this.updateCourse()
      }
    },
    //判断是否是添加课程基本信息的路由,是则清空表单
    cleanCourseInfo() {
      this.courseInfo = []
      this.courseInfo.cover = '../../../static/snow.png'
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