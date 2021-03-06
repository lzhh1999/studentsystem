package com.lhb.studentsystem.mapper;

import com.lhb.studentsystem.model.AdminUser;
import com.lhb.studentsystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByName(@Param("username") String username);

    void addUser(User user);

    User findById(@Param("id") String id);

    List<AdminUser> findAll();

    void deleteUser(Integer id);

    AdminUser findWithId(@Param("id") String id);

    //改变角色
    Integer updateByRoleIdAndId(@Param("id") String id, @Param("roleId") Integer roleId);

    //根据角色id查询是否还有用户
    Integer findByRoleId (@Param("roleId") Integer roleId);

    //条件查询
    List<AdminUser> findUser(AdminUser adminUser);
}
