package com.tqh.api;

import com.tqh.model.Permission;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:46
 */
public interface PermissionService {
   Long[] findPermissionsByRole(Long id);

     Permission findPermissionByID(Long id);
}
