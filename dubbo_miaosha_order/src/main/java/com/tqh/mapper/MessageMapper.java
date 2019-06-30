package com.tqh.mapper;

import com.tqh.model.OrderMessage;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @Author Mcorleon
 * @Date 2019/4/16 10:10
 */
@Repository
public interface MessageMapper {
    @Insert("INSERT INTO tb_order_message(id,exchange_name,routing_key,message_body,retry_count,message_time,state) VALUES (#{id},#{exchange_name},#{routing_key},#{message_body},#{retry_count},#{message_time},#{state})")
    void addOrderMessage(OrderMessage orderMessage);

    @Delete("DELETE FROM tb_order_message tom WHERE tom.id=#{id}")
    void deleteOrderMessageById(String id);

    @Update("UPDATE tb_order_message tom SET tom.state=#{state} WHERE tom.id=#{id}")
    void updateOrderMessageStateById(@Param("id") String id, @Param("state") int state);

    @Select("SELECT * FROM tb_order_message tom WHERE tom.id=#{id}")
    OrderMessage getOrderMessageById(String id);

    @Update("UPDATE tb_order_message tom SET tom.retry_count=tom.retry_count+1 WHERE tom.id=#{id}")
    void increaseOrderMessageRetryCountById(String id);
}
