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

    // 籍贯（省）
    String provinceJg;

    // 籍贯 （市）
    String cityJg;

    // 家乡所在地（省）
    String provinceHome;

    // 家乡所在地（城市）
    String cityHome;

    // 家乡所在地（详细地址）
    String addressHome;

    // 当前所在地（省）
    String province;

    // 当前所在地（城市）
    String city;

    // 当前所在地（详细地址）
    String address;

    @TableField(exist = false)
    String touchZhongGaoFlag = "没有";

    @TableField(exist = false)
    String huBeiManFlag2 = "否";

    @TableField(exist = false)
    String sheQuTouchFlag = "否";

    @TableField(exist = false)
    String jinWaiTouchFlag = "否";

    @TableField(exist = false)
    String huCheckFlag = "是";

    @TableField(exist = false)
    String health = "正常";

    @TableField(exist = false)
    String memberHealth = "正常";

    @TableField(exist = false)
    String suikangCode = "绿码";

    @TableField(exist = false)
    String yimiao = "已接种两剂";

    @TableField(exist = false)
    String infxarea = "否";
}
