package com.cloud.lsw.client.test;

import com.cloud.lsw.client.ClientSpringBootApplication;
import com.cloud.lsw.client.controller.ClientController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.awt.*;

/**
 * @author lisw
 * @create 2021/4/16 23:50
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ClientSpringBootApplication.class})
public class UploadTest {

    @Resource
    private ClientController clientController;

    @Test
    public void testSqlFileUpload(){
        /**测试文件上传用例 首先指定为sql文件*/
        System.out.println(clientController.uploadFiles());
        /**成功返回UUID c2f8ddfae44c4baf83f0aa5e7ec4e053*/
    }

    @Test
    public void testMDFileUpload(){
        /**测试文件上传用例 指定为md文件*/
        System.out.println(clientController.uploadFiles());
        /**成功返回UUID为  bd0366fda7164a12b5a84ae52f225249*/
    }

    @Test
    public void testPDFFileUpload(){
        /**测试文件上传用例 指定为pdf文件*/
        System.out.println(clientController.uploadFiles());
        /**成功返回UUID为  8c9dc9c5ba5d433cb15ea2027bcb5bce*/
    }

    @Test
    public void testDOCFileUpload(){
        /**测试文件上传用例 指定为doc文件*/
        System.out.println(clientController.uploadFiles());
        /**成功返回UUID为  33887ad552a54957a8d5a22055a4db00*/
    }

}
