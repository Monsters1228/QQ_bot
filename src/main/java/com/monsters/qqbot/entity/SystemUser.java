package com.monsters.qqbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Monsters
 * @Data 2021/8/23.
 * 打卡用户
 */

@Data
@TableName(value = "system_user")
public class SystemUser {

    @TableId(type = IdType.AUTO)
    Integer id;

    // qq号
    String qqCode;

    // 打卡系统 uuid
    String uuid;

    // 是否自动打卡
    Integer autoClockIn;
}
