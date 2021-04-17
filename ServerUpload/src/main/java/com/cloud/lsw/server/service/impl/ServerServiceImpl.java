package com.cloud.lsw.server.service.impl;

import com.cloud.lsw.server.dao.ServerDao;
import com.cloud.lsw.server.entity.FileEntity;
import com.cloud.lsw.server.service.ServerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author lisw
 * @create 2021/4/16 18:51
 */
@Service("serverService")
public class ServerServiceImpl implements ServerService {

    @Resource
    private ServerDao serverDao;

    @Override
    public Integer insertIntoFileInfo(FileEntity fileEntity) {
        return serverDao.insertIntoFileInfo(fileEntity);
    }

    @Override
    public HashMap<String, Object> getFilePathByUUID(String uuid) {
        return serverDao.getFilePathByUUID(uuid);
    }

    @Override
    public FileEntity getMetadataByUUID(String uuid) {
        return serverDao.getMetadataByUUID(uuid);
    }
}
