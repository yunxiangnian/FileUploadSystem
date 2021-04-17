package com.cloud.lsw.server.service;

import com.cloud.lsw.server.entity.FileEntity;

import java.util.HashMap;

/**
 * @author lisw
 * @create 2021/4/16 18:50
 */
public interface ServerService {
    Integer insertIntoFileInfo(FileEntity fileEntity);

    HashMap<String, Object> getFilePathByUUID(String uuid);

    FileEntity getMetadataByUUID(String uuid);
}
