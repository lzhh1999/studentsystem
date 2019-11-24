package com.lhb.studentsystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.lhb.studentsystem.mapper.HomeWorkMapper;
import com.lhb.studentsystem.model.HomeWork;
import com.lhb.studentsystem.service.HomeWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeWorkServiceImpl implements HomeWorkService {
    @Autowired
    HomeWorkMapper homeWorkMapper;

    @Override
    public List<HomeWork> findAllHomeWork(int page, int limit) {
        PageHelper.startPage(page, limit);
        return homeWorkMapper.findAllHomeWork();
    }

    @Override
    public void deleteHomeWorkOne(Integer id) {
        homeWorkMapper.deleteHomeWorkOne(id);
    }
}
