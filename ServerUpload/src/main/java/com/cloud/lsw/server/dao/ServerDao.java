package com.cloud.lsw.server.dao;

import com.cloud.lsw.server.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;

/**
 * @author lisw
 * @create 2021/4/16 18:51
 */
@Mapper
public interface ServerDao {


    /**
     * 插入用户上传的数据
     * @param fileEntity
     * @return
     */
    Integer insertIntoFileInfo(FileEntity fileEntity);


    HashMap<String,Object> getFilePathByUUID(@Param("uuid") String uuid);

    FileEntity getMetadataByUUID(@Param("uuid") String uuid);
}
