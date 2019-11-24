package com.lhb.studentsystem.service;

import com.lhb.studentsystem.model.HomeWork;

import java.util.List;

public interface HomeWorkService {
    List<HomeWork> findAllHomeWork(int page, int limit);

    void deleteHomeWorkOne(Integer id);
}
