package com.jimmy.crowd.service.api;

import com.jimmy.crowd.entity.Admin;

import java.util.List;


public interface AdminService {
    public void saveAdmin(Admin admin);

    List<Admin> getAll();
}
