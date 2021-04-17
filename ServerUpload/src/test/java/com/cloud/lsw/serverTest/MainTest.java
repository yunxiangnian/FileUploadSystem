package com.cloud.lsw.serverTest;

import com.cloud.lsw.server.ServerUploadApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author lisw
 * @create 2021/4/16 19:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ServerUploadApplication.class})
public class MainTest {

    @Resource
    DataSource dataSource;


    /**
     * 测试数据库连接是否成功
     * @throws SQLException
     */
    @Test
    public void testDatasource() throws SQLException {
        System.out.println(dataSource.getClass());
    }

}
