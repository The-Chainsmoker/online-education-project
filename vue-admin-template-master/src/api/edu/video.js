import request from '@/utils/request'
export default {
  //添加小节
  addVideo(video) {
    return request({
      url: '/eduservice/video/addVideo',
      method: 'post',
      data: video
    })
  },

  //根据id查询小节信息
  getVideoInfo(id) {
    return request({
      url: `/eduservice/video/getVideoInfo/${id}`,
      method: 'get',
    })
  },

  //修改小节信息
  updateVideo(video) {
    return request({
      url: '/eduservice/video/updateVideo',
      method: 'post',
      data: video
    })
  },

  //删除小节
  deleteVideo(videoId) {
    return request({
      url: `/eduservice/video/${videoId}`,
      method: 'delete',
    })
  },

  //根据id删除阿里云服务器上的视频
  deleteCourse(id) {
    return request({
      url: `/eduvod/video/removeAlyVideo/${id}`,
      method: 'delete',
    })
  }

}