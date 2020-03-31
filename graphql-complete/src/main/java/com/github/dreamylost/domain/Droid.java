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
    final List<String> friends;
    final List<Integer> appearsIn;
    //机器人的主要功能
    final String primaryFunction;

    public Droid(String id, String name, List<String> friends, List<Integer> appearsIn, String primaryFunction) {
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

    public List<String> getFriends() {
        return friends;
    }

    public List<Integer> getAppearsIn() {
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
