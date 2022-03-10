package com.atguigu.eduservice.clientU;

import com.atguigu.commonutils.vo.UcenterMember;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

@Component
public class UcenterClientDegradeFeignClient implements UcenterClient{
    @Override
    public UcenterMember getInfo(String id) {
        return null;
    }
}
