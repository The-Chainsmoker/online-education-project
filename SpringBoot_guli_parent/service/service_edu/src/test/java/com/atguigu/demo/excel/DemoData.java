package com.atguigu.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;

public class DemoData {
    //value给excel字段首的头起名(输出对象在excel是纵向排列),不写则以实体类属性名称为excel字段首的头起名
    @ExcelProperty(value = "学生编号",index = 0)
    private int sno;

    @ExcelProperty(value = "学生姓名",index = 1)
    private String sname;

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "DemoData{" +
                "sno=" + sno +
                ", sname='" + sname + '\'' +
                '}';
    }

}