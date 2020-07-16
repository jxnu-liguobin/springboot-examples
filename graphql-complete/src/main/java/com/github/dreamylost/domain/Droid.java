package com.github.dreamylost.domain;

import java.util.List;

/**
 * 机器人角色
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public class Droid implements FilmCharacter {
    final String id;
    final String name;
    final List<FilmCharacter> friends;
    // 使用枚举，否则在graphql调用getNameByValue时会匹配不到，一个是数值，一个是枚举
    final List<Episode> appearsIn;
    //机器人的主要功能
    final String primaryFunction;

    public Droid(String id, String name, List<FilmCharacter> friends, List<Episode> appearsIn, String primaryFunction) {
        this.id = id;
        this.name = name;
        this.friends = friends;
        this.appearsIn = appearsIn;
        this.primaryFunction = primaryFunction;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<FilmCharacter> getFriends() {
        return friends;
    }

    public List<Episode> getAppearsIn() {
        return appearsIn;
    }

    public String getPrimaryFunction() {
        return primaryFunction;
    }

    @Override
    public String toString() {
        return "Droid{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
