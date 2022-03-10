package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-06
 */
public interface EduChapterService extends IService<EduChapter> {

    List getChapterVideoByCourseId(String courseId);

    Boolean deleteChapter(String chapterId);

    //删除章节id
    void removeChapterCourseId(String courseId);
}
