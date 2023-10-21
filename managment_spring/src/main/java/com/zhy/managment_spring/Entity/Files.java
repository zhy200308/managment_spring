package com.zhy.managment_spring.Entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author zhy
 * @date 2023/10/16 22:03
 */
@Data
@TableName("file")
public class Files {
    @TableId(type = IdType.INPUT)
    private String id;
    private String md5;
    private String name;
    private String type;
    private Long size;
    private String url;
    private Boolean is_delete;
    private Boolean enable;
}
