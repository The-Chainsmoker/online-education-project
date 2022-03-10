package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.entity.vo.CoursePublishVo;
import com.atguigu.eduservice.entity.vo.CourseQuery;
import com.atguigu.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-06
 */
@RestController
@RequestMapping("/eduservice/educourse")
//@CrossOrigin //解决跨域
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //课程所有列表
    @PostMapping("allCourse/{current}/{limit}")
    public R getCourseList(@PathVariable long current, @PathVariable long limit,
                           @RequestBody(required = true) CourseQuery courseQuery){
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        //判断该条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.like("status",status);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//gmt_create是数据表的字段名
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        courseService.page(pageCourse,wrapper);

        long total=pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords();//数据list集合
        return R.ok().data("total",total).data("list",records);
    }

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){//课程的基本信息
        //返回添加之后课程id,为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);

        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id多表查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态(status状态修改为normal)
    //post和Get请求都可以请求一个参数，但对象数据只能用post请求
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse eduCourse =new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);

        return R.ok();
    }

    //删除课程相关信息
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

