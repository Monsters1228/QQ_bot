package com.monsters.qqbot.listener;

import catcode.CatCodeUtil;
import catcode.CodeBuilder;
import catcode.CodeTemplate;
import com.monsters.qqbot.entity.Image;
import com.monsters.qqbot.entity.User;
import com.monsters.qqbot.mapper.ImageMapper;
import com.monsters.qqbot.service.UserService;
import love.forte.common.utils.Carrier;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.assists.Flag;
import love.forte.simbot.api.message.containers.GroupAccountContainer;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * setu监听
 * @author Monsters
 * @Data 2021-05-21
 */
@Component
public class SeTuListener {
    final Logger logger = LoggerFactory.getLogger(getClass());

    //工具类
    final CatCodeUtil util = CatCodeUtil.INSTANCE;

    // 获取模板
    CodeTemplate<String> template = util.getStringTemplate();

    private GroupAccountContainer groupAccountContainer;

    private Random random = new Random();

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private UserService userService;

    /**
     * setu发送
     *
     * @param groupMsg
     * @param sender
     * @return
     */
    @Async
    @OnGroup
    @Filter(value = "涩图", matchType = MatchType.REGEX_FIND)
    @Filter(value = "setu", matchType = MatchType.REGEX_FIND)
    @Filter(value = "色图", matchType = MatchType.REGEX_FIND)
    public Object sendSetu(GroupMsg groupMsg, MsgSender sender) {
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String QQ = groupMsg.getAccountInfo().getAccountCode();
        User user = userService.getUser(QQ);
        if (user == null) {
            User user1 = new User();
            user1.setQq(QQ);
            userService.addUser(user1);
        } else {
            if (user.getCount() > 100) {
                return sender.SENDER.sendGroupMsg(groupMsg, at + "今天的次数已经用完了，明天再来吧！daisuki");
            } else {
                userService.userCountAdd(user);
            }
        }

        int imageId;
        imageId = random.nextInt(294) - 4;
        logger.info(String.valueOf(imageId));
        Image image = imageMapper.selectById(imageId);
        if (image == null) {
            return sender.SENDER.sendGroupMsg(groupMsg, at + "阿梓喵坏掉了");
        }
        String url = image.getUrl();
        String imageUrl = util.toCat("image", true, "url=" + url);
        logger.info("发送图片");
        return sender.SENDER.sendGroupMsg(groupMsg, at + imageUrl);
    }
}
