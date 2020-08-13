package com.jimmy.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.jimmy.crowd.entity.Role;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);
}
