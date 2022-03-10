package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-06
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    @Autowired
    EduVideoService videoService;

    //课程大纲列表,根据课程courseId查询edu_chapter和edu_video的数据表
    @Override
    public List getChapterVideoByCourseId(String courseId) {
        //1根据课程id查询课程里面所有的章节
        QueryWrapper<EduChapter> wrapperChapter = new QueryWrapper<>();
        wrapperChapter.eq("course_id",courseId);//输入的课程id
        wrapperChapter.orderByAsc("sort");//上升排序
        List<EduChapter> eduChaptersList = baseMapper.selectList(wrapperChapter);//这里不能使用chapterService(自调用)

        //2根据课程过查询课程里面所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.orderByAsc("sort");//上升排序
        List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList =new ArrayList<>();

        //3遍历查询章节list集合进行封装(一章多节)
        //遍历查询章节list集合
        for(int i=0;i<eduChaptersList.size();i++){
            //每个章节对象
            EduChapter eduChapter = eduChaptersList.get(i);
            //eduChapter对象值复制到ChapterVo里面
            ChapterVo chapterVo=new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);

            //把chapterVo放到最终list集合(放最后更符合逻辑)
            finalList.add(chapterVo);

            List<VideoVo> videoVoList=new ArrayList<>();

            //4遍历查询小节list集合，进行封装(一章多节)
            for (int m = 0; m < eduVideoList.size(); m++) {
                //得到每个小节
                EduVideo eduVideo = eduVideoList.get(m);
                //判断：小节里面chapterid和章节里面id是否一样
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    //封装一个videoVo对象
                    VideoVo videoVo=new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装集合
                    videoVoList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoVoList);
        };


        return finalList;
    }

    //删除章节
    @Override
    public Boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper=new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        //根据条件查询返回numbers(0是没数据)
        int count = videoService.count(wrapper);
        //判断
        if(count>0){//查询出小节，不进行删除
            throw new GuliException(20001,"该章节有小节不能删除！");
        }else{//不能查询数据，进行删除
            int result = baseMapper.deleteById(chapterId);
            return result>0;
        }


    }

    //删除章节
    @Override
    public void removeChapterCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
