package com.atguigu.eduservice.clientU;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.vo.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient(name="service-ucenter",fallback = UcenterClientDegradeFeignClient.class)
public interface UcenterClient {
    //根据用户id获取用户信息
    @PostMapping("/educenter/member/getInfoUc/{id}")
    public UcenterMember getInfo(@PathVariable String id);

}
