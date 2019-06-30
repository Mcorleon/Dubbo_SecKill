package com.tqh.mapper;

import com.tqh.model.Role;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:44
 */
@Repository
public interface RoleMapper {
    @Select("select * from user_role where UID=#{id}")
    Long[] findRolebyUser(Long id);

    @Select("select * from tb_role where ID=#{id}")
    Role findRolebyID(Long id);
}
