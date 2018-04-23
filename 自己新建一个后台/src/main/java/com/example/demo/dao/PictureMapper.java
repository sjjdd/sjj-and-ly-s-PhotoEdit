package com.example.demo.dao;


import com.example.demo.bean.pictures;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface PictureMapper {
    @Select("select  *  from pictures where pt_name= #{name}")
    List<pictures> findpicturesbyName(@Param("name") String name);
    @Select("select * from pictures")
    List<pictures> findAllPt();
    @Select("select * from pictures where pt_foreign_key = #{userid}")
    List<pictures> findPicturesByUser(int userid);
    @Delete("delete from pictures where ptid = #{ptid}")
    void deletePictures(int ptid);
    @Insert("insert into pictures (pt_src,pt_name,pt_createTime,pt_foreign_key) values (#{filepath},#{filename},#{filecreateTime},#{fileowner})")
    void  insertFilePath(@Param("filepath") String filepath,@Param("filename") String filename,@Param("filecreateTime") Timestamp filecreateTime,@Param("fileowner") Integer fileowner);
   //    @Insert("insert into pictures(pt_src,pt_name,pt_createTime,pt_foreign_key)")
  //根据用户名以及图片名去查找图片
  @Select("select n.* from users m , pictures n where m.id=n.pt_foreign_key and  m.username=#{username}  and n.pt_name=#{pt_name}")
  List<pictures> findPicturesByUserName(@Param("username") String username,@Param("pt_name") String pt_name);
    //根据用户名去查找图片
    @Select("select n.* from users m , pictures n where m.id=n.pt_foreign_key and m.username=#{username}")
    List<pictures> findPicturesByUserName2(String username);
}
