package com.controller;

import com.pojo.Admin;
import com.pojo.Student;
import com.service.AdminService;
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
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    JwtUtil jwtUtil;

    @RequestMapping(value = "",method = RequestMethod.GET)
    public Result getAdminById() {
        Claims claims = (Claims) request.getAttribute("admin_claims");

        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String id = claims.getId();
        Optional<Admin> optional = adminService.getAdminById(id);
        return optional.map(u->new Result(true, StatusCode.OK,"个人信息",u)).orElse(
          new Result(false,StatusCode.ERROR,"查询失败")
        );
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody Map<String,String> map){
        String id = map.get("id");
        String password = map.get("password");
        if(StringUtils.isBlank(id)||StringUtils.isBlank(password)){
            return new Result(false,StatusCode.LOGINERROR,"用户名或密码为空");
        }
        Admin admin = adminService.findByIdAndPassword(id, password);
        if (admin != null) {
            String token = jwtUtil.createJWT(id, admin.getName(), "admin");
            token = "Bearer "+token;
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("token", token);
            resultMap.put("name", admin.getName());
            return new Result(true,StatusCode.OK,"登录成功",resultMap);
        }else
            return new Result(false,StatusCode.LOGINERROR,"登录失败");
    }

    @RequestMapping(value = "/changePassword",method = RequestMethod.POST)
    public Result changePassword(@RequestBody Map<String,String> map){
        Claims claims = (Claims) request.getAttribute("admin_claims");
        if (claims == null) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        String id = claims.getId();
        String oldPassword = map.get("oldPassword");
        String newPassword = map.get("newPassword");
        if(StringUtils.isBlank(id)||StringUtils.isBlank(oldPassword)||StringUtils.isBlank(newPassword)){
            return new Result(false,StatusCode.ERROR,"新旧密码不一致");
        }
        Admin admin = adminService.changePassword(id, oldPassword, newPassword);
        if (admin != null) {
            return new Result(true,StatusCode.OK,"修改成功");
        }else{
            return new Result(false,StatusCode.ERROR,"修改失败");
        }
    }
}
