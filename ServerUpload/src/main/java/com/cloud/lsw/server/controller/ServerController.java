package com.cloud.lsw.server.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.cloud.lsw.server.entity.FileEntity;
import com.cloud.lsw.server.service.ServerService;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lisw
 * @create 2021/4/16 18:41
 */
@RestController()
@RequestMapping("server")
public class ServerController {

    @Resource
    private ServerService serverService;

    @PostMapping("uploadFile")
    public String uploadFile(@RequestParam(value = "upFile") MultipartFile file) throws IOException {
        String UUID = IdUtil.simpleUUID();
        //获取文件的原始名称
        String oldFileName=file.getOriginalFilename();
        //获取文件后缀
        String ext = "."+ FileUtil.extName(file.getOriginalFilename());
        //生成新的文件名
        String newFileName=new SimpleDateFormat("yyyyMMdd").format(new Date())+ IdUtil.simpleUUID() + ext;
        //文件的大小
        Long size = file.getSize();
        //文件类型
        String type = file.getContentType();
        //根据日期生成目录
        String realPath = ResourceUtils.getURL("classpath:").getPath() + "/static/stu/files";
        String dataFormat = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String dateDirPath= realPath+"/"+ dataFormat;
        File dateDir=new File(dateDirPath);
        if (!dateDir.exists())
            dateDir.mkdirs();
        /**处理文件的上传*/
        file.transferTo(new File(dateDir,newFileName));

        /**插入到数据库*/
        FileEntity fileEntity = new FileEntity();
        fileEntity.setUUID(UUID);
        fileEntity.setOldFileName(oldFileName);
        fileEntity.setNewFileName(newFileName);
        fileEntity.setPath(dateDirPath);
        fileEntity.setSize(String.valueOf(size));
        fileEntity.setType(type);
        fileEntity.setExt(ext);
        fileEntity.setUploadTime(new Date());

        try {
            /**执行保存操作*/
            serverService.insertIntoFileInfo(fileEntity);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("出错了");
        }
        return UUID;
    }

    @GetMapping("downloadFile")
    public void downloadFile(@RequestParam("uuid") String uuid,
                             HttpServletResponse response) throws IOException {
        /**设置响应流的格式，防止中文乱码*/
        response.setHeader("Content-Type","text/html:charset=UTF-8");
        response.setContentType("application/octet-stream");
        /**获取输出流*/
        ServletOutputStream outputStream = response.getOutputStream();
        try {
            /**获取文件的路径*/
            HashMap<String,Object> maps = serverService.getFilePathByUUID(uuid);
            StringBuffer path = new StringBuffer();
            path.append(maps.get("path")).append("\\").append(maps.get("newFileName"));
            /**读取文件*/
            BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(path.toString()));
            OutputStream outStream = new BufferedOutputStream(outputStream);
            //创建存放文件内容的数组
            byte[] buff =new byte[1024];
            //所读取的内容使用n来接收
            int len = -1;
            //当没有读取完时,继续读取,循环
            while(( len = inputStream.read(buff) ) != -1){
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff,0,len);
            }
            /**将数据从缓存中刷出*/
            outputStream.flush();
            outStream.flush();
            /**关闭输出流和输入流*/
            outputStream.close();
            outStream.close();
            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
            outputStream.write(401);
        }
    }

    @GetMapping("getMetadata")
    public JSONObject getMetadata(@RequestParam("uuid") String uuid){
        FileEntity fileEntity = serverService.getMetadataByUUID(uuid);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("file", fileEntity);
        return jsonObject;
    }
}
