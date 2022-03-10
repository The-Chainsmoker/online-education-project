package com.atguigu.educms.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class BannerQuery {
    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "查询开始时间", example = "2022-02-02 10:10:10")
    private String begin;//注意，这里使用的是String类型，前端传过来的数据无需进行类型转换

    @ApiModelProperty(value = "查询结束时间", example = "2022-02-02 10:10:10")
    private String end;
}
