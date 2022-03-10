import request from '@/utils/request'
export default {
  //查询前8条热门课程
  getIndexCourse() {
    return request({
      url: '/eduservice/indexfront/index/course',
      method: 'get'
    })
  },
  //查询前4条名师
  getIndexTeacher() {
    return request({
      url: '/eduservice/indexfront/index/teacher',
      method: 'get'
    })
  }
}