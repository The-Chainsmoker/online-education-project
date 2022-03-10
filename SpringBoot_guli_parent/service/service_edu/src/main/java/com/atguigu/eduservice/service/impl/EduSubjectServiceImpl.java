package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-05
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //根据一级和二级分类查询课程所有数据
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1 查询所有一级分类  parentid = 0
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        //baseMapper是服务层使用mybatis-plus的操作数据库的对象
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        //2 查询所有二级分类  parentid ！= 0
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3 封装一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for(int i=0;i<oneSubjectList.size();i++){
            //得到oneSubjectList每个eduSubject对象(每个eduSubject对象储存一条数据库记录)
            EduSubject eduSubject = oneSubjectList.get(i);

            //把eduSubject里面值获取出来，放到OneSubject对象里面
            OneSubject oneSubject=new OneSubject();

            //oneSubject.setId(eduSubject.getId());
            //oneSubject.setTitle(eduSubject.getTitle());
            BeanUtils.copyProperties(eduSubject,oneSubject);//把eduSubject对象的相同属性的值赋给oneSubject对象的相同属性的值

            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubjectList = new ArrayList<>();

            //遍历二级分类list集合
            for(int j=0;j<twoSubjectList.size();j++){
                //获取每个二级分类
                EduSubject tSubject = twoSubjectList.get(j);

                if (eduSubject.getId().equals(tSubject.getParentId())) {
                    TwoSubject twoSubject=new TwoSubject();
                    //twoSubject.setId(teduSubject.getId());
                    //twoSubject.setTitle(teduSubject.getTitle());
                    //把tSubject值复制到TwoSubject里面，放到twoFinalSubjectList里面
                    BeanUtils.copyProperties(tSubject,twoSubject);
                    twoFinalSubjectList.add(twoSubject);
                }
            }
            //把一级下面所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubjectList);

            //多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);
        }
        
        return finalSubjectList;
    }
}
