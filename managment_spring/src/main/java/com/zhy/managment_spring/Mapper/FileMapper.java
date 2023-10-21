package com.zhy.managment_spring.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhy.managment_spring.Entity.Files;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhy
 * @date 2023/10/16 22:19
 */
@Mapper
public interface FileMapper extends BaseMapper<Files> {

}
