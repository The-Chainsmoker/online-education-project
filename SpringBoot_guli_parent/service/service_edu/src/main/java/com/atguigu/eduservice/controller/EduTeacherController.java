package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-09-04
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping(value = "/eduservice/teacher")
//@CrossOrigin //解决跨域
public class EduTeacherController {

    //把server注入
    @Autowired
    private EduTeacherService eduTeacherService;

    //1 查询讲师所有数据
    @ApiOperation(value = "所有讲师列表")//前端web页面的findAllTecher的注解
    @GetMapping("findAll")//(等于/findAll)
    public R findAllTecher(){
        List<EduTeacher> list = eduTeacherService.list(null);

        return R.ok().data("item",list);
        //链式编程
        //返回对象,因为使用mp的@data封装toString方法

    }

    //2逻辑删除讲师方法
    @ApiOperation(value = "根据ID删除讲师")//前端web页面注解
    @DeleteMapping("{id}")//id需要通过路径进行传递
    //@PathVariable String id 得到路径的id名，由前端传递过来
    public R removeTeacher(@ApiParam(name = "id",value = "讲师ID",required = true) @PathVariable String id){
        //@ApiParam(name = "id",value = "讲师ID",required = true)Swagger页面注解
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //3 分页查询讲师的方法
    //current 当前页
    //limit 每页记录数
    //浏览器不支持带参数的路径
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@ApiParam(name = "current",value = "当前页码",required = true) @PathVariable long current,
                             @ApiParam(name = "limit", value = "每页记录数", required = true)@PathVariable long limit){

        //try {
        //    int i=1/0;
        //}catch (Exception e){
        //    //执行自定义异常
        //    //new GuliException(20001,"执行了之定义异常处理"):执行有参构造方法,
        //    //是个对象,最终输出的是toString方法,被throw抛出异常
        //    throw new GuliException(20001,"执行了之定义异常处理");
        //}

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduTeacherService.page(pageTeacher,null);

        long total=pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        //Map map=new HashMap();
        //map.put("total",total);
        //map.put("rows",records);
        //return R.ok().data(map);
        return R.ok().data("total",total).data("rows",records);
    }

    //4条件查询带分页的方法
    //浏览器不支持带参数的路径
    @ApiOperation(value = "条件带分页查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = true)  TeacherQuery teacherQuery){
        //required = false 参数值可以为空

        //@RequestBody使用Json传递数据,把Json数据封装到对应对象里面
        //@ResponseBody返回数据,返回json数据

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断该条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.like("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);//gmt_create是数据表的字段名
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        //下降排序
        wrapper.orderByDesc("sort");
        //wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        eduTeacherService.page(pageTeacher,wrapper);

        long total=pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);

    }

    //添加讲师接口的方法
    @ApiOperation(value = "添加讲师")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = eduTeacherService.save(eduTeacher);
        if(save){
            return  R.ok();
        }else {
            return R.error();
        }
    }


    //根据讲师id进行查询
    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher byId = eduTeacherService.getById(id);

        //try{
        //    int i=1/0;
        //}catch (Exception e){
        //    throw new GuliException(20001,"执行自定义异常");
        //}

        return R.ok().data("teacher",byId);
    }

    //讲师修改功能
    @ApiOperation(value = "修改讲师")
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);


        if(flag){
            return R.ok();

        }else{
            return R.error();
        }
    }

}