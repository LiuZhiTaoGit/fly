package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author: liuzt
 * @date: 2024/4/3 - 04 - 03 - 12:37
 * @description: com.sky.controller.admin
 * @version: 1.0
 */
@RestController
@Api(tags = "通用接口")
@Slf4j
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file){
        log.info("上传开始！");

        try {

//        初始化文件名
            String originalFilename = file.getOriginalFilename();

//        截取初始文件名的后缀   ddjkf.png
            String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
//        构造新文件的名称
            String objectName = UUID.randomUUID().toString() + substring;
//        文件的请求路径  注意，需要自动注入
            String filePath = null;
            filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.error("文件上传失败：{}",e);
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
