package com.atguigu.eduservice.controller.front;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduCourseService;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/eduservice/indexfront")
//@CrossOrigin
public class IndexFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduTeacherService teacherService;

    //查询前8条热门课程
    //返回结果会放在缓存中,下次要调用这个方法时，能直接从缓存里取到(用redis做缓存)
    @Cacheable(value = "course",key = "'selectIndexList'")
    @GetMapping("index/course")
    public R indexCourse() {
        //查询前8条热门课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("id");
        wrapper.last("limit 8");
        List<EduCourse> eduList = courseService.list(wrapper);


        return R.ok().data("eduList",eduList);
    }

    //查询前4条名师
    //返回结果会放在缓存中,下次要调用这个方法时，能直接从缓存里取到(用redis做缓存)
    @Cacheable(value = "teacher",key = "'selectIndexList'")
    @GetMapping("index/teacher")
    public R indexTeacher() {

        //查询前4条名师
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByAsc("id");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> teacherList = teacherService.list(wrapperTeacher);

        return R.ok().data("teacherList",teacherList);
    }


}
