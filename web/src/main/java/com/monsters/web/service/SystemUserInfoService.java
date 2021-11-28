package com.monsters.web.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.monsters.web.entity.SystemUserInfo;
import com.monsters.web.mapper.SystemUserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Monsters
 * @Data 2021/8/23.
 */
@Service
public class SystemUserInfoService {

    @Autowired
    private SystemUserInfoMapper mapper;

    public int insertItem(SystemUserInfo systemUserInfo){
        SystemUserInfo userInfo = queryByQqCode(systemUserInfo.getQqCode());
        // 如果已经存在则更新
        if(userInfo != null){
            return updateItem(systemUserInfo);
        }
        return mapper.insert(systemUserInfo);
    }

    /**
     * 通过qq号更新数据
     * @param userInfo
     * @return
     */
    public int updateItem(SystemUserInfo userInfo){
        QueryWrapper<SystemUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("qq_code",userInfo.getQqCode());
        return mapper.update(userInfo,wrapper);
    }

    /**
     * 通过qq号查询详细数据
     * @param qqCode
     * @return
     */
    public SystemUserInfo queryByQqCode(String qqCode){
        QueryWrapper<SystemUserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("qq_code", qqCode);
        return mapper.selectOne(wrapper);
    }
}
