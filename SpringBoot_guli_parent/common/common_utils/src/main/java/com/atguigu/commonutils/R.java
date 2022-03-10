package com.atguigu.commonutils;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

//统一返回结果的类
@Data
public class R {
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    //返回的结果 "{String1 = Object1,String1 = Object1}",输出的接口为{"String1" : "Object1","String2" : "Object2"}(对象属性)
    private Map<String, Object> data = new HashMap<String, Object>();// Map<String, Object>=>data={}

    //把构造方法私有
    private R(){}

    //成功静态方法
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);//相同的包内可以直接引用
        r.setMessage("成功");
        return r;
    }

    //成功静态方法()
    //使用this不能用static
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    public R success(Boolean success){
        this.setSuccess(success);

        //this指向当前的直接调用者
        return this;
    }

    public R  message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        //put()方法将指定的键/值以key的数组名存入HashMap中
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);//就是data=map 结果:data:{...}
        return this;
    }
}
