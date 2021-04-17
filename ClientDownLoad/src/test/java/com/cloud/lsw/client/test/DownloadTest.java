package com.cloud.lsw.client.test;

import com.cloud.lsw.client.ClientSpringBootApplication;
import com.cloud.lsw.client.controller.ClientController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author lisw
 * @create 2021/4/17 11:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClientSpringBootApplication.class})
public class DownloadTest {

    @Resource
    private ClientController clientController;

    @Test
    public void testDownloadFile(){
        /***/
        System.out.println(clientController.downloadFile());
    }
}
