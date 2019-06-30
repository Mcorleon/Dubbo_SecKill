package com.tqh.mapper;

import com.tqh.model.Order;
import com.tqh.model.OrderVo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Mcorleon
 * @Date 2019/2/23 15:10
 */
@Repository
public interface OrderMapper {
    @Insert("INSERT INTO tb_order(id, user_id, goods_id, address_id, goods_name, goods_num, good_price, state, order_time) VALUES (#{id},#{user_id},#{goods_id},#{address_id},#{goods_name},#{goods_num},#{good_price},#{state},#{order_time})")
    void inserOrder(Order order);

    @Select("SELECT tbo.id,tbo.user_id,ta.nickname,tbo.goods_id,tbo.address_id,tbo.goods_name,tbo.goods_num,tbo.good_price,tbo.state,tbo.order_time,tbo.pay_time,ta.address_detail FROM tb_order tbo,tb_address ta WHERE tbo.user_id=#{uid} AND ta.id=tbo.address_id")
    List<OrderVo> getOrderVoByUid(String uid);

    @Select("SELECT * FROM tb_order tbo WHERE tbo.id=#{order_id}")
    Order getOrderById(String order_id);
}
