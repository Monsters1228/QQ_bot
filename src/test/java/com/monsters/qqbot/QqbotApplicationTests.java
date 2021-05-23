package com.monsters.qqbot;

import com.monsters.qqbot.entity.Image;
import com.monsters.qqbot.mapper.ImageMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class QqbotApplicationTests {

    @Autowired
    private ImageMapper imageMapper;
    @Test
    public void myTest(){
        List<Image> list = imageMapper.selectList(null);
        list.forEach(System.out::println);
    }

}
