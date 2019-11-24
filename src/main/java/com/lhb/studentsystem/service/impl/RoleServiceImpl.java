package com.lhb.studentsystem.service.impl;

import com.lhb.studentsystem.mapper.RoleMapper;
import com.lhb.studentsystem.model.Role;
import com.lhb.studentsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    RoleMapper roleMapper;
    @Override
    public Role findById(Integer id) {
        return roleMapper.findById(id);
    }

    @Override
    public List<Role> findAllRole(int page , int limit) {
        return roleMapper.findAllRole();
    }

    @Override
    public Role findByName(String name) {
        return roleMapper.findByName(name);
    }

    @Override
    public void deleteRole(Integer id) {
         roleMapper.deleteRole(id);
    }

    @Override
    public void addRole(Role role) {
        roleMapper.addRole(role);
    }

    @Override
    public List<Role> getAllRole() {
        return roleMapper.findAllRole();
    }

    @Override
    public void roleUpdate(Role role) {
        roleMapper.roleUpdate(role);
    }
}
