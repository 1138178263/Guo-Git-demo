package com.xiaoguo.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoguo.smartcampus.pojo.Grade;
import com.xiaoguo.smartcampus.service.GradeService;
import com.xiaoguo.smartcampus.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "年级控制器") //Swagger2指标检测用于测试
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    //请求网址: http://localhost:9001/sms/gradeController/getGrades/1/3?gradeName=???
    //用户类型：管理员
    //分页查询年纪表
    @ApiOperation("根据年级名称模糊查询，带分页")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradeByOpr(@ApiParam("分页查询的页码数") @PathVariable("pageNo") Integer pageNo ,
                            @ApiParam("分页查询的页大小") @PathVariable("pageSize") Integer pageSize,
                            @ApiParam("模糊查询的名称条件") String gradeName){
        //分页带条件查询
        Page<Grade> page = new Page<>(pageNo,pageSize);
        //通过服务层查询
        IPage<Grade> IPage = gradeService.getGradeByOpr(page,gradeName);
        //封装Result对象并返回
        return Result.ok(IPage);
    }

    /**
     * 添加年级信息
     * @return
     */
    @ApiOperation("修改Grade信息")
    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@ApiParam("JSON的Grade对象") @RequestBody Grade grade){
        //接收参数
        //调用服务层完成添加功能
        gradeService.saveOrUpdate(grade);   //直接调用了IService的方法
        return Result.ok();
    }

    /**
     * 单个删除，批量删除年级信息
     * @return
     */
    @ApiOperation("删除Grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@ApiParam("要删除的所有id的JSON集合") @RequestBody List<Integer> ids){
        gradeService.removeByIds(ids);  //直接调用了IService的方法
        return Result.ok();
    }

    /**
     * 班级页查询年级信息
     * @return
     */
    @GetMapping("getGrades")
    public Result getGrades(){
        List<Grade> grades = gradeService.getGrades();
        return Result.ok(grades);
    }
}
