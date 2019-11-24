package com.lhb.studentsystem.service;

import com.lhb.studentsystem.model.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
    Role findById(Integer id);

    List<Role> findAllRole(int page , int limit);

    List<Role> getAllRole();

    Role findByName(@Param("name") String name);

    void deleteRole(Integer id);

    void addRole(Role role);

    void roleUpdate(Role role);
}
