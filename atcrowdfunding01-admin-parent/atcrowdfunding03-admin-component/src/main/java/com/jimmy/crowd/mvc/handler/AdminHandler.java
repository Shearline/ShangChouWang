package com.jimmy.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.jimmy.crowd.constant.CrowdConstant;
import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    AdminService adminService;

    @RequestMapping("/admin/update.html")
    public String update(Admin admin, @RequestParam("pageNum") Integer pageNum, @RequestParam("keyword") String keyword) {
        adminService.update(admin);
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;

    }

    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            ModelMap modelMap
    ) {
        //1.根据adminId查询admin对象
        Admin admin = adminService.getAdminById(adminId);
        //2.将admin对象存入模型
        modelMap.addAttribute("admin", admin);
        return "admin-edit";


    }

    @RequestMapping("/admin/save.html")
    public String save(Admin admin) {
        adminService.saveAdmin(admin);
        return "redirect:/admin/get/page.html?pageNum=" + Integer.MAX_VALUE;
    }


    @RequestMapping("/admin/remove/{adminId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("adminId") Integer adminId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword
    ) {
        //执行删除
        adminService.remove(adminId);

        //页面跳转:回到分页页面

        /*
        尝试方案1：直接转发到admin-page，会无法显示分页数据
        尝试方案2：转发到/admin/get/page.html，一旦刷新页面会重复删除，影响性能
        尝试方案3：重定向到/admin/get/page.html，同时为了保持原本所在的页面和查询关键词，再附加pageNum和keyWord两个请求参数
         */
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }


    @RequestMapping("/admin/get/page.html")
    public String getPageInfo(
//            使用defaultValue属性，指定默认值，在请求中没有携带对应参数时，使用默认值
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap
    ) {
        //调用service方法，获取pageInfo对象
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);
        //2.将PageInfo对象存入模型
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    @RequestMapping(value = "/admin/do/login.html")
    public String doLogin(@RequestParam("loginAcct") String loginAcct,
                          @RequestParam("userPswd") String userPswd,
                          HttpSession session) {
        //这个方法如果能够返回admin对象，说明登录成功，如果账号密码不正确，则会抛出异常
        Admin admin = adminService.geAdminByLoginAccount(loginAcct, userPswd);

        //将登录成功返回的admin对象存入session域
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/admin/do/logout.html")
    public String doLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }
}
