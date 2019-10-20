package com.lhb.studentsystem.mapper;

import com.lhb.studentsystem.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findByName(@Param("username") String username);

    void addUser(User user);

    User findById(@Param("id") String id);
}
