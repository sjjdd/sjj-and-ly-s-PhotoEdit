package com.example.demo.controller;

import com.example.demo.bean.pictures;
import com.example.demo.bean.registerbz;
import com.example.demo.bean.users;
import com.example.demo.dao.PictureMapper;
import com.example.demo.dao.UserMapper;

import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer.Vanilla.std;

@RestController

@RequestMapping({"/user"})
public class UserController{
//    static SqlSession session;
//    static{
//        try {
//            session = MybatisConf.getSqlSession(true);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }finally{
//            if(session == null){
//                session.close();
//            }
//        }
//    }
        @Autowired
        UserMapper userMapper;
        @RequestMapping(value="/user")
        @ResponseBody
        //通过用户名去数据库查找对应的用户密码
        //在数据库中有数据则会返回用户名以及密码的值，没有则不返回
        public users getUserInfoByName(String name) {
              users user=new users();
               try {
                    user = userMapper.findUserByName(name).get(0);
               }catch (Exception e){
                   user.setSuccess(0);//用户名不存在数据库中，此时可以注册
                   System.out.println("fail");
                   return user;
               }finally {
                   if(!user.getUsername().isEmpty())
                   user.setSuccess(1);//用户名存在数据库中，则返回success:0不可以注册
                   System.out.println("success");
                   return user;
               }
        }

//    @RequestMapping(value="/insert" ,method = RequestMethod.POST)
//    @ResponseBody
//    //注册信息插入表中
//    public users insertUserInform(users user)
//    {
//      // if(!(user.getUsername().isEmpty())) {//用户名不为空即可
//         List<users> user1 = userMapper.findUserByName(user.getUsername());
//        // registerbz registerbzs=new registerbz();
//          if (user1.size() <= 0)//即在数据库中找不到这条数据,则可以插入该数据，即可以注册
//          {
//              userMapper.insertUserInfor(user.getUsername(), user.getPassword());
//              user.setSuccess(1);
//              user.setId(-1);
//              System.out.println("insert success");
//              return user;
//          }
////
//          else {
//                user.setSuccess(0);
//                user.setId(-1);
//                System.out.println("insert failed");
//                return user;
//           }
//     //  }else
//        // {
////           System.out.println("insert a valid name");
////
////       }
//    }


    @RequestMapping(value="/insert1" ,method = RequestMethod.GET)
    @ResponseBody
    //注册信息插入表中
    public users insertUserInform1(String username,String password)
    {
        // if(!(user.getUsername().isEmpty())) {//用户名不为空即可
        List<users> user1 = userMapper.findUserByName(username);
        users user=new users();
        if (user1.size() <= 0)//即在数据库中找不到这条数据,则可以插入该数据，即可以注册
        {
            userMapper.insertUserInfor(username, password);
            System.out.println("insert success");
            user.setSuccess(1);
            return user;
        }
//
        else {
            System.out.println("insert failed");
            user.setSuccess(0);
            return user;
        }
        //  }else
        // {
//           System.out.println("insert a valid name");
//
//       }
    }

    @RequestMapping(value = "/id")
    @ResponseBody
    //通过用户名去数据库查找对应的用户密码
    public users getUserInforByID(int id){
        users user=new users();
        try {
            user = userMapper.findUserById(id).get(0);
        }catch (Exception e){
            user.setSuccess(0);//用户名不存在数据库中，此时可以注册
            return user;
        }finally {
            if(!user.getUsername().isEmpty())
                user.setSuccess(1);//用户名存在数据库中，则返回success:0不可以注册
            return user;
        }
    }
    //登录接口，根据success字段返回1能成功登录，返回0不能
    @RequestMapping(value = "/login")
    @ResponseBody
    public users loginPD(String username,String password){
        users user=new users();
        try{
            user=userMapper.login(username,password).get(0);
        }catch (Exception e){
            user.setSuccess(0);//用户名不存在数据库中，此时可以注册
            System.out.println("login fail");
            return user;
        }finally {
            if(!user.getUsername().isEmpty())
                user.setSuccess(1);//用户名存在数据库中，则返回success:0不可以注册
            System.out.println("login success");
            return user;
        }
    }
    //根据用户名去查找与之相关所有图片

