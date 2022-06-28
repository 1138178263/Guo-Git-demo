package com.xiaoguo.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoguo.smartcampus.pojo.Grade;

import java.util.List;

public interface GradeService extends IService<Grade> {
    //分页查询年级表
    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);
    //班级页查询年级信息
    List<Grade> getGrades();
}
