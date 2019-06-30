package com.tqh.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
  private Long id;
  private String nickname;
  private String password;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date lastlogintime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Date getLastlogintime() {
    return lastlogintime;
  }

  public void setLastlogintime(Date lastlogintime) {
    this.lastlogintime = lastlogintime;
  }

  @Override
  public String toString(){
      return "id="+getId()+",nickname="+getNickname()+",password="+getPassword()+",lastlogin="+getLastlogintime();
  }
}
