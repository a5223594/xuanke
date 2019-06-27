package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String login(){
        return "login";
    }
    @RequestMapping("/i")
    public String index(){
        return "index";
    }
    @RequestMapping("/a")
    public String admin(){
        return "admin";
    }
}
