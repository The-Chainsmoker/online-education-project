package com.atguigu.eduservice.entity.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChapterVo {

    private String id;

    private String title;

    //表示小节
    //通过实例的setChildren()设置数据,List的add方法这里无法使用(因为是children是私有属性)
    private List<VideoVo> children = new ArrayList<>();
}
