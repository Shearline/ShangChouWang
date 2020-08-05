package com.jimmy.crowd.mvc.handler;

import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.entity.Student;
import com.jimmy.crowd.service.api.AdminService;
import com.jimmy.crowd.util.CrowdUtil;
import com.jimmy.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.List;

@Controller
public class TestHandler {

    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap, HttpServletRequest request) {
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult=" + judgeResult);
        List<Admin> adminList = adminService.getAll();
        modelMap.addAttribute("adminList", adminList);
        int i = 10 / 0;
        return "target";
    }

    @RequestMapping("/send/array/one.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        for (Integer integer : array) {
            System.out.println(integer);
        }
        return "target";
    }

    @RequestMapping("/send/array/two.html")
    public String testReceiveArrayTwo(@RequestParam("array") List<Integer> array) {
        for (Integer integer : array) {
            System.out.println(integer);
        }
        return "target";
    }

    @ResponseBody
    @RequestMapping("send/array/three.html")
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {

        for (Integer integer : array) {
            logger.debug(String.valueOf(integer));
        }
        return "success";
    }

    @ResponseBody
    @RequestMapping("/send/compose/object.json")
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student, HttpServletRequest request) {
        boolean judgeResult = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult=" + judgeResult);
        logger.info(student.toString());
        //将查询到的student的对象封装到resultEntity中返回
        return ResultEntity.successWithData(student);
    }
}
