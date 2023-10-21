package com.zhy.managment_spring.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhy.managment_spring.Entity.Files;
import com.zhy.managment_spring.Entity.User;
import com.zhy.managment_spring.Mapper.FileMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zhy
 * @date 2023/10/16 22:22
 */
@Service
public class FileService extends ServiceImpl<FileMapper, Files> {
    public boolean saveFiles(Files files){

        if (files.getId() == null || files.getId().isEmpty()) {
            // 如果ID为null或为空，则生成新的UUID
            files.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
            return save(files);
        }else {
            return updateById(files);
        }
    }

}
