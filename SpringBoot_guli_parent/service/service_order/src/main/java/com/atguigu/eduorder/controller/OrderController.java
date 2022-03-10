package com.atguigu.eduorder.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.entity.vo.OrderQuery;
import com.atguigu.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-23
 */
@RestController
@RequestMapping("/eduorder/order")
//@CrossOrigin
public class OrderController {
    @Autowired
    private OrderService orderService;

    //1 生成订单的方法(订单id)
    @PostMapping("createOrder/{courseId}")
    public R saveOrder(@PathVariable String courseId , HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单，返回订单号
        //使用JWT解析用户Id和课程Id
        String orderNo =
                orderService.createOrders(courseId,memberId);

        return R.ok().data("orderId",orderNo);
    }

    //2 根据订单id查询订单信息
    @GetMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable String orderId) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("item",order);
    }

    //3根据课程id和用户id查询订单表中订单状态
    @GetMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,
                               @PathVariable String memberId) {

        QueryWrapper<Order> wrapper=new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);//支付状态 1代表已经支付
        //这样获取count前台不用判断返回对象的status属性存不存在
        int count = orderService.count(wrapper);
        if(count>0) { //已经支付
            return true;
        } else {
            return false;
        }
    }

    //前端后台订单管理==================================
    @PostMapping("pageOrder/{page}/{limit}")
    public R pageOrder(@PathVariable long page, @PathVariable long limit,
                       @RequestBody(required = true) OrderQuery orderQuery){
        Page<Order> pageOrder=new Page<>(page,limit);
        QueryWrapper<Order> wrapper=new QueryWrapper<>();

        String courseTitle = orderQuery.getCourseTitle();
        String teacherName = orderQuery.getTeacherName();
        Integer status = orderQuery.getStatus();
        String begin = orderQuery.getBegin();
        String end = orderQuery.getEnd();

        if(!StringUtils.isEmpty(courseTitle)){
            wrapper.like("course_title",courseTitle);
        }

        if(!StringUtils.isEmpty(teacherName)){
            wrapper.like("teacher_name",teacherName);
        }

        if(!StringUtils.isEmpty(status)){
            wrapper.like("status",status);
        }

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        wrapper.orderByAsc("gmt_create");
        orderService.page(pageOrder,wrapper);
        System.out.println(pageOrder.getTotal());
        return R.ok().data("items",pageOrder.getRecords()).data("total",pageOrder.getTotal());
    }

    //删除order
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable String id) {
        orderService.removeById(id);
        return R.ok();
    }
}

