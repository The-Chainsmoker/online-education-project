import request from '@/utils/request'

export default {
  // 前端接口参数加不加要看后端接口有@RequestBody对象参数是json对象所对应

  // 1.讲师列表（条件查询分页）
  // 2.current当前页,limit每页记录数,teacherQuery条件对象
  getTeacherListPage(current, limit, TeacherQuery) {
    return request({
      // url: '/table/list'+current+"/"+limit,
      url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
      method: 'post',
      //teacherQuery条件对象,后端使用RequestBody获取数据
      //post请求使用data,get请求使用params请求
      // params会把入参拼接到url上; data是放在body里发送给后台
      data: TeacherQuery//条件封装的对象
    })
  },
  //删除讲师
  deleteTeacherList(id) {
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete',
    })
  },
  //添加讲师
  addTeacherList(teacher) {
    return request({
      url: `eduservice/teacher/addTeacher`,
      method: 'post',
      data: teacher
    })
  },
  //根据id查找讲师记录
  getTeacherInfo(id) {
    return request({
      url: `eduservice/teacher/getTeacher/${id}`,
      method: 'get',
    })
  },
  //修改讲师
  updateTeacherInfo(teacher) {
    return request({
      url: `eduservice/teacher/updateTeacher`,
      method: 'post',
      data: teacher
    })
  }

}
