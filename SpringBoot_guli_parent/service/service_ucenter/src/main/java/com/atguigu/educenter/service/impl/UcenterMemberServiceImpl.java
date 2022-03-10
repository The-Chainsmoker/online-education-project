package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.MD5;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-18
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //登录
    @Override
    public String login(UcenterMember member) {
        //获取登录手机号和密码
        String mobile = member.getMobile();
        String password = member.getPassword();

        //手机号和密码非空判断
        if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001,"手机或密码为空,登录失败");//使用自定义异常类的构造方法

        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UcenterMember mobileMember = baseMapper.selectOne(wrapper);
        //判断查询对象是否为空
        if(mobileMember==null){//没有这个手机号
            throw new GuliException(20001,"手机号码不正确,登录失败");
        }

        //判断密码
        //因为存储到数据库密码肯定加密的
        //把输入的密码进行加密，再和数据库密码进行比较
        //加密方式MD5
        if(!MD5.encrypt(password).equals(mobileMember.getPassword())){
            throw new GuliException(20001,"密码不正确,登录失败");
        }

        //判断用户是否禁用
        if(mobileMember.getIsDisabled()){
            throw new GuliException(20001,"账号被禁用,登录失败");
        }

        //登录成功
        //生成token字符串，使用jwt工具类
        //传入用户信息id和昵称经行JWT加密
        String jwtToken = JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());

        return jwtToken;
    }

    //注册
    @Override
    public void register(RegisterVo registerVo) {
        //获取注册的数据
        String code = registerVo.getCode();//验证码
        String mobile = registerVo.getMobile();//手机号
        String nickname = registerVo.getNickname();//昵称
        String password = registerVo.getPassword();//密码
        String avatar = registerVo.getAvatar();//用户头像

        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(password)||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)){
            throw new GuliException(20001,"手机、密码、验证码为空,注册失败");
        }

        //判断验证码
        //获取redis验证码
        //取出redis的以手机号码数字作为键(如 13725737343::1234//验证码)
        String redisCode = redisTemplate.opsForValue().get(mobile);
        //redis的手机号码数字键的值五分钟内是存在
        //超出时间则为空
        if(!code.equals(redisCode)){
            throw new GuliException(20001,"验证码失效,注册失败");
        }

        //判断手机号是否重复，表里面存在相同手机号不进行添加
        QueryWrapper<UcenterMember>wrapper=new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0) {
            throw new GuliException(20001,"手机号码已存在,注册失败");
        }

        //数据添加数据库中
        UcenterMember member =new UcenterMember();
        member.setMobile(mobile);
        member.setNickname(nickname);
        member.setAvatar(avatar);
        member.setPassword(MD5.encrypt(password));//密码需要加密的
        //只对两者都有的属性，进行赋值
        //BeanUtils.copyProperties(registerVo,member);
        member.setIsDisabled(false);//用户不禁用
        baseMapper.insert(member);

    }

    //根据openid判断
    @Override
    public UcenterMember getOpenIdMenber(String openid) {
        QueryWrapper<UcenterMember> wrapper =new QueryWrapper<>();
        wrapper.eq("openid",openid);
        UcenterMember member = baseMapper.selectOne(wrapper);

        return member;
    }

    //查询某一天注册人数
    @Override
    public Integer countRegisterDay(String day) {
        return baseMapper.countRegisterDay(day);
    }

}
