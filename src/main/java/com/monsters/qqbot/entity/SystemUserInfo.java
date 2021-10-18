package com.monsters.qqbot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Monsters
 * @Data 2021/8/23.
 * 打卡用户详情数据
 */

@Data
@TableName(value = "system_user_info")
public class SystemUserInfo {

    @TableId(type = IdType.AUTO)
    Integer id;

    // qq号
    String qqCode;

    // 手机号
    String mobile;

    // 紧急联系人姓名
    String linkman;

    // 紧急联系人电话
    String linkmanmobile;

    // 家乡所在地（省）
    String provinceHome;

    // 家乡所在地（城市）
    String cityHome;

    // 家乡所在地（详细地址）
    String addressHome;

    // 常住地址（省）
    String province2;

    // 常住地址（市）
    String city2;

    // 常住地址（详细地址）
    String address2;

    // 当前所在地（省）
    String province;

    // 当前所在地（城市）
    String city;

    // 当前所在地（详细地址）
    String address;


    @TableField(exist = false)
    String health = "正常";

    @TableField(exist = false)
    String memberHealth = "正常";

    @TableField(exist = false)
    String suikangCode = "绿码";

    @TableField(exist = false)
    String yimiao = "已完成全程接种";

    @TableField(exist = false)
    String todaytrave = "只在中山市";

    @TableField(exist = false)
    String zuoritrave = "只在中山市";

    @TableField(exist = false)
    String touchZhongGaoFlag = "没有";

    @TableField(exist = false)
    String infxarea = "否";
}
