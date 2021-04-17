package com.cloud.lsw.server.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author lisw
 * @create 2021/4/16 18:52
 */
@Data
public class FileEntity {

    /**
     * 文件的uuid
     */
    private String UUID;

    /**
     * 旧文件名
     */
    private String oldFileName;

    /**
     * 新文件名
     */
    private String newFileName;

    /**
     * 文件名后缀
     */
    private String ext;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 文件大小
     */
    private String size;

    /**
     * 文件类型
     */
    private String type;

    /**
     * 上传时间
     */
    private Date uploadTime;
}
