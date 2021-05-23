package com.monsters.qqbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Monsters
 * @Data 2021-41-21
 */
@Data
@TableName(value = "user")
public class User {

    @TableId(type = IdType.AUTO)
    Integer id;

    @TableField("qq")
    String qq;

    Integer count;

}
