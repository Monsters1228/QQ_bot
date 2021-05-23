package com.monsters.qqbot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monsters.qqbot.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
