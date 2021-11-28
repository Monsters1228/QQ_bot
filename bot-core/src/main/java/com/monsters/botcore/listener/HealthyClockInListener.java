package com.monsters.botcore.listener;

import catcode.CatCodeUtil;
import catcode.CodeTemplate;
import com.monsters.botcore.entity.ClockInResult;
import com.monsters.botcore.entity.SystemUser;
import com.monsters.botcore.entity.SystemUserInfo;
import com.monsters.botcore.service.SystemUserInfoService;
import com.monsters.botcore.service.SystemUserService;
import com.monsters.botcore.util.HttpUtil;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Monsters
 * @Data 2021/8/23.
 * <p>
 * 健康打卡监听器
 */

@Component
public class HealthyClockInListener {
    final Logger logger = LoggerFactory.getLogger(getClass());

    // 工具类
    final CatCodeUtil util = CatCodeUtil.INSTANCE;

    // 获取模版
    CodeTemplate<String> template = util.getStringTemplate();

    @Autowired
    private SystemUserService userService;

    @Autowired
    private SystemUserInfoService systemUserInfoService;


    /**
     * 获取菜单
     *
     * @param groupMsg
     * @param msgSender
     * @return
     */
    @OnGroup
    @Filter(value = "/健康打卡", matchType = MatchType.STARTS_WITH)
    public Object sendClockInStep(GroupMsg groupMsg, MsgSender msgSender) {
        String imageUrl = util.toCat("image", true, "url=https://files.catbox.moe/1r0a0n.jpg");
        return msgSender.SENDER.sendGroupMsg(groupMsg,
                "健康打卡步骤:" + "\n" +
                        "1. 通过指令 /绑定 + uaid" + "来绑定打卡系统uaid,获取uaid见下图" + "\n" +
                        imageUrl + "\n" +
                        "2. 在以下网页填写个人信息 (重复填写则覆盖上一次记录)" + "\n" +
                        "http://121.5.110.15:8788/#/"
        );
    }

    /**
     * 手动打卡
     *
     * @param groupMsg
     * @param sender
     * @return
     */
    @OnGroup
    @Filter(value = "/打卡", matchType = MatchType.STARTS_WITH)
    public Object ClockIn(GroupMsg groupMsg, MsgSender sender) {
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String qqCode = groupMsg.getAccountInfo().getAccountCode();
        SystemUser user = userService.queryByQqCode(qqCode);
        if (user == null) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "请先绑定uaid");
        }
        SystemUserInfo userInfo = systemUserInfoService.queryByQqCode(qqCode);
        if (userInfo == null) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "请在以下网页填写个人信息" + "\n" + "http://121.5.110.15:8788/#/");
        }
        ClockInResult result = HttpUtil.sendPost(userInfo, user.getUuid());
        logger.info(result.toString());
        if (result.getErrorcode() == 0) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "打卡成功");
        } else if (result.getErrorcode() == 1000) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "打卡失败，失败原因" + result.getMessage());
        }
        return sender.SENDER.sendGroupMsg(groupMsg, at + "打开失败，请联系管理员");
    }

    /**
     * 绑定 uuid
     *
     * @param groupMsg
     * @param sender
     * @param uuid
     * @return
     */
    @OnGroup
    @Filter(value = "/绑定 {{uuid,.+}}", matchType = MatchType.REGEX_MATCHES)
    public Object bindUUid(GroupMsg groupMsg, MsgSender sender, @FilterValue("uuid") String uuid) {
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String qqCode = groupMsg.getAccountInfo().getAccountCode();
        SystemUser user = new SystemUser();
        user.setQqCode(qqCode);
        user.setUuid(uuid);
        int res = userService.insertItem(user);
        if (res == 1) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "绑定成功");
        }
        return sender.SENDER.sendGroupMsg(groupMsg, at + "绑定失败");
    }

    /**
     * 发送指令菜单
     * @param groupMsg
     * @param sender
     * @return
     */
    @OnGroup
    @Filter(value = "/菜单", matchType = MatchType.STARTS_WITH)
    public Object sendMenu(GroupMsg groupMsg, MsgSender sender) {
        return sender.SENDER.sendGroupMsg(groupMsg,
                "1./健康打卡 —— 查看健康打卡步骤" + "\n" +
                        "2./绑定 + uaid —— 绑定 uaid" + "\n" +
                        "3./打卡 —— 手动打卡" + "" + "\n" +
                        "4./申请出校 —— 直接扫码出校" + "\n" +
                        "5./自动打卡 —— 设置每天8点自动打卡" + "\n" +
                        "6./取消自动打卡 —— 取消每天自动打卡"
        );
    }

    /**
     * 设置自动打卡
     * @param groupMsg
     * @param sender
     * @return
     */
    @OnGroup
    @Filter(value = "/自动打卡", matchType = MatchType.STARTS_WITH)
    public Object autoClock(GroupMsg groupMsg, MsgSender sender){
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String qqCode = groupMsg.getAccountInfo().getAccountCode();
        SystemUser user = userService.queryByQqCode(qqCode);
        if (user == null) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "请先绑定uaid");
        }
        SystemUserInfo userInfo = systemUserInfoService.queryByQqCode(qqCode);
        if (userInfo == null) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "请在以下网页填写个人信息" + "\n" + "http://121.5.110.15:8788/#/");
        }
        user.setAutoClockIn(1);
        userService.updateItem(user);
        return sender.SENDER.sendGroupMsg(groupMsg,at + "设置自动打卡成功");
    }

    /**
     * 设置自动打卡
     * @param groupMsg
     * @param sender
     * @return
     */
    @OnGroup
    @Filter(value = "/取消自动打卡", matchType = MatchType.STARTS_WITH)
    public Object cancelAutoClock(GroupMsg groupMsg, MsgSender sender){
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String qqCode = groupMsg.getAccountInfo().getAccountCode();
        SystemUser user = userService.queryByQqCode(qqCode);
        user.setAutoClockIn(0);
        userService.updateItem(user);
        return sender.SENDER.sendGroupMsg(groupMsg,at + "取消自动打卡成功");
    }

    /**
     * 自动申请出校
     * @param groupMsg
     * @param sender
     * @return
     */
    @OnGroup
    @Filter(value = "/申请出校", matchType = MatchType.STARTS_WITH)
    public Object autoRequire(GroupMsg groupMsg, MsgSender sender){
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String qqCode = groupMsg.getAccountInfo().getAccountCode();
        return sender.SENDER.sendGroupMsg(groupMsg,at + "该功能暂未开放");
    }

    /**
     * 每天八点自动执行打卡
     */
    @Scheduled(cron = "0 0 8 * * *")
    public void autoClockIn(){
        List<SystemUser> userList = userService.queryAll();
        for(SystemUser user : userList){
            if(user.getAutoClockIn() == 1){
                SystemUserInfo userInfo = systemUserInfoService.queryByQqCode(user.getQqCode());
                ClockInResult result = HttpUtil.sendPost(userInfo, user.getUuid());
                if(result.getErrorcode() == 0){
                    logger.info(user.getQqCode() + "自动打卡成功");
                }
            }
        }
    }
}
