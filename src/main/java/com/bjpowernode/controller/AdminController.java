package com.bjpowernode.controller;

import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.ProductType;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;
    @Autowired
    ProductTypeService productTypeService;

    @RequestMapping("/login.action")
    public String login(String name, String pwd, HttpServletRequest request){
        Admin admin = adminService.login(name, pwd);
        if (admin!=null){
           request.getSession().setAttribute("name",name);
            return "main";
        }else {
            request.setAttribute("errmsg","用户名或密码错误");
            return "login";
        }

    }


}
