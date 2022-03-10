package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;


import java.util.List;

public class TestVod {
    public static void main(String[] args)throws Exception {

        String accessKeyId = "LTAI5tB9HdThqnqvbBKZLWL2";
        String accessKeySecret = "0FJyEiOTxvDArg8vzRC3EgAjYQkLM6";

        String title = "6 - What If I Want to Move Faster - upload by sdk";   //上传之后文件名称
        String fileName = "C:/Users/肖d, accessKeySecret, title, fileName);\n" +
                "        /* 可指定分片上传时每个分片的大小，默认为2M字节 */旭源/Desktop/resources/video/6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    //1 根据视频iD获取视频播放凭证
    public static void getPlayAuth() throws Exception{
        DefaultAcsClient client=InitObject.initVodClient("LTAI5tB9HdThqnqvbBKZLWL2","0FJyEiOTxvDArg8vzRC3EgAjYQkLM6");
        // 创建初始化对象,填写自己Oss密钥ID和Oss密钥密码
        GetVideoPlayAuthRequest request=new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response=new GetVideoPlayAuthResponse();
        // 创建获取视频地址request和response
        request.setVideoId("66684ea5b2ee4022bdc10b7b63182e37");
        // 向request对象的方法得到凭证
        response= client.getAcsResponse(request);
        System.out.println("playAuth:"+response.getPlayAuth());//凭证对加密和不加密都有效
    }

    //1根据视频iD获取视频播放地址(无法获取加密的视频)
    public static void getPlayUrl() throws Exception{
        DefaultAcsClient client=InitObject.initVodClient("LTAI5tB9HdThqnqvbBKZLWL2","0FJyEiOTxvDArg8vzRC3EgAjYQkLM6");
        // 创建初始化对象,填写自己Oss密钥ID和Oss密钥密码
        GetPlayInfoRequest request=new GetPlayInfoRequest();
        GetPlayInfoResponse response=new GetPlayInfoResponse();
        // 创建获取视频地址request和response
        request.setVideoId("e34773011e794ab7ad0c23ad1f58b821");
        // 向request对象里面设置视频id
        response= client.getAcsResponse(request);
        // 调用初始化对象里面的方法,传递request,获取数据
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

    }
}
