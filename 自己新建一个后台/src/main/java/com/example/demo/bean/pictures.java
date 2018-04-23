package com.example.demo.bean;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "pictures")
@Entity
public class pictures {
    @GeneratedValue(strategy=GenerationType.IDENTITY)//自增属性
    @Id
    private Integer ptid;
    private String pt_src;
    private String pt_name;
    private Timestamp pt_createTime;//数据类型一定要与实际的mysql中定义的类型匹配否则找不到
    private Integer pt_foreign_key;
    private Integer success;
    private String  username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //private Blob pt_file;//二进制流文件，真实的图片
    public pictures(Integer ptid,String pt_src, String pt_name, Timestamp pt_createTime, Integer pt_foreign_key) {
        this.ptid=ptid;
        this.pt_src = pt_src;
        this.pt_name = pt_name;
        this.pt_createTime = pt_createTime;
        this.pt_foreign_key = pt_foreign_key;

    }

    public Integer getPtid() {
        return ptid;
    }

    public void setPtid(Integer ptid) {
        this.ptid = ptid;
    }

    public String getPt_src() {
        return pt_src;
    }

    public void setPt_src(String pt_src) {
        this.pt_src = pt_src;
    }

    public String getPt_name() {
        return pt_name;
    }

    public void setPt_name(String pt_name) {
        this.pt_name = pt_name;
    }

    public Timestamp getPt_createTime() {
        return pt_createTime;
    }

    public void setPt_createTime(Timestamp pt_createTime) {
        this.pt_createTime = pt_createTime;
    }

    public Integer getPt_foreign_key() {
        return pt_foreign_key;
    }

    public void setPt_foreign_key(Integer pt_foreign_key) {
        this.pt_foreign_key = pt_foreign_key;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }
    //    public Blob getPt_file() {
//        return pt_file;
//    }
//
//    public void setPt_file(Blob pt_file) {
//        this.pt_file = pt_file;
//    }
}
