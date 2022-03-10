package com.atguigu.eduservice.entity.frontvo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseFrontVo {

    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;//course表的buy_count

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;//course表的gmt_create

    @ApiModelProperty(value = "价格排序")
    private String priceSort;//course表的price



}
