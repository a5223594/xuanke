package com.controller;

import com.pojo.Menu;
import com.service.MenuService;
import com.status.Result;
import com.status.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("")
    public Result getMenus(){

        Claims admin_claims = (Claims) request.getAttribute("admin_claims");
        Claims student_claims = (Claims) request.getAttribute("student_claims");
        if (admin_claims != null) {
            String pid = "200";
            List<Menu> menus = menuService.getMenus(pid);
            return new Result(true, StatusCode.OK, "管理员菜单",menus);
        } else if (student_claims != null) {
            String pid = "100";
            List<Menu> menus = menuService.getMenus(pid);
            return new Result(true, StatusCode.OK, "学生菜单",menus);
        }else{
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }

    }
}
