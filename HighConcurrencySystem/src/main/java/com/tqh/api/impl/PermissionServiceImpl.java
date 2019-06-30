package com.tqh.api.impl;

import com.tqh.mapper.PermissionMapper;
import com.tqh.model.Permission;
import com.tqh.api.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:47
 */
@Repository
public class PermissionServiceImpl implements PermissionService{
    @Autowired
    PermissionMapper permissionMapper;
    @Override
    public Long[] findPermissionsByRole(Long id) {
        return permissionMapper.findPermissionsByRole(id);
    }

    @Override
    public Permission findPermissionByID(Long id) {
        return permissionMapper.findPermissionByID(id);
    }
}
