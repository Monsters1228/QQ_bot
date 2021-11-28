package com.monsters.botcore.util;

import com.monsters.botcore.entity.ClockInResult;
import com.monsters.botcore.entity.SystemUserInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

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
        map.add("linkman",userInfo.getLinkman());
        map.add("linkmanmobile",userInfo.getLinkmanmobile());
        map.add("provinceHome", userInfo.getProvinceHome());
        map.add("cityHome", userInfo.getCityHome());
        map.add("addressHome", userInfo.getAddressHome());
        map.add("province2", userInfo.getProvince2());
        map.add("city2",userInfo.getCity2());
        map.add("address2",userInfo.getAddress2());;
        map.add("province", userInfo.getProvince());
        map.add("city",userInfo.getCity());
        map.add("address",userInfo.getAddress());;

        map.add("health", userInfo.getHealth());
        map.add("memberHealth", userInfo.getMemberHealth());
        map.add("suikangCode", userInfo.getSuikangCode());
        map.add("yimiao", userInfo.getYimiao());
        map.add("todaytrave", userInfo.getTodaytrave());
        map.add("zuoritrave", userInfo.getZuoritrave());
        map.add("touchZhongGaoFlag", userInfo.getTouchZhongGaoFlag());
        map.add("infxarea", userInfo.getInfxarea());
        RestTemplate restTemplate = new RestTemplate();

        ClockInResult result = restTemplate.postForObject( healthySystemUrl, request, ClockInResult.class);
        return result;
    }
}
