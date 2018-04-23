package com.example.demo.bean;

import javax.persistence.*;

@Table(name = "users")
@Entity
public class users {
    @GeneratedValue(strategy=GenerationType.IDENTITY)//自增属性
     @Id//主键
    private Integer id;//一定要写出integer不能写出int，因为int是一个类型而非对象
    private String username;
    private String password;
    private Integer success;//这个变量用在注册是否成功

    public Integer getSuccess() {
        return success;
    }

    public users() {
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
