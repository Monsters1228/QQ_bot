package com.monsters.qqbot.controller;

import com.monsters.qqbot.entity.SystemUser;
import com.monsters.qqbot.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Monsters
 * @Data 2021/8/23.
 * 打卡用户信息API
 */
@RestController
@RequestMapping("/api/user")
public class SystemUserController {

    @Autowired
    private SystemUserService service;

    @PostMapping("")
    public int save(@RequestBody SystemUser user){
        return service.insertItem(user);
    }
}
