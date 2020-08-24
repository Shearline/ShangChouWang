package com.jimmy.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.jimmy.crowd.entity.Role;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum,Integer pageSize,String keyword);

    void saveRole(Role role);

    void update(Role role);

    void removeRole(List<Integer>roleIdList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);
}
