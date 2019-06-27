package com.controller;

import com.pojo.Student;
import com.service.StudentService;
import com.status.Result;
import com.status.StatusCode;
import com.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    HttpServletRequest request;
    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public Result getAllStudent() {
        return new Result(true, StatusCode.OK, "查询所有学生信息", studentService.getAllStudent());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result getStudent() {

        Claims claims = (Claims) request.getAttribute("student_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String id = claims.getId();
        Optional<Student> optional = studentService.getStudentById(id);
        return optional.map(u -> new Result(true, StatusCode.OK, "查询成功", u)).orElse(
                new Result(false, StatusCode.ERROR, "查询失败")
        );
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String, String> map) {
        String id = map.get("id");
        String password = map.get("password");
        if (StringUtils.isBlank(id) || StringUtils.isBlank(password)) {
            return new Result(false, StatusCode.LOGINERROR, "用户名或密码为空");
        }
        Student student = studentService.findByIdAndPassword(id, password);
        if (student != null) {
            String token = jwtUtil.createJWT(id, student.getName(), "student");
            token = "Bearer " + token;
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("name", student.getName());
            return new Result(true, StatusCode.OK, "登录成功", resultMap);
        } else
            return new Result(false, StatusCode.LOGINERROR, "登录失败");
    }

    /**
     * 用来给未加密的密码加密
     *
     * @param password
     * @return
     */
    @RequestMapping("/encode/{password}")
    public String encodePassword(@PathVariable String password) {
        return studentService.encodePassword(password);
    }

    /**
     * 修改密码
     * @param map
     * @return
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public Result changePassword(@RequestBody Map<String, String> map) {
        Claims claims = (Claims) request.getAttribute("student_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String id = claims.getId();
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        if (StringUtils.isBlank(id) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)) {
            return new Result(false, StatusCode.ERROR, "新旧密码不一致");
        }
        Student student = studentService.changePassword(id, oldPassword, newPassword);
        if (student != null) {
            return new Result(true, StatusCode.OK, "修改成功");
        } else {
            return new Result(false, StatusCode.ERROR, "修改失败");
        }
    }

}
