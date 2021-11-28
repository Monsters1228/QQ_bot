package com.monsters.botcore.entity;

import lombok.Data;

/**
 * @author Monsters
 * @Data 2021-24-16
 */
@Data
public class Result {
    String error;

    com.monsters.botcore.entity.Data[] data;
}
