package com.jimmy.crowd.service.api;

import com.github.pagehelper.PageInfo;
import com.jimmy.crowd.entity.Admin;

import java.util.List;


public interface AdminService {
    public void saveAdmin(Admin admin);

    public List<Admin> getAll();

    public Admin geAdminByLoginAccount(String loginAccount, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer adminId);

    Admin getAdminById(Integer adminId);

    void update(Admin admin);

    void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList);
}
