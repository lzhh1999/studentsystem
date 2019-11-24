package com.lhb.studentsystem.mapper;

import com.lhb.studentsystem.model.UserWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserWorkMapper {
    //查询所有学生作业
    List<UserWork> findAllUserWork();

    //根据作业人id查询
    Integer findUserWorkByUserId(Integer userId);

    //删除作业
    void userWorkDeleteOne(Integer id);

    //根据作业ID查询
    Integer findByHomeWorkId(Integer homeworkId);
}
