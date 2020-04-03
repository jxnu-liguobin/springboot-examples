package com.github.dreamylost.domain;

import java.util.List;

/**
 * 人类角色
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public class Human implements FilmCharacter {
    final String id;
    final String name;
    final List<String> friends;
    final List<Integer> appearsIn;
    //人类的家园！（是行星）
    final String homePlanet;
    //自定义标量类型
    final String email;

    public Human(String id, String name, List<String> friends, List<Integer> appearsIn, String homePlanet, String email) {
        this.id = id;
        this.name = name;
        this.friends = friends;
        this.appearsIn = appearsIn;
        this.homePlanet = homePlanet;
        this.email = email;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<String> getFriends() {
        return friends;
    }

    @Override
    public List<Integer> getAppearsIn() {
        return appearsIn;
    }

    public String getHomePlanet() {
        return homePlanet;
    }

    public String getEmail() {
        return email;
    }

}
