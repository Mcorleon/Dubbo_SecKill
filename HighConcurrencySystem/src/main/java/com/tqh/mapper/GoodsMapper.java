package com.tqh.mapper;

import com.tqh.model.GoodIDAndStock;
import com.tqh.model.Goods;
import com.tqh.model.MiaoshaGoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Mcorleon
 * @Date 2019/2/21 20:15
 */
@Repository
public interface GoodsMapper {
    @Select("SELECT * FROM tb_goods")
    List<Goods> getGoodsList();

    @Select("SELECT tm.id,tg.name,tg.img,tm.start_time,tm.end_time,tg.price,tm.miaosha_price,tg.stock FROM tb_goods tg,tb_miaosha_goods tm WHERE tg.id=#{goods_id} AND tm.goods_id=tg.id")
    List<MiaoshaGoodsVo> getMiaoshaGoodsList(@Param("goods_id")String goods_id);

    @Select("SELECT tm.id,tg.name,tg.img,tm.goods_id,tm.start_time,tm.end_time,tg.price,tm.miaosha_price,tg.stock FROM tb_goods tg,tb_miaosha_goods tm WHERE tm.id=#{id} AND tm.goods_id=tg.id")
    MiaoshaGoodsVo getMiaoshaGoodByID(String id);

    @Update("UPDATE tb_goods tg SET tg.stock=tg.stock-1 WHERE tg.id=#{goods_id} AND tg.stock>0")
    boolean decreaseStock(String goods_id);

    @Select("SELECT tmg.id, tg.stock FROM tb_miaosha_goods tmg,tb_goods tg WHERE tmg.goods_id=tg.id")
    List<GoodIDAndStock> getGoodsIDAndStock();
}
