package com.jimmy.crowd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jimmy.crowd.constant.CrowdConstant;
import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.entity.AdminExample;
import com.jimmy.crowd.exception.LoginAcctAlreadyInUseException;
import com.jimmy.crowd.exception.LoginAcctAlreadyInUseForUpdateException;
import com.jimmy.crowd.exception.LoginFailedException;
import com.jimmy.crowd.mapper.AdminMapper;
import com.jimmy.crowd.service.api.AdminService;
import com.jimmy.crowd.util.CrowdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public void saveAdmin(Admin admin) {
        String userPswd = admin.getUserPswd();
        userPswd = CrowdUtil.md5(userPswd);
        admin.setUserPswd(userPswd);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = simpleDateFormat.format(date);
        admin.setCreateTime(createTime);
        try {
            adminMapper.insert(admin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
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

    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 1.调用PageHelper的静态方法开启分页功能
        // 充分体现了PageHelper的非侵入式设计：原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum, pageSize);

        //2. 执行查询
        List<Admin> list = adminMapper.selectAdminByKeyWord(keyword);

        //3.封装到PageInfo对象中
        return new PageInfo<>(list);


    }

    @Override
    public void remove(Integer adminId) {
        adminMapper.deleteByPrimaryKey(adminId);
    }

    @Override
    public Admin getAdminById(Integer adminId) {
        return adminMapper.selectByPrimaryKey(adminId);
    }

    @Override
    public void update(Admin admin) {
        //selective表示有选择的更新，对于null值字段不更新
        try {
            adminMapper.updateByPrimaryKeySelective(admin);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info(e.getClass().getName());
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }
}

