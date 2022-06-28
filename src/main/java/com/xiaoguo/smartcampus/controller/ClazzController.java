package com.xiaoguo.smartcampus.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xiaoguo.smartcampus.pojo.Clazz;
import com.xiaoguo.smartcampus.service.ClazzService;
import com.xiaoguo.smartcampus.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    /**
     * 模糊加分页查询班级信息
     * @param pageNO
     * @param pageSize
     * @param clazz
     * @return
     */
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsByOpr(
            @PathVariable("pageNo") Integer pageNO,
            @PathVariable("pageSize") Integer pageSize,
            Clazz clazz){

        Page<Clazz> page = new Page<>(pageNO,pageSize);
        IPage<Clazz> iPage = clazzService.getClazzsByOpr(page,clazz);
        return Result.ok(iPage);
    }

    /**
     * 添加,修改班级信息
     * @return
     */
    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz){
        clazzService.saveOrUpdate(clazz);
        return Result.ok();
    }

    /**
     * 单个，批量删除班级信息
     * @return
     */
    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids){
        clazzService.removeByIds(ids);
        return Result.ok();
    }

    /**
     * 学生页查询班级信息
     * @return
     */
    @GetMapping("/getClazzs")
    public Result getClazzs(){
        List<Clazz> clazzes = clazzService.getClazzs();
        return Result.ok(clazzes);
    }
}
