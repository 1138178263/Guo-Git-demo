package com.xiaoguo.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoguo.smartcampus.pojo.Student;
import com.xiaoguo.smartcampus.pojo.Teacher;
import com.xiaoguo.smartcampus.service.StudentService;
import com.xiaoguo.smartcampus.util.MD5;
import com.xiaoguo.smartcampus.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * 分页查询学生信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNo") Integer pageNo,
                              @PathVariable("pageSize") Integer pageSize,
                              Student student){
        Page<Student> page = new Page<>();
        IPage<Student> Ipage = studentService.getStudentByOpr(page,student);
        return Result.ok(Ipage);
    }

    /**
     * 添加或修改学生数据
     * @param student
     * @return
     */
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        Integer id = student.getId();
        if(id==null||id==0){
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return Result.ok();
    }

    /**
     * 单个，批量删除学生信息
     * @return
     */
    @DeleteMapping("/delStudentById")
    public Result delStudentById(@RequestBody List<Integer> ids){
        studentService.removeByIds(ids);
        return Result.ok();
    }

}
