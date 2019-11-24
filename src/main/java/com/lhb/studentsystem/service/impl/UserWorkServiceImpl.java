package com.lhb.studentsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.lhb.studentsystem.mapper.UserWorkMapper;
import com.lhb.studentsystem.model.UserWork;
import com.lhb.studentsystem.service.UserWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserWorkServiceImpl implements UserWorkService {
    @Autowired
    UserWorkMapper userWorkMapper;

    //查询所有学生作业
    @Override
    public List<UserWork> findAllUserWork(int page , int limit) {
        PageHelper.startPage(page, limit);
        return userWorkMapper.findAllUserWork();
    }

    @Override
    public Integer findUserWorkByUserId(Integer userId) {
        return userWorkMapper.findUserWorkByUserId(userId);
    }

    @Override
    public void userWorkDeleteOne(Integer id) {
        userWorkMapper.userWorkDeleteOne(id);
    }

    @Override
    public Integer findByHomeWorkId(Integer homeworkId) {
        return userWorkMapper.findByHomeWorkId(homeworkId);
    }
}
