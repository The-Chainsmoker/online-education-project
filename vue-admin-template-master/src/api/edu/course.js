import request from '@/utils/request'
export default {
  //1.添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: '/eduservice/educourse/addCourseInfo',
      method: 'post',
      data: courseInfo
    })
  },
  //2查询所有讲师
  getListTeacher() {
    return request({
      url: '/eduservice/teacher/findAll',
      method: 'get',
    })
  },

  //3根据课程id查询课程基本信息
  getCourseInfoId(id) {
    return request({
      url: '/eduservice/educourse/getCourseInfo/' + id,
      method: 'get',
    })
  },

  //3修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: '/eduservice/educourse/updateCourseInfo',
      method: 'post',
      data: courseInfo
    })
  },

  //4课程确认信息显示
  getPublishCourseInfo(id) {
    return request({
      url: '/eduservice/educourse/getPublishCourseInfo/' + id,
      method: 'get',
    })
  },

  //发布课程
  //修改课程状态(status状态修改为normal)
  publishCourse(id) {
    return request({
      url: `/eduservice/educourse/publishCourse/${id}`,
      method: 'post'
    })
  },

  //查询有关课程所有列表
  getListCourse(current, limit, courseQuery) {
    return request({
      url: `/eduservice/educourse/allCourse/${current}/${limit}`,
      method: 'post',
      data: courseQuery
    })
  },

  //删除有关课程
  deleteCourse(courseId) {
    return request({
      url: `/eduservice/educourse/${courseId}`,
      method: 'delete',
    })
  },

}