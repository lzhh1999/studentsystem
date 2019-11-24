package com.lhb.studentsystem.mapper;

import com.lhb.studentsystem.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface AdminMapper {
    @Select("select * from t_admin where username=#{username} and password=#{password}")
    Admin findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Update("update t_admin set password=#{repassword} where username=#{username}")
    Integer updatePassword(@Param("repassword") String repassword, @Param("username") String username);

    @Select("select * from t_admin where password=#{password}")
    Admin findByPassword(String password);
}
