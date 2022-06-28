package com.xiaoguo.smartcampus.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaoguo.smartcampus.pojo.Admin;
import com.xiaoguo.smartcampus.pojo.LoginForm;


public interface AdminService extends IService<Admin> {

    //登录验证
    Admin login(LoginForm loginForm);

    //根据用户类型返回首页
    Admin getAdminById(Long userId);

    //模糊分页查询管理员信息
    IPage getAllAdmin(Page<Admin> page, Admin admin);

    //修改用户密码
    Admin updateUserPwd(Long userId, String oldPwd1);

}
