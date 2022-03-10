import request from '@/utils/request'
export default {
  //分页查讲师
  getTeacherList(current, limit) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontList/${current}/${limit}`,
      method: 'post'
    })
  },
  //讲师和讲师详情方法
  getTeacherListId(id) {
    return request({
      url: `/eduservice/teacherfront/getTeacherFrontInfo/${id}`,
      method: 'get'
    })
  }
}