    //根据用户名去查找数据库中图片信息，若用户名有值，则返回与之对应的图片信息，若无值，则返回所有图片信息
    @RequestMapping(value = "/findPtByUName")
    @ResponseBody
    public List<pictures> findPicturesByN(@RequestParam(value = "username",defaultValue = "all") String username) {
        Map<String,Integer> map=new HashMap<String, Integer>();
        if (!username.equals("all")) {
            List<pictures> picturesList = userMapper.findPicturesByUserName(username);
            List<users> users=userMapper.findUserById(picturesList.get(0).getPt_foreign_key());
            for (int i = 0; i < picturesList.size(); i++) {
                picturesList.get(i).setSuccess(1);
                picturesList.get(i).setUsername(users.get(0).getUsername());
            }
            System.out.println("findPtByUName" + picturesList);
            return picturesList;
        }
        else{
            List<pictures> picturesList=userMapper.findAllPt();
            for (int i = 0; i < picturesList.size(); i++) {
                picturesList.get(i).setSuccess(1);
                picturesList.get(i).setUsername(userMapper.findUserById(picturesList.get(i).getPt_foreign_key()).get(0).getUsername());
            }
            return  picturesList;
        }

    }
    //根据用户名去查找数据库中图片的数量，若用户名有值，则返回与之对应的图片数量，若无值，则返回所有图片数量
    @RequestMapping(value = "/findPtByUName2")
    @ResponseBody
    public List<pictures> findPicturesByN2(@RequestParam(value = "username",defaultValue = "all") String username
            ,@RequestParam(value="currentPage") int currentPage,@RequestParam(value="onePages") int onePages) {
        Map<String,Integer> map=new HashMap<String, Integer>();
        if (!username.equals("all")) {
            List<pictures> picturesList = userMapper.findPicturesByUserName(username);
            List<users> users=userMapper.findUserById(picturesList.get(0).getPt_foreign_key());
            for (int i = 0; i < picturesList.size(); i++) {
                picturesList.get(i).setSuccess(1);
                picturesList.get(i).setUsername(users.get(0).getUsername());
            }
            System.out.println("findPtByUName" + picturesList);
            List<pictures> newpicturesList=new ArrayList<>();
            for(int i=(currentPage-1)*onePages;i<(currentPage-1)*onePages+onePages&&i<picturesList.size();i++){
                newpicturesList.add(picturesList.get(i));
            }
            return newpicturesList;
        }
//            return picturesList;
//        }
        else{
            List<pictures> picturesList=userMapper.findAllPt();
            for (int i = 0; i < picturesList.size(); i++) {
                picturesList.get(i).setSuccess(1);
                picturesList.get(i).setUsername(userMapper.findUserById(picturesList.get(i).getPt_foreign_key()).get(0).getUsername());
            }
            List<pictures> newpicturesList=new ArrayList<>();
            for(int i=(currentPage-1)*onePages;i<(currentPage-1)*onePages+onePages&&i<picturesList.size();i++){
                newpicturesList.add(picturesList.get(i));
            }
            return newpicturesList;
        }


    }
    //得到图片数量
    @RequestMapping(value = "/findPtNumByUName")
    @ResponseBody
    public Map<String ,Integer> findPicturesNumByN(@RequestParam(value = "username",defaultValue = "all") String username) {
        Map<String,Integer> map=new HashMap<String, Integer>();
        if (!username.equals("all")) {
            List<pictures> picturesList = userMapper.findPicturesByUserName(username);
            int picturesSize=0;
            for (int i = 0; i < picturesList.size(); i++) {
                picturesList.get(i).setSuccess(1);
                picturesSize++;
            }
            System.out.println("findPtByUName" + picturesList);
            map.put("NumofPt",picturesSize);
            return map;
        }
        else{
            List<pictures> picturesList=userMapper.findAllPt();
            int picturesSize=0;
            for(int i=0;i<picturesList.size();i++){
                picturesList.get(i).setSuccess(1);
                picturesSize++;
            }
            map.put("NumofPt",picturesSize);
            return  map;
        }

    }
}
