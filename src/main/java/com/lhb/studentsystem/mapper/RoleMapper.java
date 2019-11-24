package com.lhb.studentsystem.mapper;

import com.lhb.studentsystem.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    Role findById(@Param("id")Integer id);

    List<Role> findAllRole();

    Role findByName(@Param("name") String  name);
    //删除角色
    void deleteRole(Integer id);

    //添加角色
    void addRole(Role role);

    //更新角色
    void roleUpdate(Role role);
}
