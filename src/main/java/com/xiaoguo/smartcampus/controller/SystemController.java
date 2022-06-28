package com.xiaoguo.smartcampus.controller;

import com.xiaoguo.smartcampus.pojo.Admin;
import com.xiaoguo.smartcampus.pojo.LoginForm;
import com.xiaoguo.smartcampus.pojo.Student;
import com.xiaoguo.smartcampus.pojo.Teacher;
import com.xiaoguo.smartcampus.service.AdminService;
import com.xiaoguo.smartcampus.service.StudentService;
import com.xiaoguo.smartcampus.service.TeacherService;
import com.xiaoguo.smartcampus.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/sms/system")
public class SystemController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private TeacherService teacherService;

    /**
     * 修改用户密码
     * @return
     */
    @PostMapping("updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@PathVariable("oldPwd") String oldPwd,
                            @PathVariable("newPwd") String newPwd,
                            @RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            //token过期
            return Result.fail().message("token失败，请重新登录后修改密码");
        }
        //获取用户id与用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        String oldPwd1 = MD5.encrypt(oldPwd);
        String newPwd1= MD5.encrypt(newPwd);
        switch (userType){
            case 1:
                Admin admin = adminService.updateUserPwd(userId,oldPwd1);
                if(admin!=null) {
                    admin.setPassword(newPwd1);
                    adminService.saveOrUpdate(admin);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
            case 2:
                Student student = studentService.updateUserPwd(userId,oldPwd1);
                if(student!=null) {
                    student.setPassword(newPwd1);
                    studentService.saveOrUpdate(student);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
            case 3:
                Teacher teacher = teacherService.updateUserPwd(userId,oldPwd1);
                if(teacher!=null) {
                    teacher.setPassword(newPwd1);
                    teacherService.saveOrUpdate(teacher);
                }else{
                    return Result.fail().message("原密码有误");
                }
                break;
        }
        return Result.ok();
    }

    /**
     * 头像上传
     * @param multipartFile
     * @return
     */
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(@RequestPart("multipartFile") MultipartFile multipartFile){
        String s = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = s + originalFilename.substring(i);
        //保存文件,临时方案
        String portraitPath="E:/大学文件/JavaWeb/ideajava/smartcampus/target/classes/public/upload/".concat(newFileName);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //响应图片路径
        String path="upload/"+newFileName;
        return Result.ok(path);
    }

    /**
     * 根据用户类型返回对应首页
     * @param token
     * @return
     */
    @GetMapping("/getInfo")
    public Result getInfoByToken(@RequestHeader("token") String token){
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        //从token中解析出用户Id，和用户类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);

        Map<String,Object> map = new LinkedHashMap<>();
        switch (userType){
            case 1:
                Admin admin = adminService.getAdminById(userId);
                map.put("userType",1);
                map.put("user",admin);
                break;
            case 2:
                Student student = studentService.getStudentById(userId);
                map.put("userType",2);
                map.put("user",student);
                break;
            case 3:
                Teacher teacher = teacherService.getTeacherById(userId);
                map.put("userType",3);
                map.put("user",teacher);
                break;
        }
        return Result.ok(map);
    }

    /**
     *验证登录信息
     * @param loginForm
     * @param request
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm,HttpServletRequest request){
        //验证码校验
        HttpSession session = request.getSession();
        String sessionVerifiCode = (String) session.getAttribute("verifiCode");
        String loginFormVerifiCode = loginForm.getVerifiCode();
        if("".equals(sessionVerifiCode)||null==sessionVerifiCode){
            return Result.fail().message("验证码失效请刷新重试");
        }
        if(!sessionVerifiCode.equalsIgnoreCase(loginFormVerifiCode)){
            return Result.fail().message("验证码有误，请小心输入后重试");
        }
            //从session域中移除验证码
        session.removeAttribute("verifiCode");
        //分用户类型，进行效验
            //准备一个Map用于响应
        Map<String,Object> map = new LinkedHashMap<>();
        switch (loginForm.getUserType()){
            case 1:
                try {
                    Admin admin = adminService.login(loginForm);
                    if(null!=admin){
                        //用户的类型和用户id转换成一个密文，以token的名称返回
                        map.put("token",JwtHelper.createToken(admin.getId().longValue(),1));
                    }else {
                        throw new RuntimeException("用户名密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 2:
                try {
                    Student student = studentService.login(loginForm);
                    if(null!=student){
                        //用户的类型和用户id转换成一个密文，以token的名称返回
                        map.put("token",JwtHelper.createToken(student.getId().longValue(),2));
                    }else {
                        throw new RuntimeException("用户名密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
            case 3:
                try {
                    Teacher teacher = teacherService.login(loginForm);
                    if(null!=teacher){
                        //用户的类型和用户id转换成一个密文，以token的名称返回
                        map.put("token",JwtHelper.createToken(teacher.getId().longValue(),3));
                    }else {
                        throw new RuntimeException("用户名密码有误");
                    }
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    return Result.fail().message(e.getMessage());
                }
        }

        // 查无此用户,响应失败
        return Result.fail().message("查无此用户");
    }


    /**
     * 响应验证码
     * @param request
     * @param response
     */
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){
        //获取图片
        BufferedImage verifiCodeImage = CreateVerifiCodeImage.getVerifiCodeImage();
        //获取图片的验证码
        String verifiCode = new String(CreateVerifiCodeImage.getVerifiCode());
        //将验证码放入session域，为下一次验证做准备
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode",verifiCode);
        //将验证码图片响应给浏览器
       try {
           ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
       } catch (IOException e) {
            e.printStackTrace();
       }

    }
}
