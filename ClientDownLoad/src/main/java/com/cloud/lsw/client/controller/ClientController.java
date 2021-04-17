package com.cloud.lsw.client.controller;

import cn.hutool.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @author lisw
 * @create 2021/4/16 21:06
 */
@RestController
@RequestMapping("client")
public class ClientController {

    @GetMapping("test")
    public String testMapping(){
        return "hello world";
    }

    @GetMapping("/upload")
    public String uploadFiles() {
        String result = "";
        try {
            String url = "http://127.0.0.1:8000/server/uploadFile";
            /**上传的文件路径*/
            String fileName = "C:\\Users\\11692\\Desktop\\个人信息\\李松伟简历.docx";
            /**设置文件的一些细节*/
            HashMap<String, Object> fileMap = new HashMap<>(16);
            fileMap.put("upFile", fileName);
            result = formUpload(url, fileMap);

        } catch (Exception e) {
            throw new RuntimeException("上传文件失败：", e);
        } finally {//处理结束后关闭httpclient的链接
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    /**
     * 上传文件
     *
     * @param urlStr
     * @param fileMap
     * @return response的返回数据
     */
    private String formUpload(String urlStr, HashMap<String, Object> fileMap) throws ProtocolException {
        String result = "";
        HttpURLConnection conn = null;
        // request头和上传文件内容的分隔符
        String LINE = "---------------------------123821742118716";
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            /**设置连接超时时间*/
            conn.setConnectTimeout(5000);
            /**设置读取超时时间*/
            conn.setReadTimeout(30000);
            /**设置使用输出*/
            conn.setDoOutput(true);
            /**设置使用输入*/
            conn.setDoInput(true);
            /**不适用缓存*/
            conn.setUseCaches(false);
            /**设置请求方法为post*/
            conn.setRequestMethod("POST");
            /**设置请求属性*/
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type","multipart/form-data; boundary=" + LINE);
            /**获取输出流*/
            OutputStream out = new DataOutputStream(conn.getOutputStream());
            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();

                    /**获取文件的输入类型*/
                    String contentType = new MimetypesFileTypeMap().getContentType(file);
                    StringBuffer strBuf = new StringBuffer();
                    /**设置请求头类型*/
                    strBuf.append("\r\n").append("--").append(LINE).append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"; filename=\"" + filename + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
                    out.write(strBuf.toString().getBytes());
                    DataInputStream in = new DataInputStream(new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
                byte[] endData = ("\r\n--" + LINE + "--\r\n").getBytes();
                out.write(endData);
                out.flush();
                out.close();
                // 读取返回数据
                StringBuffer strBuf = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    strBuf.append(line).append("\n");
                }
                result = strBuf.toString();
                reader.close();
                reader = null;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出错。" + urlStr);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return result;
    }

    @GetMapping("/downloadFile")
    public String downloadFile() {
        HttpURLConnection connection = null;
        InputStream stream = null;
        BufferedReader reader = null;
        String uuid = "c2f8ddfae44c4baf83f0aa5e7ec4e053";
        String server = "http://localhost:8000/server/downloadFile?uuid=" + uuid;
        try {
            /**实例化URL对象*/
            URL url = new URL(server);
            /**通过URL对象打开一个连接，强制转换为httpURLConnection类*/
            connection = (HttpURLConnection) url.openConnection();
            /**设置连接方式*/
            connection.setRequestMethod("GET");
            /**设置连接远程服务的超时时间：15000毫秒*/
            connection.setConnectTimeout(15000);
            /**设置读取远程服务返回的数据时间：60000毫秒*/
            connection.setReadTimeout(60000);
            /**建立连接，发送请求*/
            connection.connect();
            /**通过connect连接，获取输入流*/
            stream = connection.getInputStream();
            /**实例化reader*/
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                stringBuffer.append(temp);
                stringBuffer.append("\r\n");
            }
            // 返回内容输出到控制台
            System.out.println("远程服务返回的数据：" + stringBuffer.toString());
            return stringBuffer.toString();
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        } finally {
            // 关闭资源
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @GetMapping("/getMetadata")
    public JSONObject getMetadata() throws IOException {
        HttpURLConnection connection = null;
        InputStream stream = null;
        BufferedReader reader = null;
        String uuid = "c2f8ddfae44c4baf83f0aa5e7ec4e053";
        String server = "http://localhost:8000/server/getMetadata?uuid=" + uuid;
        try {
            /**实例化URL对象*/
            URL url = new URL(server);
            /**通过URL对象打开一个连接，强制转换为httpURLConnection类*/
            connection = (HttpURLConnection) url.openConnection();
            /**设置连接方式*/
            connection.setRequestMethod("GET");
            /**设置连接远程服务的超时时间：15000毫秒*/
            connection.setConnectTimeout(15000);
            /**设置读取远程服务返回的数据时间：60000毫秒*/
            connection.setReadTimeout(60000);
            /**建立连接，发送请求*/
            connection.connect();
            /**通过connect连接，获取输入流*/
            stream = connection.getInputStream();
            /**实例化reader*/
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            while ((temp = reader.readLine()) != null) {
                stringBuffer.append(temp);
                stringBuffer.append("\r\n");
            }
            // 返回内容输出到控制台
            System.out.println("远程服务返回的数据：" + stringBuffer.toString());
            return new JSONObject(stringBuffer.toString());
        } catch (Exception e) {
            throw new RuntimeException("查询异常", e);
        } finally {
            // 关闭资源
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != stream) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
