package com.monsters.qqbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Monsters
 * @Data 2021-51-21
 */
@Data
@TableName(value = "images")
public class Image {
    @TableId(type = IdType.AUTO)
    Integer id;

    String url;

    Integer isView;
}
