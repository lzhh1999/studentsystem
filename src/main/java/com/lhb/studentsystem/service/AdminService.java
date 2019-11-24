package com.lhb.studentsystem.service;

import com.lhb.studentsystem.model.AdminUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminService {
    //查询所有用户
    List<AdminUser> findAll(int page , int limit);

    //删除用户
    void deleteUser(Integer id);

    AdminUser findWithId(@Param("id") String id);

    Integer updateByRoleIdAndId(@Param("id") String id, @Param("roleId") Integer roleId);

    //根据角色id查询是否还有用户
    Integer findByRoleId (@Param("roleId") Integer roleId);

    //条件查询
    List<AdminUser> findUser(AdminUser adminUser,int page,int limit);
}
