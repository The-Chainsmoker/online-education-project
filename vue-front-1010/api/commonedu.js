import request from '@/utils/request'

export default {
  //评论分页列表
  getPageList(page, limit, courseId) {
    return request({
      url: `/eduservice/comment/${page}/${limit}`,
      method: 'get',
      params: { courseId }

    })

  },

  //添加评论
  addComment(comment) {
    return request({
      url: `/eduservice/comment/auth/save`,
      method: 'post',
      data: comment
    })

  }

}