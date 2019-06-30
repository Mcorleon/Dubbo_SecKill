package com.tqh.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class OrderMessage {
  private String id;
  private String exchange_name;
  private String routing_key;
  private String message_body;
  private Long retry_count;
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date message_time;
  private Long state;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getExchange_name() {
    return exchange_name;
  }

  public void setExchange_name(String exchange_name) {
    this.exchange_name = exchange_name;
  }

  public String getRouting_key() {
    return routing_key;
  }

  public void setRouting_key(String routing_key) {
    this.routing_key = routing_key;
  }

  public String getMessage_body() {
    return message_body;
  }

  public void setMessage_body(String message_body) {
    this.message_body = message_body;
  }

  public Long getRetry_count() {
    return retry_count;
  }

  public void setRetry_count(Long retry_count) {
    this.retry_count = retry_count;
  }

  public Date getMessage_time() {
    return message_time;
  }

  public void setMessage_time(Date message_time) {
    this.message_time = message_time;
  }

  public Long getState() {
    return state;
  }

  public void setState(Long state) {
    this.state = state;
  }
}
