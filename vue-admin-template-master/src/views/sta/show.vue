<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">

      <el-form-item>
        <el-select v-model="searchObj.type" clearable placeholder="请选择">
          <el-option label="学员登录数统计" value="login_num" />
          <el-option label="学员注册数统计" value="register_num" />
          <el-option label="课程播放数统计" value="video_view_num" />
          <el-option label="每日课程数统计" value="course_num" />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-date-picker v-model="searchObj.begin" type="date" placeholder="选择开始日期" value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="searchObj.end" type="date" placeholder="选择截止日期" value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-button :disabled="btnDisabled" type="primary" icon="el-icon-search" @click="showChart()">查询</el-button>
    </el-form>

    <div class="chart-container">
      <div id="chart" class="chart" style="height:500px;width:100%" />
    </div>
  </div>
</template>

<script>
import echarts from 'echarts'
import staApi from '@/api/sta'
export default {
  data() {
    return {
      searchObj: {}, //条件查询
      btnDisabled: false, //是否禁用
      xData: [], //横坐标数据
      yData: [], //纵坐标数据
      chart: '',
    }
  },
  created() {},
  methods: {
    showChart() {
      //判断三个查询条件都不能为空!
      if (Object.keys(this.searchObj).length !== 3) {
        this.$message({
          type: 'warning',
          message: '请选择的查询!',
        })
        return
      }
      staApi.getDataStart(this.searchObj).then((response) => {
        //获取日期数组
        this.xData = response.data.date_calculatedList
        //获取相关人数数组
        this.yData = response.data.numDataList
        //调用下面生成图表的方法，改变值
        this.setChart()
      })
    },
    setChart() {
      // 基于准备好的dom，初始化echarts实例
      this.chart = echarts.init(document.getElementById('chart'))
      // 指定图表的配置项和数据
      var option = {
        title: {
          text: '数据统计',
        },
        tooltip: {
          trigger: 'axis', //
        },
        dataZoom: [
          {
            show: true,
            height: 30,
            xAxisIndex: [0],
            bottom: 30,
            start: 10,
            end: 80,
            handleIcon:
              'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
            handleSize: '110%',
            handleStyle: {
              color: '#3580DE',
            },
            textStyle: {
              color: '#fff',
            },
            borderColor: '#90979c',
          },
          {
            type: 'inside',
            show: true,
            height: 15,
            start: 1,
            end: 35,
          },
        ],
        // x轴是类目轴（离散数据）,必须通过data设置类目数据
        xAxis: {
          type: 'category',
          data: this.xData,
        },
        // y轴是数据轴（连续数据）
        yAxis: {
          type: 'value',
        },
        // 系列列表。每个系列通过 type 决定自己的图表类型
        series: [
          {
            // 系列中的数据内容数组
            data: this.yData,
            // 折线图
            type: 'line',

            itemStyle: {
              normal: {
                color: '#007acc', //改变折线点的颜色
                lineStyle: {
                  color: '#3580DE', //改变折线颜色
                },
              },
            },
          },
        ],
      }

      this.chart.setOption(option)
    },
  },
}
</script>

<style>
</style>