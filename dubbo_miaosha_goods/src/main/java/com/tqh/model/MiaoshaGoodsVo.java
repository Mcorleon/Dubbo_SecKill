  package com.tqh.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class MiaoshaGoodsVo implements Serializable {
  private String id;
  private String goods_id;
  private String name;
  private String img;

  private Double miaosha_price;
  private Double price;

  private Long stock;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date start_time;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date end_time;
  public String getName() {
    return name;
  }
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGoods_id() {
    return goods_id;
  }

  public void setGoods_id(String goods_id) {
    this.goods_id = goods_id;
  }

  public Double getMiaosha_price() {
    return miaosha_price;
  }

  public void setMiaosha_price(Double miaosha_price) {
    this.miaosha_price = miaosha_price;
  }

  public Long getStock() {
    return stock;
  }

  public void setStock(Long stock) {
    this.stock = stock;
  }

  public Date getStart_time() {
    return start_time;
  }

  public void setStart_time(Date start_time) {
    this.start_time = start_time;
  }

  public Date getEnd_time() {
    return end_time;
  }

  public void setEnd_time(Date end_time) {
    this.end_time = end_time;
  }
}