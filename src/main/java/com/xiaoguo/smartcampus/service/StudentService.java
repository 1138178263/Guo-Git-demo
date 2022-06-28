package com.xiaoguo.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoguo.smartcampus.pojo.LoginForm;
import com.xiaoguo.smartcampus.pojo.Student;

public interface StudentService extends IService<Student> {
    //登录验证
    Student login(LoginForm loginForm);

    //根据用户类型返回首页
    Student getStudentById(Long userId);

    //模糊分页查询学生信息
    IPage<Student> getStudentByOpr(Page<Student> page, Student student);

    //修改用户密码
    Student updateUserPwd(Long userId, String oldPwd1);
}
