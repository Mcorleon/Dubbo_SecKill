package com.tqh.model;

import java.io.Serializable;
public class Address implements Serializable{
  private String id;
  private String uid;
  private String nickname;
  private String real_name;
  private String phone;
  private String address_detail;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getReal_name() {
    return real_name;
  }

  public void setReal_name(String real_name) {
    this.real_name = real_name;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAddress_detail() {
    return address_detail;
  }

  public void setAddress_detail(String address_detail) {
    this.address_detail = address_detail;
  }
}
