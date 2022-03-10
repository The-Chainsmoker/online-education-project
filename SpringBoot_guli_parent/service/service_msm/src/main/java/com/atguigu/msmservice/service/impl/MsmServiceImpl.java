package com.atguigu.msmservice.service.impl;

import com.atguigu.msmservice.service.MsmService;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.sms.v20210111.SmsClient;
import com.tencentcloudapi.sms.v20210111.models.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;


@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(String[] param, String phone) {
        if(StringUtils.isEmpty(phone)) return false;
        try{
            // 实例化一个认证对象，入参需要传入腾讯云账户secretId，secretKey,此处还需注意密钥对的保密
            // 密钥可前往https://console.cloud.tencent.com/cam/capi网站进行获取
            Credential cred = new Credential("AKIDd7NOJ9nDMEqCG3bv0oNgiV2II1SdYDbj", "tjJdjZ8RUFrGVTCf0A4i6D3cuvf8JDrE");
            // 实例化一个http选项，可选的，没有特殊需求可以跳过
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");
            // 实例化一个client选项，可选的，没有特殊需求可以跳过
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            // 实例化要请求产品的client对象,clientProfile是可选的
            SmsClient client = new SmsClient(cred, "ap-guangzhou", clientProfile);
            //实例化一个请求对象,每个接口都会对应一个request对象
            SendSmsRequest req = new SendSmsRequest();
            String[] phoneNumberSet = {phone};    //短信接受号码
            req.setPhoneNumberSet(phoneNumberSet);
            req.setSmsSdkAppId("1400635075");           //SdkAppId
            req.setSignName("springboot学习");              //短信签名
            req.setTemplateId("1307810");            //短信模板ID
            //设置参数
            //String[] templateParamSet = {"1234"};
            req.setTemplateParamSet(param);
            //最终发送
            client.SendSms(req);

            // 返回的resp是一个SendSmsResponse的实例，与请求对象对应
            //SendSmsResponse resp = client.SendSms(req);
            // 输出json格式的字符串回包
            //System.out.println(SendSmsResponse.toJsonString(resp));
            return true;
        } catch (TencentCloudSDKException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
