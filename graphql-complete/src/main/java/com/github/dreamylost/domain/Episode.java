package com.github.dreamylost.domain;

/**
 * 电影 电视剧 枚举类型
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public enum Episode {

    NEWHOPE(0),
    EMPIRE(1),
    JEDI(2);

    Integer value;

    Episode(Integer value) {
        this.value = value;
    }
}
