<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="bannerQuery.title" placeholder="Banner名称" />
      </el-form-item>
      <el-form-item label="添加时间">
        <el-date-picker v-model="bannerQuery.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="bannerQuery.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table v-loading="listLoading" :data="list" element-loading-text="数据加载中" border fit highlight-current-row>
      <!--highlight-current-row正在加载-->
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
          <!--等到1，2，3，4，5...的算法-->
        </template>
      </el-table-column>

      <el-table-column label="图片" width="400">
        <template slot-scope="scope">
          <div class="conBox">
            <img :src="scope.row.imageUrl" alt="">
          </div>
        </template>
      </el-table-column>

      <el-table-column prop="title" label="名称" />
      <!--prop="name"拿到data的数据的对象值在页面输出-->

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />
      <el-table-column prop="sort" label="排序" width="90" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">

          <router-link :to="'/banner/save/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit" class="editerButton">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataByCoursId(scope.row.id)" class="editerButton editerButtonBttom">删除
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper" @current-change="getList" />

  </div>
</template>

<script>
import bannerApi from '@/api/banner'

export default {
  data() {
    return {
      listLoading: false, //是否显示loading信息
      list: [], //获取banner列表数据
      page: 1, //当前页
      limit: 4, //每页有多少条
      total: 0, //记录总数
      bannerQuery: {}, //条件封装的对象
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //获取有关Banner列表数据
    getList(page = 1) {
      //elementUi组件一开始不能获取当前的页数,需要默认参数默认值pagee
      this.page = page
      bannerApi
        .getBannerListPage(this.page, this.limit, this.bannerQuery)
        .then((res) => {
          // console.log(res)
          this.list = res.data.items
          this.total = res.data.total
        }) //请求成功
        .catch((err) => {
          // console.log(err)
        }) //请求失败
    },

    //清空条件查询的方法
    resetData() {
      //表单输入项数据清空
      this.bannerQuery = {}
      //查询所有有关课程
      this.getList()
    },
    //删除有关课程
    removeDataByCoursId(id) {
      this.$confirm('此操作将永久删除Banner记录,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          //删除讲师记录
          bannerApi.deleteBanner(id).then((res) => {
            // console.log('成功')
            this.getList()
            //提示框
            this.$message({
              type: 'success',
              message: '删除成功!',
            })
          })
        })
        .catch(() => {})
    },
  },
}
</script>

<style scoped>
.conBox {
  height: 130px;
}
.conBox img {
  width: 100%;
  height: 100%;
}
</style>