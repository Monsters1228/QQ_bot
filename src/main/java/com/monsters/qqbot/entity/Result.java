package com.monsters.qqbot.entity;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author Monsters
 * @Data 2021-24-16
 */
@Data
public class Result {
    String error;
    com.monsters.qqbot.entity.Data[] data;
}
