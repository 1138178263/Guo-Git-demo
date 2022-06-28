package com.xiaoguo.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoguo.smartcampus.pojo.Clazz;

import java.util.List;

public interface ClazzService extends IService<Clazz> {
    //班级分页模糊查询
    IPage<Clazz> getClazzsByOpr(Page<Clazz> page, Clazz clazz);
    //学生页查询班级信息
    List<Clazz> getClazzs();
}
