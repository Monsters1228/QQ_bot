package com.monsters.web.controller;

import com.monsters.web.entity.SystemUserInfo;
import com.monsters.web.service.SystemUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Monsters
 * @Data 2021/8/23.
 * 打卡用户数据详情API
 */

@RestController
@RequestMapping("/api/userInfo")
public class SystemUserInfoController {

    @Autowired
    private SystemUserInfoService service;

    @PostMapping("")
    public int save(@RequestBody SystemUserInfo userInfo){
        return service.insertItem(userInfo);
    }
}
