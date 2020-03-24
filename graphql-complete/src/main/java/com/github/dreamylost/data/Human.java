package com.github.dreamylost.data;

import java.util.List;

/**
 * 人类
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public class Human implements FilmCharacter {
    final String id;
    final String name;
    final List<String> friends;
    final List<Integer> appearsIn;
    final String homePlanet;

    public Human(String id, String name, List<String> friends, List<Integer> appearsIn, String homePlanet) {
        this.id = id;
        this.name = name;
        this.friends = friends;
        this.appearsIn = appearsIn;
        this.homePlanet = homePlanet;
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

    public String getHomePlanet() {
        return homePlanet;
    }

    @Override
    public String toString() {
        return "Human{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
