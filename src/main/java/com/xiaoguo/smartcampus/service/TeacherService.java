package com.xiaoguo.smartcampus.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoguo.smartcampus.pojo.LoginForm;
import com.xiaoguo.smartcampus.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    //登录验证
    Teacher login(LoginForm loginForm);

    //根据用户类型返回首页
    Teacher getTeacherById(Long userId);

    //模糊分页查询教师信息
    IPage<Teacher> getTeachers(Page<Teacher> page, Teacher teacher);

    //修改用户密码
    Teacher updateUserPwd(Long userId, String oldPwd1);
}
