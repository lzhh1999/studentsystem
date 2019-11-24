package com.lhb.studentsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.lhb.studentsystem.mapper.UserMapper;
import com.lhb.studentsystem.model.AdminUser;
import com.lhb.studentsystem.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<AdminUser> findAll(int page , int limit) {
        PageHelper.startPage(page, limit);
        return userMapper.findAll();
    }

    @Override
    public void deleteUser(Integer id) {
        userMapper.deleteUser(id);
    }

    @Override
    public AdminUser findWithId(String id) {
        return userMapper.findWithId(id);
    }

    @Override
    public Integer updateByRoleIdAndId(String id, Integer roleId) {
        return userMapper.updateByRoleIdAndId(id,roleId);
    }

    @Override
    public Integer findByRoleId(Integer roleId) {
        return userMapper.findByRoleId(roleId);
    }

    @Override
    public List<AdminUser> findUser(AdminUser adminUser,int page,int limit) {
        PageHelper.startPage(page, limit);
        return userMapper.findUser(adminUser);
    }
}
