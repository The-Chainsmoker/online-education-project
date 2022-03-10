<template>
  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="最终发布" />
    </el-steps>

    <el-button type="text" @click="openChapterDialog()">添加章节</el-button>

    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}

          <span class="acts">
            <!-- 这里的id是chapterVideoList列表的id -->
            <el-button style="" type="text" @click="openVideo(chapter.id)">添加小节</el-button>
            <el-button style="" type="text" @click="openEditChatper(chapter.id)">编辑</el-button>
            <el-button type="text" @click="removeChapter(chapter.id)">删除</el-button>
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>{{ video.title }}

              <span class="acts">
                <el-button style="" type="text" @click="openEditVideo(video.id)">编辑</el-button>
                <el-button type="text" @click="removeVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" :title="chapter.id ? '修改章节' : '添加章节'">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" :title="video.id? '修改课时' : '添加课时'">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right" />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload :on-success="handleVodUploadSuccess" :on-remove="handleVodRemove" :before-remove="beforeVodRemove" :on-exceed="handleUploadExceed" :file-list="fileList" :action="BASE_API+'/eduvod/video/uploadAlyiVideo'" :limit="1" class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'
export default {
  name: 'chapter',
  data() {
    return {
      saveBtnDisabled: false, //下一步小节确定按钮是否可点击
      saveVideoBtnDisabled: false, //按钮是否可点击
      courseId: '', //课程Id
      chapterVideoList: [], //课程大纲(查询课程id的章节和小节)列表
      dialogChapterFormVisible: false, //章节弹框的是否显示
      dialogVideoFormVisible: false, //小节弹框的是否显示
      chapter: {
        //章节数据
        title: '',
        sort: 0,
      },
      video: {
        //小节数据
        title: '',
        sort: 0,
        free: 0,
        videoSourceId: '', //视频资源id
        videoOriginalName: '', //视频名称
      },
      BASE_API: process.env.BASE_API, // 接口API地址
      fileList: [], //上传文件列表
    }
  },
  components: {},
  created() {
    //获取路由的id值
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      //根据课程id查询章节和小节
      this.getChapterVideo()
    }
  },
  methods: {
    //视频上传成功
    handleVodUploadSuccess(response, file, fileList) {
      //把视频上传到阿里服务器返回的videoId
      this.video.videoSourceId = response.data.videoId
      //返回视频名称
      this.video.videoOriginalName = file.name
    },
    //文件超出个数限制
    handleUploadExceed() {
      this.$message.warning('想要重新上传视频，请先删除已上传的视频')
    },
    //点击×调用这个方法
    beforeVodRemove(file, fileList) {
      //返回true或false判断是否删除
      console.log(fileList)
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    //删除上传视频成功后
    handleVodRemove() {
      // 调用接口的删除视频的方法
      video
        .deleteCourse(this.video.videoSourceId)
        .then((response) => {
          //提示信息
          this.$message({
            type: 'success',
            message: '删除视频成功!',
          })
          //清除文件列表
          this.fileList = []
          //上传视频id赋值
          this.video.videoSourceId = ''
          //上传视频名称赋值
          this.video.videoOriginalName = ''
        })
        .catch(() => {})
    },

    //==============================小节操作====================================
    //添加小节弹框显示的
    openVideo(chapterId) {
      //弹框
      this.dialogVideoFormVisible = true
      //清空小节id
      this.video.id = ''
      //清空小节标题
      this.video.title = ''
      //清空视频资源的id
      this.video.videoSourceId = ''
      //设置排序默认=值
      this.video.sort = 0
      //默认课程
      this.video.isFree = false
      //清除文件列表
      this.fileList = []
      //设置课程id
      this.video.courseId = this.courseId
      //设置章节id
      // 这里的chapterId是chapterVideoList列表的id
      this.video.chapterId = chapterId
    },
    //添加小节
    addVideo() {
      video.addVideo(this.video).then((response) => {
        //关闭弹框
        this.dialogVideoFormVisible = false
        this.$message({
          type: 'success',
          message: '添加小节成功!',
        })
        //刷新页面
        this.getChapterVideo()
      })
    },
    //修改小节弹框显示的
    openEditVideo(id) {
      this.dialogVideoFormVisible = true
      //根据id查询小节信息
      video.getVideoInfo(id).then((response) => {
        this.video = response.data.videoInfo
      })
    },
    //修改小节
    updateVideo() {
      video.updateVideo(this.video).then((response) => {
        //关闭弹框
        this.dialogVideoFormVisible = false
        this.$message({
          type: 'success',
          message: '修改小节成功!',
        })
        //刷新页面
        this.getChapterVideo()
      })
    },
    //修改或添加小节
    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.addVideo()
      } else {
        this.updateVideo()
      }
    },
    //删除小节
    removeVideo(id) {
      this.$confirm('此操作将删除小节, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          //点击确定，执行then方法
          //调用删除的方法
          video.deleteVideo(id).then((response) => {
            //删除成功
            //提示信息
            this.$message({
              type: 'success',
              message: '删除小节成功!',
            })
            //刷新页面
            this.getChapterVideo()
          })
        })
        .catch((err) => {}) //点击取消，执行catch方法
    },

    //==============================章节操作====================================

    //添加章节弹框显示
    openChapterDialog() {
      this.dialogChapterFormVisible = true
      this.chapter.id = ''
      this.chapter.title = ''
      this.chapter.sort = 0
    },
    //修改章节弹框数据回显
    openEditChatper(chapterId) {
      //这里的chapterId是chapterVideoList列表的id
      //弹框
      this.dialogChapterFormVisible = true
      // 根据id查询章节
      chapter.getChapter(chapterId).then((response) => {
        //this.chapter是双向绑定自动改变页面
        this.chapter = response.data.chapter
      })
    },
    //删除章节
    removeChapter(chapterId) {
      this.$confirm('此操作将删除章节, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then((response) => {
          chapter.deleteChapter(chapterId).then((response) => {
            //提示信息
            this.$message({
              type: 'success',
              message: '删除成功！',
            })
            //刷新页面
            this.getChapterVideo()
          })
        })
        .catch(() => {
          // this.$message({
          //   type: 'info',
          //   message: '已取消删除',
          // })
        })
    },
    //添加章节
    addChapter() {
      //设置课程id到chapter对象里面
      this.chapter.courseId = this.courseId
      chapter.addChapter(this.chapter).then((response) => {
        //关闭弹框
        this.dialogChapterFormVisible = false
        //提示
        this.$message({
          type: 'success',
          message: '添加章节成功!',
        })
        //刷新页面
        this.getChapterVideo()
      })
    },
    //修改章节的方法(被saveOrUpdate方法调掉)
    updateChapter() {
      chapter.updateChapter(this.chapter).then((response) => {
        //关闭弹框
        this.dialogChapterFormVisible = false
        //提示
        this.$message({
          type: 'success',
          message: '修改章节成功!',
        })
        //刷新页面
        this.getChapterVideo()
      })
    },
    //添加或修改章节
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.addChapter()
      } else {
        this.updateChapter()
      }
    },
    //根据课程id查询章节和小节
    getChapterVideo() {
      chapter.getAllChapterVideo(this.courseId).then((response) => {
        this.chapterVideoList = response.data.allChapterVideo
      })
    },
    //上一步
    previous() {
      this.$router.push({ path: '/course/info/' + this.courseId })
    },
    //下一步
    next() {
      this.$router.push({ path: '/course/publish/' + this.courseId })
    },
  },
}
</script>

<style scoped>
.chanpterList {
  position: relative;
  /* list-style去掉li的原点 */
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>