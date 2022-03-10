<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;" />

    <el-tree ref="tree2" :data="data2" :props="defaultProps" :filter-node-method="filterNode" class="filter-tree" default-expand-all />

  </div>
</template>

<script>
import subject from '@/api/edu/subject'
export default {
  data() {
    return {
      filterText: '',
      data2: [],
      defaultProps: {
        children: 'children', //自定义传入data2属性名children
        label: 'title', //自定义传入data2属性名label
      },
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    },
  },
  created() {
    this.getAllSubjectList()
  },
  methods: {
    getAllSubjectList() {
      subject.getSubjectList().then((response) => {
        this.data2 = response.data.list
      })
    },
    filterNode(value, data) {
      if (!value) return true
      //toLowerCase把字符串转换成小写
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    },
  },
}
</script>

