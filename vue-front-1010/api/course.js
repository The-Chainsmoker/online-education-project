import request from '@/utils/request'
export default {
  //条件分页课程查询的方法
  getCourseList(page, limit, searchObj) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //查询所有分类的方法
  getAllSubject() {
    return request({
      url: '/eduservice/subject/getAllSubject',
      method: 'get'
    })
  },
  //课程详情的方法
  getCourseInfo(id) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseInfo/${id}`,
      method: 'get'
    })
  },

  //根据课程id查询课程基本信息
  getCourseInfoFront(courseId) {
    return request({
      url: `/eduservice/coursefront/getCourseWeb/${courseId}`,
      method: 'get'
    })
  },
  //修改课程信息
  updateCourseInfoFront(courseInfo) {
    return request({
      url: `/eduservice/coursefront/updateCourseWeb`,
      method: 'post',
      data: courseInfo
    })
  },

}