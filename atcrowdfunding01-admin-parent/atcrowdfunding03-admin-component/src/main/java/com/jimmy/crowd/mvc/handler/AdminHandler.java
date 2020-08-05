package com.jimmy.crowd.mvc.handler;

import com.jimmy.crowd.constant.CrowdConstant;
import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session) {
        //这个方法如果能够返回admin对象，说明登录成功，如果账号密码不正确，则会抛出异常
        Admin admin = adminService.geAdminByLoginAccount(loginAcct, userPswd);

        //将登录成功返回的admin对象存入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);
        return "admin-main";
    }
}
