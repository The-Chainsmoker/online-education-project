package com.atguigu.eduservice.client;

import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class OrdersFileDegradeFeignClient implements OrdersClient{
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        //throw new GuliException()
        //这里不能抛出运行异常！！！
        return false;
    }
}
