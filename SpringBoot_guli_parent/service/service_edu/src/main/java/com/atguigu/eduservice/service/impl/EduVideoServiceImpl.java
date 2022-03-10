package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.mapper.EduVideoMapper;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-06
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {

    @Autowired
    private VodClient vodClient;

    //1 根据课程id删除小节
    @Override
    public void removeVideoByCourseId(String courseId) {
        //1根据课程id查询课程所有的视频id
        QueryWrapper<EduVideo> wrapperVideo=new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        wrapperVideo.select("video_source_id");//只返回video_source_id字段的记录
        List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);
        List<String> videoIds=new ArrayList<>();
        for (int i = 0; i < eduVideoList.size(); i++) {
            EduVideo eduVideo = eduVideoList.get(i);
            String videoSourceId = eduVideo.getVideoSourceId();

            if(!StringUtils.isEmpty(videoSourceId)){
                //放到videoIds集合里面
                videoIds.add(videoSourceId);
            }

        }

        ///根据多个视频id删除多个视频
        if(videoIds.size()>0){
            vodClient.deleteBatch(videoIds);
        }


        //创建查询条件对象(删除数据库的表)
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        //delete传入一个QueryWrapper<EduVideo>对象
        baseMapper.delete(wrapper);
    }
}
