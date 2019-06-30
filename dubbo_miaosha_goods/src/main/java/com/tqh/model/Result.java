package com.tqh.model;

import java.io.Serializable;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 20:04
 */
public class Result implements Serializable {
    private int code;

    //其他所有错误
    public static final Result OTHER_ERR=new Result(100000,"其他错误");

    private String msg;
    private String detail;
    //5001XX用户错误
    public static final Result PERMISSION_ERR=new Result(500100,"权限错误");
    public static final Result UNKONWN_USER_ERR=new Result(500101,"未知用户");
    public static final Result INCORRECT_PSW_ERR=new Result(500102,"校验失败");
    public static final Result LOCK_ERR=new Result(500103,"账号已锁定");
    public static final Result EXCESSIVE_ERR=new Result(500104,"错误次数过多");
    //5002XX订单错误
    public static final Result UNKONWN_GOODS_ERR=new Result(500200,"未知商品");
    public static final Result LACK_STOCK_ERR=new Result(500201,"库存不足");
    public static final Result BEFORE_MIAOSHA_ERR=new Result(500202,"活动未开始");
    public static final Result END_MIAOSHA_ERR=new Result(500203,"活动已经结束");
    public static final Result UNKONWN_ADDRESS_ERR=new Result(500204,"未知地址");
    //5003XX请求错误
    public static final Result ACCESS_LIMIT_ERR=new Result(500300,"请求过于频繁");

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Result(int code, String msg){
        this.msg=msg;
        this.code=code;
    }
    public Result(int code, String msg, String detail){
        this.msg=msg;
        this.code=code;
        this.detail=detail;
    }
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString(){
        return "code:"+getCode()+",msg:"+getMsg();
    }

}
