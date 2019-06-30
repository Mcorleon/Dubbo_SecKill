package com.tqh.mapper;

import com.tqh.model.Address;
import com.tqh.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:34
 */
@Repository
public interface UserMapper {
    @Select("select * from tb_user where NickName=#{name}")
    User findByLoginName(String name);

    @Select("SELECT * FROM tb_address ta WHERE ta.nickname=#{nickName}")
    List<Address> getAddressByNickName(String nickName);

    @Select("SELECT * FROM tb_address ta WHERE ta.id=#{address_id}")
    Address getAddressByAddressID(String address_id);
}
