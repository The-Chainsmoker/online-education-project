package com.atguigu.oss.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //上传头像Oss
    String uploadFileAvatar(MultipartFile file);
}
