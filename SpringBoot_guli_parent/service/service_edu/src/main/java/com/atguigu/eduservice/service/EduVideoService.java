package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-06
 */
public interface EduVideoService extends IService<EduVideo> {
    //删除小节
    void removeVideoByCourseId(String courseId);
}
