package com.jimmy.crowd.test;

import com.jimmy.crowd.entity.Admin;
import com.jimmy.crowd.mapper.AdminMapper;
import com.jimmy.crowd.service.api.AdminService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//在类上标记必要的注解，Spring整合junit
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class CrowdTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testLog() {
        Logger logger = LoggerFactory.getLogger(CrowdTest.class);
        logger.debug("Hello, I am Debug level!");
        logger.info("Hello, I am Info level!");
        logger.warn("Hello, I am Warn level!");
        logger.error("Hello, I am Error level!");

    }

    @Test
    public void testInsertAdmin() {
        Admin admin = new Admin(null, "tom", "123123", "汤姆", "tom@qq.com", null);
        int count = adminMapper.insert(admin);
        /**如果在实际开发中，所有想查看数值的地方都使用sout方式进行打印，会给项目上线运行带来问题。
         * sout本质上是一个IO操作，通常IO的操作是比较消耗性能的。如果项目中sout很多，那么对性能的影响就比较大了
         */

        System.out.println(count);
    }

    @Test
    public void testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "jerry", "123456", "杰瑞", "jerry@qq.com", null);
        adminService.saveAdmin(admin);
    }

    @Test
    public void test() {
        for (int i = 0; i < 238; i++) {
            adminMapper.insert(new Admin(null, "loginAcct" + i, "userPswd" + i, "userName" + i, "email" + i, null));
        }
    }
}
