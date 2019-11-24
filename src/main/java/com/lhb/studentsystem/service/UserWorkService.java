package com.lhb.studentsystem.service;

import com.lhb.studentsystem.model.UserWork;

import java.util.List;

public interface UserWorkService {
    //查询所有学生作业
    List<UserWork> findAllUserWork(int page , int limit);

    //根据作业人id查询
    Integer findUserWorkByUserId(Integer userId);

    //删除作业
    void userWorkDeleteOne(Integer id);

    //根据作业ID查询
    Integer findByHomeWorkId(Integer homeworkId);
}
