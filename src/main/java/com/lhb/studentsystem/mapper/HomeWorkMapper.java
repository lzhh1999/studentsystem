package com.lhb.studentsystem.mapper;

import com.lhb.studentsystem.model.HomeWork;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HomeWorkMapper {
    List<HomeWork> findAllHomeWork();

    void deleteHomeWorkOne(Integer id);


}
