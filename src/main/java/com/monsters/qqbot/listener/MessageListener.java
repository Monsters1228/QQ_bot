package com.monsters.qqbot.listener;

import catcode.CatCodeUtil;
import catcode.CodeBuilder;
import catcode.CodeTemplate;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.annotation.Priority;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.constant.PriorityConstant;
import love.forte.simbot.filter.AtDetection;
import love.forte.simbot.filter.MatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 普通消息监听
 *
 * @author Monsters
 * @Data 2021-23-23
 */
@Component
public class MessageListener {
    final Logger logger = LoggerFactory.getLogger(getClass());

    //工具类
    final CatCodeUtil util = CatCodeUtil.INSTANCE;

    //构建器
    final CodeBuilder<String> builder = util.getStringCodeBuilder("image", true);

    // 获取模板
    CodeTemplate<String> template = util.getStringTemplate();

    /**
     * 自定义监听群消息
     *
     * @return
     */

    @Priority(PriorityConstant.SECOND)
    @OnGroup
    @Filter(value = "Hi", matchType = MatchType.CONTAINS)
    public void groupMsg(GroupMsg groupMsg, MsgSender sender, AtDetection atDetection) {
        if (atDetection.atBot() == true) {
            return;
        }
        sender.SENDER.sendGroupMsg(groupMsg, "ohayou");
        logger.info("监听消息");
    }


    @Priority(PriorityConstant.FIRST)
    @OnGroup
    @Filter(value = "晚安", matchType = MatchType.CONTAINS)
    public void goodNight(GroupMsg groupMsg, MsgSender sender, AtDetection atDetection) {
        if (atDetection.atBot() == true) {
            return;
        }
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        sender.SENDER.sendGroupMsg(groupMsg, at + "oyasumi");
    }
}
