package com.monsters.qqbot.listener;

import catcode.CatCodeUtil;
import catcode.CodeBuilder;
import catcode.CodeTemplate;
import love.forte.common.utils.Carrier;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.assists.Flag;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * At 消息监听
 * @author Monsters
 * @Data 2021-25-23
 */
@Component
public class AtListener {
    final Logger logger = LoggerFactory.getLogger(getClass());

    //工具类
    final CatCodeUtil util = CatCodeUtil.INSTANCE;

    //构建器
    final CodeBuilder<String> builder = util.getStringCodeBuilder("image", true);

    // 获取模板
    CodeTemplate<String> template = util.getStringTemplate();


    @OnGroup
    @Filter(atBot = true)
    public Carrier<? extends Flag<GroupMsg.FlagContent>> atMyReturn(GroupMsg groupMsg, MsgSender sender) {

        if (groupMsg.getAccountInfo().getAccountCode().equals("2859456720") || groupMsg.getAccountInfo().getAccountCode().equals("1545029383")) {
            return sender.SENDER.sendGroupMsg(groupMsg, "主人，我在");
        }
        return sender.SENDER.sendGroupMsg(groupMsg, "我在");
    }
}
