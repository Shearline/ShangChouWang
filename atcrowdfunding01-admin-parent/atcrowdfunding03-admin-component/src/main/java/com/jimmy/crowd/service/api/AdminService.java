package com.jimmy.crowd.service.api;

import com.jimmy.crowd.entity.Admin;

import java.util.List;


public interface AdminService {
    public void saveAdmin(Admin admin);

    public List<Admin> getAll();

    public Admin geAdminByLoginAccount(String loginAccount, String userPswd);
}
