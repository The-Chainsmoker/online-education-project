<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="orderQuery.courseTitle" placeholder="课程名称" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="orderQuery.teacherName" placeholder="讲师名称" />
      </el-form-item>

      <el-form-item>
        <el-select v-model="orderQuery.status" clearable placeholder="订单状态">
          <el-option :value="0" label="未支付" />
          <el-option :value="1" label="已支付" />
        </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
        <el-date-picker v-model="orderQuery.begin" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="orderQuery.end" type="datetime" placeholder="选择截止时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00" />
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

      <el-table-column prop="orderNo" label="订单号" width="155" />
      <!--prop="name"拿到data的数据的对象值在页面输出-->

      <el-table-column prop="courseTitle" label="课程名称" />

      <el-table-column prop="teacherName" label="讲师名称" />

      <el-table-column prop="nickname" label="用户昵称" />

      <el-table-column prop="mobile" label="用户手机" />

      <el-table-column label="订单金额" width="80">
        <template slot-scope="scope">
          {{scope.row.totalFee}}元
        </template>
      </el-table-column>

      <el-table-column label="支付方式" width="80">
        <template slot-scope="scope">
          <!--slot-scope="scope"只是定义对象来代表取得的data数据-->
          {{ scope.row.payType === 1 ? '微信' : '支付宝' }}
          <!-- row是一条item数据 -->
        </template>
      </el-table-column>

      <el-table-column label="订单状态" width="80">
        <template slot-scope="scope">
          <!--slot-scope="scope"只是定义对象来代表取得的data数据-->
          {{ scope.row.status === 1 ? '已支付' : '未支付' }}
          <!-- row是一条item数据 -->
        </template>
      </el-table-column>

      <el-table-column prop="gmtCreate" label="添加时间" width="160" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <!-- <router-link :to="'/teacher/edit/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link> -->
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="page" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;" layout="total, prev, pager, next, jumper" @current-change="getList" />

  </div>
</template>

<script>
import orderApi from '@/api/order'

export default {
  data() {
    return {
      listLoading: false, //是否显示loading信息
      list: [], //获取订单列表数据
      page: 1, //当前页
      limit: 5, //每页有多少条
      total: 0, //记录总数
      orderQuery: {}, //条件封装的对象
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //获取订单列表数据
    getList(page = 1) {
      //一开始不能获取当前的页数,需要默认参数默认值pagee
      this.page = page
      orderApi
        .getOrderListPage(this.page, this.limit, this.orderQuery)
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
      this.teacherQuery = {}
      //查询所有订单数据
      this.getList()
    },

    removeDataById(id) {
      this.$confirm('此操作将永久删除订单记录,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }).then(() => {
        //删除讲师记录
        orderApi.deleteOrder(id).then((res) => {
          // console.log('成功')
          this.getList()
          //提示框
          this.$message({
            type: 'success',
            message: '删除成功!',
          })
        })
      })
      // .catch(() => {
      //   //提示框
      //   this.$message({
      //     type: 'info',
      //     message: '已取消删除',
      //   })
      // })
    },
  },
}
</script>

<style scoped>
</style>