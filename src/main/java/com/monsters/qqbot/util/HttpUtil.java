package com.monsters.qqbot.util;

import com.monsters.qqbot.entity.ClockInResult;
import com.monsters.qqbot.entity.SystemUserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.swing.*;

/**
 * @author Monsters
 * @Data 2021/8/24.
 */
public class HttpUtil {
    
    final static String healthySystemUrl = "http://srv.zsc.edu.cn/f/_jiankangSave";
    final static HttpHeaders headers = new HttpHeaders();

    /**
     * 打卡
     * @param
     * @return
     */
    public static ClockInResult sendPost(SystemUserInfo userInfo, String uuid){

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String,String> map = new LinkedMultiValueMap<>();

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        map.add("uaid", uuid);
        map.add("mobile", userInfo.getMobile());
        map.add("provinceJg",userInfo.getProvinceJg());
        map.add("cityJg", userInfo.getCityJg());
        map.add("provinceHome", userInfo.getProvinceHome());
        map.add("cityHome", userInfo.getCityHome());
        map.add("addressHome", userInfo.getAddressHome());
        map.add("province", userInfo.getProvince());
        map.add("city",userInfo.getCity());
        map.add("address",userInfo.getAddress());;
        map.add("touchZhongGaoFlag", "没有");
        map.add("huBeiManFlag2", "否");
        map.add("sheQuTouchFlag", "否");
        map.add("jinWaiTouchFlag", "否");
        map.add("huCheckFlag", "是");
        map.add("health", "正常");
        map.add("memberHealth", "正常");
        map.add("suikangCode", "绿码");
        map.add("yimiao", "已接种两剂");
        map.add("infxarea", "否");
        RestTemplate restTemplate = new RestTemplate();

        ClockInResult result = restTemplate.postForObject( healthySystemUrl, request, ClockInResult.class);
        return result;
    }
}
