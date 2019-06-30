package com.tqh.mapper;

import com.tqh.model.Permission;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:48
 */
@Repository
public interface PermissionMapper {
    @Select("select * from role_permission where RID=#{id}")
    Long[] findPermissionsByRole(Long id);

    @Select("select * from tb_permission where ID=#{id}")
    Permission findPermissionByID(Long id);
}
