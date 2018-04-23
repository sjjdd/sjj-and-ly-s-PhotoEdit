package com.example.demo.controller;

import com.example.demo.bean.registerbz;
import com.example.demo.dao.PictureMapper;
import com.example.demo.dao.UserMapper;
import io.lettuce.core.dynamic.annotation.Value;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import static org.apache.naming.SelectorContext.prefix;

@RestController

@RequestMapping({"/picture"})
public class UploadController {
    @Autowired
    PictureMapper pictureMapper;
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    @org.springframework.beans.factory.annotation.Value("${uploadDir}")
    private String uploadDir;
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public registerbz uploadImage(@RequestParam(value = "file") MultipartFile file,@RequestParam(value="id") Integer id) throws RuntimeException {
        if (file.isEmpty()) {
           System.out.println("文件不能为空");
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();

        String str = simpleDateFormat.format(date);
        SimpleDateFormat simpleDateFormat2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2= new Date();
        String str2 = simpleDateFormat2.format(date2);
        Timestamp timestamp=Timestamp.valueOf(str2);

        logger.info("上传的文件名为：" + fileName);
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        logger.info("上传的后缀名为：" + suffixName);
        String fileName2= str+suffixName;
        // 文件上传后的路径
        String filePath = uploadDir;
        // 解决中文问题，liunx下中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        File dest = new File(filePath + fileName2);
        logger.info("保存在服务器的文件名" + fileName2);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //只有这三种格式的图片才能上传到服务器
        if(suffixName.equals(".jpg")||suffixName.equals(".png")||suffixName.equals(".jpeg")) {
            try {
                    file.transferTo(dest);//上传图片
                    logger.info("上传成功后的文件路径未：" + filePath + fileName2);
                    String FilePath=filePath+fileName2;
                    pictureMapper.insertFilePath(FilePath,fileName,timestamp,id);
                    System.out.println(fileName + "上传成功");

            } catch (IllegalStateException e) {
                e.printStackTrace();
                System.out.println(fileName + "上传失败");
                registerbz result = new registerbz();
                result.setSuccess(0);
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(fileName + "上传失败");
                registerbz result = new registerbz();
                result.setSuccess(0);
                return result;
            }
            registerbz result = new registerbz();
            result.setSuccess(1);//
            return result;
        }
        else {
            System.out.println(fileName + "上传失败");
            registerbz result = new registerbz();
            result.setSuccess(0);
            return result;

        }
    }

    }
