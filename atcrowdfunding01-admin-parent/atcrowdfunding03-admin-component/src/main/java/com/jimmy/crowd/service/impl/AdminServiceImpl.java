package com.jimmy.crowd.service.impl;

import com.jimmy.crowd.constant.CrowdConstant;
import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.entity.AdminExample;
import com.jimmy.crowd.exception.LoginFailedException;
import com.jimmy.crowd.mapper.AdminMapper;
import com.jimmy.crowd.service.api.AdminService;
import com.jimmy.crowd.util.CrowdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdmin(Admin admin) {
        adminMapper.insert(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin geAdminByLoginAccount(String loginAccount, String userPswd) {
        // 1.根据登录账号查询 Admin 对象
        // ①创建 AdminExample 对象
        AdminExample adminExample = new AdminExample();
        // ②创建 Criteria 对象
        AdminExample.Criteria criteria = adminExample.createCriteria();
        // ③在 Criteria 对象中封装查询条件
        criteria.andLoginAcctEqualTo(loginAccount);
        // ④调用 AdminMapper 的方法执行查询
        List<Admin> list = adminMapper.selectByExample(adminExample);
        // 2.判断 Admin 对象是否为 null
        if (list == null || list.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if (list.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
        }
        Admin admin = list.get(0);
        // 3.如果 Admin 对象为 null 则抛出异常
        if (admin == null) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 4.如果 Admin 对象不为 null 则将数据库密码从 Admin 对象中取出
        String userPswdDB = admin.getUserPswd();
        // 5.将表单提交的明文密码进行加密
        String userPswdForm = CrowdUtil.md5(userPswd);
        // 6.对密码进行比较
        if (!Objects.equals(userPswdDB, userPswdForm)) {
            // 7.如果比较结果是不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        // 8.如果一致则返回 Admin 对象
        return admin;
    }
}

