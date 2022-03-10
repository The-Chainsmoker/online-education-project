package com.atguigu.eduservice.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    private String id;
    private String title;

    //一个一级分类有多个二级分类
    //设置数据时先定义List<TwoSubject>对象最后在OneSubject.set方法
    private List<TwoSubject> children =new ArrayList<>();
}
