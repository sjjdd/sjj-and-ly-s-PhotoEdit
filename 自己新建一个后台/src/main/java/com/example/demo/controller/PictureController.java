package com.example.demo.controller;

import com.example.demo.bean.pictures;
import com.example.demo.dao.PictureMapper;

import com.example.demo.dao.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController

@RequestMapping({"/picture"})
public class PictureController {
    @Autowired
     PictureMapper pictureMapper;
     UserMapper userMapper;
    //通过图片名找图片
    @RequestMapping(value="/picture")
    @ResponseBody
    public List<pictures> getPictureInorByName(@RequestParam(value = "pt_name",defaultValue = "all")String pt_name,
                                               @RequestParam(value="username") String username){
       List<pictures> picture=new ArrayList<>();
        if(!pt_name.equals("all")) {
            try {
             picture = pictureMapper.findPicturesByUserName(username,pt_name);
            } catch (Exception e) {
                for(int i=0;i<picture.size();i++)
                picture.get(i).setSuccess(0);//success=0表明并没有这张图片
                return picture;
            } finally {
                for(int i=0;i<picture.size();i++)
                if (!picture.get(i).getPt_name().isEmpty())
                    picture.get(i).setSuccess(1);//success=1表明有这张图片
                return picture;
            }
        }else{
            //如果没有输入图片名则返回与用户名相关的所有哦图片
            picture=pictureMapper.findPicturesByUserName2(username);
            return picture;
        }
    }
    //查找所有的图片信息，指的是查找存放在数据库中的所有图片的信息
    @RequestMapping(value="/findAllPt")
    @ResponseBody
    public List<pictures> getAllPictures(){
        List<pictures> picturesList=pictureMapper.findAllPt();
        for(int i=0;i<picturesList.size();i++)
        {
            if(!picturesList.get(i).getPt_src().isEmpty())
                picturesList.get(i).setSuccess(1);
        }
        return picturesList;
    }
    //根据用户id去查找与之相关的所有图片
    @RequestMapping(value="/IdToPt")
    @ResponseBody
    public List<pictures> getPicturesByUserId(int userid){
        List<pictures> picturesList=pictureMapper.findPicturesByUser(userid);
        for(int i=0;i<picturesList.size();i++)
        {
            picturesList.get(i).setSuccess(1);
        }
        return picturesList;
    }
    //根据图片id去删除图片
    @RequestMapping(value="/deletepicture")
    @ResponseBody
    public void deletePictures(int ptid){
        pictureMapper.deletePictures(ptid);
    }

    //根据传来的2个参数，第一个前端的当前页数，一页显示多少条
    @RequestMapping(value="/dividePage")
    @ResponseBody
    public List<pictures> dividePage(@RequestParam(value="currentPage") int currentPage,@RequestParam(value="onePages") int onePages){
        List<pictures> picturesList=pictureMapper.findAllPt();
        List<pictures> newpicturesList=new ArrayList<>();
        for(int i=(currentPage-1)*onePages;i<(currentPage-1)*onePages+onePages&&i<picturesList.size();i++){
            newpicturesList.add(picturesList.get(i));
        }
        return newpicturesList;
    }
    //分页需要的通过用户名找图片
    @RequestMapping(value="/picture2")
    @ResponseBody
    public List<pictures> getPictureInorByName2(@RequestParam(value = "pt_name",defaultValue = "all")String pt_name,
                                               @RequestParam(value="username") String username,
                                                @RequestParam(value="currentPage") int currentPage,@RequestParam(value="onePages") int onePages){
        List<pictures> picturesList=new ArrayList<>();
        if(!pt_name.equals("all")) {
            try {
                picturesList= pictureMapper.findPicturesByUserName(username,pt_name);
            } catch (Exception e) {
                for(int i=0;i<picturesList.size();i++)
                    picturesList.get(i).setSuccess(0);//success=0表明并没有这张图片
                List<pictures> newpicturesList=new ArrayList<>();
                for(int i=(currentPage-1)*onePages;i<(currentPage-1)*onePages+onePages&&i<picturesList.size();i++){
                    newpicturesList.add(picturesList.get(i));
                }
                return newpicturesList;

            } finally {
                for(int i=0;i<picturesList.size();i++)
                    if (!picturesList.get(i).getPt_name().isEmpty())
                        picturesList.get(i).setSuccess(1);//success=1表明有这张图片
                List<pictures> newpicturesList=new ArrayList<>();
                for(int i=(currentPage-1)*onePages;i<(currentPage-1)*onePages+onePages&&i<picturesList.size();i++){
                    newpicturesList.add(picturesList.get(i));
                }
                return newpicturesList;
              //  return picturesList;
            }
        }else{
            //如果没有输入图片名则返回与用户名相关的所有哦图片
            picturesList=pictureMapper.findPicturesByUserName2(username);
            List<pictures> newpicturesList=new ArrayList<>();
            for(int i=(currentPage-1)*onePages;i<(currentPage-1)*onePages+onePages&&i<picturesList.size();i++){
                newpicturesList.add(picturesList.get(i));
            }
            return newpicturesList;
            //return picturesList;
        }
    }
}
