package com.monsters.botcore.listener;

import catcode.CatCodeUtil;
import catcode.CodeTemplate;
import com.monsters.botcore.entity.Result;
import love.forte.simbot.annotation.Filter;
import love.forte.simbot.annotation.FilterValue;
import love.forte.simbot.annotation.OnGroup;
import love.forte.simbot.api.message.containers.GroupAccountContainer;
import love.forte.simbot.api.message.events.GroupMsg;
import love.forte.simbot.api.sender.MsgSender;
import love.forte.simbot.filter.MatchType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * setu监听
 *
 * @author Monsters
 * @Data 2021-05-21
 */
@Component
public class SeTuListener {

    // 不可以色色
    boolean canBeColored = true;

    final Logger logger = LoggerFactory.getLogger(getClass());

    //工具类
    final CatCodeUtil util = CatCodeUtil.INSTANCE;

    // 获取模板
    CodeTemplate<String> template = util.getStringTemplate();

    private GroupAccountContainer groupAccountContainer;

    private Random random = new Random();

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
    public Object sendSetu1(GroupMsg groupMsg, MsgSender sender, @FilterValue("tag") String tag) {
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        if(canBeColored == false)
            return  sender.SENDER.sendGroupMsg(groupMsg, at + "不可以色色");

        String api = "https://api.lolicon.app/setu/v2?tag={tag}";
        Map<String,Object> map  = new HashMap<>();
        map.put("tag",tag);
        Result response = new Result();
        try{
            response = restTemplate.getForObject(api, Result.class, map);
        }catch (ResourceAccessException exception){
            return  sender.SENDER.sendGroupMsg(groupMsg, at + "请求超时，请重试");
        }
        if(response.getData().length == 0){
            return sender.SENDER.sendGroupMsg(groupMsg, at + "没有搜到 daisuki!");
        }
        String pid = response.getData()[0].getPid();
        String url = "https://pixiv.re/" + pid +".jpg";
        try {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(null, new HttpHeaders()), String.class);
        }catch (Exception e){
            logger.error("不是单张图片");
            url = "https://pixiv.re/" + pid +"-1.jpg";
        }
        logger.info(url);
        String imageUrl = util.toCat("image", true, "url=" + url);
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
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        if(canBeColored == false)
            return  sender.SENDER.sendGroupMsg(groupMsg, at + "不可以色色");

            String api = "https://api.lolicon.app/setu/v2";
            Result response = new Result();
        try{
            response = restTemplate.getForObject(api, Result.class);
        }catch (ResourceAccessException exception){
            return sender.SENDER.sendGroupMsg(groupMsg, at + "请求超时，请重试");
        }
        String pid = response.getData()[0].getPid();
        String url = "https://pixiv.re/" + pid +".jpg";
        try {
            ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<String>(null, new HttpHeaders()), String.class);
        }catch (Exception e){
            logger.error("不是单张图片");
            url = "https://pixiv.re/" + pid +"-1.jpg";
        }
        logger.info(url);
        String imageUrl = util.toCat("image", true, "url=" + url);
        return sender.SENDER.sendGroupMsg(groupMsg,
                "pid: " + response.getData()[0].getPid() + "\n" + "title: " + response.getData()[0].getAuthor() + imageUrl);
    }


    @Async
    @OnGroup
    @Filter(value = "setu.", matchType = MatchType.STARTS_WITH)
    public Object sendSetu3(GroupMsg groupMsg, MsgSender sender) {
        String url = "https://api.lolicon.app/setu/v2?r18=1";
        Result response = restTemplate.getForObject(url, Result.class);
        String pid = response.getData()[0].getPid();
        String imageUrl = util.toCat("image", true, "url=" + "https://pixiv.re/" + pid +".jpg");
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

    @OnGroup
    @Filter(value = "可以色色", matchType = MatchType.STARTS_WITH)
    public Object canBeColored(GroupMsg groupMsg, MsgSender sender){
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        if (groupMsg.getAccountInfo().getAccountCode().equals("2859456720")){
            this.canBeColored = true;
        }
        return sender.SENDER.sendGroupMsg(groupMsg, at + "好的主人，可以色色");
    }


    @OnGroup
    @Filter(value = "不可以色色", matchType = MatchType.STARTS_WITH)
    public Object notColor(GroupMsg groupMsg, MsgSender sender){
        String at = template.at(groupMsg.getAccountInfo().getAccountCode());
        if (groupMsg.getAccountInfo().getAccountCode().equals("2859456720")){
            this.canBeColored = false;
        }
        return sender.SENDER.sendGroupMsg(groupMsg, at + "好的主人，不可以色色");
    }
}
