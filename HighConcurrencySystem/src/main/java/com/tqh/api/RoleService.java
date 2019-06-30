package com.tqh.api;

import com.tqh.model.Role;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:43
 */
public interface RoleService {
   Long[] findRolesByUser(Long id);

     Role findRolebyID(Long id);
}
