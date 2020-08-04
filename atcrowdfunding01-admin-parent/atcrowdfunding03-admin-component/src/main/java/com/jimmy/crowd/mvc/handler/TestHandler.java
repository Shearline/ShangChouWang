package com.jimmy.crowd.mvc.handler;

import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap) {
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        return "target";
    }

    @RequestMapping("/send/array.html")
    public String testReceiveArrayOne(@RequestParam("array") List<Integer> array){
        for (Integer integer : array) {
            System.out.println(integer);
        }
        return "target";
    }
}
