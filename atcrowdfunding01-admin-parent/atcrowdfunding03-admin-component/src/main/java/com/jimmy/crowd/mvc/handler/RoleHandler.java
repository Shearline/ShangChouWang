package com.jimmy.crowd.mvc.handler;

import com.github.pagehelper.PageInfo;
import com.jimmy.crowd.entity.Role;
import com.jimmy.crowd.service.api.RoleService;
import com.jimmy.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleHandler {
    @Autowired
    private RoleService roleService;

    @RequestMapping("role/remove/by/role/id/array.json")
    public ResultEntity<String> removeByRoleIdArray(@RequestBody List<Integer> roleIdList) {
        roleService.removeRole(roleIdList);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("role/update.json")
    public ResultEntity<String> updateRole(Role role) {
        roleService.update(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("role/save.json")
    public ResultEntity<String> saveRole(Role role) {
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/get/page/info.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        //调用service方法获取分页数据
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);

        //封装到ResultEntity对象中返回（如果抛出异常，交给异常处理机制处理）
        return ResultEntity.successWithData(pageInfo);
    }
}
