package com.atguigu.eduservice.mapper;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.frontvo.CourseWebVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-02-06
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    //课程id多表查询课程
    public CoursePublishVo getPublishCourseInfo(String courseId);
    //课程id多表查询课程(前台)
    public CourseWebVo getBaseCourseInfo(String courseId);
}
