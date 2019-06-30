package com.tqh.api.impl;

import com.tqh.mapper.RoleMapper;
import com.tqh.model.Role;
import com.tqh.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Mcorleon
 * @Date 2019/2/20 16:44
 */
@Service
public class RoleSeriveImpl implements RoleService{
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Long[] findRolesByUser(Long id) {
        return roleMapper.findRolebyUser(id);
    }

    @Override
    public Role findRolebyID(Long id) {
        return roleMapper.findRolebyID(id);
    }


}
