package com.monsters.qqbot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monsters.qqbot.entity.User;
import com.monsters.qqbot.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Monsters
 * @Data 2021-07-22
 */
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * 每天凌晨刷新用户count
     */
    void reSetCount() {
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            user.setCount(0);
            userMapper.updateById(user);
        }
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    public int addUser(User user) {
        return userMapper.insert(user);
    }

    /**
     * 通过 qq号查询用户
     * @param qq
     * @return
     */
    public User getUser(String qq) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("qq", qq);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 用户查询次数+1
     * @param user
     * @return
     */
    public int userCountAdd(User user) {
        user.setCount(user.getCount() + 1);
        return userMapper.updateById(user);
    }
}
