package com.jimmy.crowd.service.impl;

import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.mapper.AdminMapper;
import com.jimmy.crowd.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
//        throw new RuntimeException();
    }
}
