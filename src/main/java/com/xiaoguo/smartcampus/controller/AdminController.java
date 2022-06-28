package com.xiaoguo.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoguo.smartcampus.pojo.Admin;
import com.xiaoguo.smartcampus.service.AdminService;
import com.xiaoguo.smartcampus.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * 模糊分页查询管理员信息
     * @param pageNo
     * @param pageSize
     * @param admin
     * @return
     */
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable("pageNo") Integer pageNo,
                              @PathVariable("pageSize") Integer pageSize,
                              Admin admin){
        Page<Admin> page = new Page<>(pageNo,pageSize);
        IPage iPage = adminService.getAllAdmin(page,admin);
        return Result.ok(iPage);
    }

    /**
     * 添加和修改管理员信息
     * @param admin
     * @return
     */
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    /**
     * 单个，批量删除管理员信息
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids){
        adminService.removeByIds(ids);
        return Result.ok();
    }
}
