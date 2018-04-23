package com.example.demo.dao;

import com.example.demo.bean.pictures;
import com.example.demo.bean.registerbz;
import com.example.demo.bean.users;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
@Mapper
public interface UserMapper {
    //通过用户名去获得所有的用户信息
    @Select("select * from users where username  = #{name}")
    List<users> findUserByName(String name);
    //注册接口，输入用户名和密码
    @Insert("insert into users(username,password) values (#{username} , #{password})")
   void insertUserInfor(@Param("username") String username, @Param("password") String password);
    //通过用户id找到用户信息
    @Select("select * from users where id  = #{id}")
    List<users> findUserById(int id);
    //登录接口
    @Select("select * from users where username= #{username} and password =#{password}")
    List<users> login(@Param("username") String username,@Param("password") String password);
    //根据用户名去查找图片
    @Select("select n.* from users m , pictures n where m.id=n.pt_foreign_key and m.username=#{username}")
    List<pictures> findPicturesByUserName(String username);
    @Select("select * from pictures")
    List<pictures> findAllPt();

}
