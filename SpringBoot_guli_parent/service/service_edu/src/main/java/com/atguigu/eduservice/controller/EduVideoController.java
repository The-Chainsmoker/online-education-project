package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.client.VodClient;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-06
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private VodClient vodClient;

    @Autowired
    EduVideoService videoService;

    //添加小节
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节(调用eduvod服务的接口)
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获取视频id,调用方法实现视频删除
        EduVideo eduVedio = videoService.getById(id);
        String videoSourceId = eduVedio.getVideoSourceId();
        //判断小节里面是否有视频id(使用工具类判断)
        if(!StringUtils.isEmpty(videoSourceId)) {
            //删除阿里云服务器的视频
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode()==20001){
                throw new GuliException(20001,"删除视频失败，熔断器...");
            }
        }

        videoService.removeById(id);
        return R.ok();
    }

    //根据id查询小节信息
    @GetMapping("getVideoInfo/{id}")
    public R getVideoInfo(@PathVariable String id){
        EduVideo videoInfo = videoService.getById(id);
        return R.ok().data("videoInfo",videoInfo);
    }
    //修改小节
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }
}

