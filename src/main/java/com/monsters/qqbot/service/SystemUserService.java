package com.monsters.qqbot.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monsters.qqbot.entity.SystemUser;
import com.monsters.qqbot.mapper.SystemUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Monsters
 * @Data 2021/8/23.
 */
@Service
public class SystemUserService {

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 绑定 qq 号和 uuid
     * @param systemUser
     * @return
     */
    public int insertItem(SystemUser systemUser){
        SystemUser user = queryByQqCode(systemUser.getQqCode());
        // 存在数据则更新
        if(user != null){
         return updateItem(systemUser);
        }
        return systemUserMapper.insert(systemUser);
    }

    /**
     * 更新 通过 uuid
     * @param systemUser
     * @return
     */
    public int updateItem(SystemUser systemUser){
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("qq_code", systemUser.getQqCode());
        return systemUserMapper.update(systemUser,wrapper);
    }

    /**
     * 通过qq号查询
     * @param qqCode
     * @return
     */
    public SystemUser queryByQqCode(String qqCode){
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("qq_code", qqCode);
        return systemUserMapper.selectOne(wrapper);
    }

    /**
     * 获取所有的用户
     * @return
     */
    public List<SystemUser> queryAll(){
        return systemUserMapper.selectList(null);
    }
}
