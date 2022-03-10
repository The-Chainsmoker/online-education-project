package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j//统一日志处理
public class GlobalExceptionHandler {
    //@ExceptionHandler()=>(处理什么异常类)处理先找特定异常再找全局异常

    //配置全局异常类返回所有报错的一致信息
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回JSON数据
    public R error(Exception e){
        e.printStackTrace();//打印异常
        return R.error().message("执行全局异常处理...");
    }

    //配置指定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody//返回JSON数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常");
    }

    //配置自定义异常
    @ExceptionHandler(GuliException.class)//GuliException运行时异常
    @ResponseBody//返回JSON数据
    public R error(GuliException e){
        log.error(e.getMessage());//日志输出(Message是内置属性)
        log.error(e.getMsg());//日志输出
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
