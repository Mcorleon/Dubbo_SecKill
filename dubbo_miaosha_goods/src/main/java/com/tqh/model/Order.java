package com.tqh.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable{

  private String id;
  private String user_id;
  private String goods_id;
  private String address_id;
  private String goods_name;
  private Long goods_num;
  private Double good_price;

  //0:未支付 1：待发货 2:已发货 3：已收货 4：已取消 5：已完成
  private int state;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date order_time;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date pay_time;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getGoods_id() {
    return goods_id;
  }

  public void setGoods_id(String goods_id) {
    this.goods_id = goods_id;
  }

  public String getAddress_id() {
    return address_id;
  }

  public void setAddress_id(String address_id) {
    this.address_id = address_id;
  }

  public String getGoods_name() {
    return goods_name;
  }

  public void setGoods_name(String goods_name) {
    this.goods_name = goods_name;
  }

  public Long getGoods_num() {
    return goods_num;
  }

  public void setGoods_num(Long goods_num) {
    this.goods_num = goods_num;
  }

  public Double getGood_price() {
    return good_price;
  }

  public void setGood_price(Double good_price) {
    this.good_price = good_price;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public Date getOrder_time() {
    return order_time;
  }

  public void setOrder_time(Date order_time) {
    this.order_time = order_time;
  }

  public Date getPay_time() {
    return pay_time;
  }

  public void setPay_time(Date pay_time) {
    this.pay_time = pay_time;
  }
}
