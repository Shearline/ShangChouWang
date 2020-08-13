package com.jimmy.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jimmy.crowd.entity.Role;
import com.jimmy.crowd.mapper.RoleMapper;
import com.jimmy.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        //1. 开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        //2.执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        //3.封装为pageInfo对象返回
        return new PageInfo<>(roleList);
    }

    @Override
    public void saveRole(Role role) {
        roleMapper.insert(role);
    }
}
