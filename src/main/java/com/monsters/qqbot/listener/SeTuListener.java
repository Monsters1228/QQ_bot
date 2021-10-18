package com.monsters.qqbot.listener;

import catcode.CatCodeUtil;
import catcode.CodeBuilder;
import catcode.CodeTemplate;
import com.monsters.qqbot.entity.Image;
import com.monsters.qqbot.entity.Result;
import com.monsters.qqbot.entity.User;
import com.monsters.qqbot.mapper.ImageMapper;
import com.monsters.qqbot.service.UserService;
import love.forte.common.utils.Carrier;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.assists.Flag;
import love.forte.simbot.api.message.containers.GroupAccountContainer;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.*;

/**
 * setu监听
 *
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

    @Autowired
    private RestTemplate restTemplate;

    /**
     * setu发送
     *
     * @param groupMsg
     * @param sender
     * @return
     */
    @Async
    @OnGroup
    @Filter(value = "setu {{tag,.+}}", matchType = MatchType.REGEX_MATCHES)
    public Object sendSetu1(GroupMsg groupMsg, MsgSender sender,
                            @FilterValue("tag") String tag) {
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        String QQ = groupMsg.getAccountInfo().getAccountCode();
        User user = userService.getUser(QQ);

        if (user == null) {
            User user1 = new User();
            user1.setQq(QQ);
            userService.addUser(user1);
        } else {
            if (user.getCount() > 1000) {
                return sender.SENDER.sendGroupMsg(groupMsg, at + "今天的次数已经用完了，明天再来吧！daisuki");
            } else {
                userService.userCountAdd(user);
            }
        }
        String url = "https://api.lolicon.app/setu/v2?tag={tag}";
        Map<String,Object> map  = new HashMap<>();
        map.put("tag",tag);
        Result response = restTemplate.getForObject(url,Result.class,map);
        if(response.getData().length == 0){
            return sender.SENDER.sendGroupMsg(groupMsg, at + "没有搜到 daisuki!");
        }
        // String imageUrl = util.toCat("image", true, "url=" + url);
        logger.info("发送图片");
        String imageUrl = util.toCat("image", true, "url=" + response.getData()[0].getUrls().getOriginal());
        return sender.SENDER.sendGroupMsg(groupMsg,
                "pid: " + response.getData()[0].getPid() + "\n" + "title: " + response.getData()[0].getAuthor() + imageUrl);
    }

    /**
     * @param groupMsg
     * @param sender
     * @return
     */
    @Async
    @OnGroup
    @Filter(value = "setu", matchType = MatchType.ENDS_WITH)
    public Object sendSetu2(GroupMsg groupMsg, MsgSender sender) {
        String url = "https://api.lolicon.app/setu/v2";
        Result response = restTemplate.getForObject(url, Result.class);
        String imageUrl = util.toCat("image", true, "url=" + response.getData()[0].getUrls().getOriginal());
        return sender.SENDER.sendGroupMsg(groupMsg,
                "pid: " + response.getData()[0].getPid() + "\n" + "title: " + response.getData()[0].getAuthor() + imageUrl);
    }


    @Async
    @OnGroup
    @Filter(value = "setu.", matchType = MatchType.STARTS_WITH)
    public Object sendSetu3(GroupMsg groupMsg, MsgSender sender) {
        String url = "https://api.lolicon.app/setu/v2?r18=1";
        Result response = restTemplate.getForObject(url, Result.class);
//        logger.info(response.getData()[0].getUrls().getOriginal());

        String imageUrl = util.toCat("image", true, "url=" + response.getData()[0].getUrls().getOriginal());
        return sender.SENDER.sendGroupMsg(groupMsg,
                "pid: " + response.getData()[0].getPid() + "\n" + "title: " + response.getData()[0].getAuthor() + imageUrl);
    }


    /**
     *
     * @param groupMsg
     * @param sender
     */
    @OnGroup
    @Filter(value = "摩多摩多", matchType = MatchType.STARTS_WITH)
    public void sendSeTuGroup(GroupMsg groupMsg, MsgSender sender){
        for(int i =0;i < 10;i++){
            sendSetu2(groupMsg,sender);
        }
    }
}
