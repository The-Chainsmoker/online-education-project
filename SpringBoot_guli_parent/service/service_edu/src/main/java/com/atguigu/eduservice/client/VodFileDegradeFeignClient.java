package com.atguigu.eduservice.client;

import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

@Component//给sring去管理
public class VodFileDegradeFeignClient implements VodClient{

    //但调用VodClient失败时调用一下方法

    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
