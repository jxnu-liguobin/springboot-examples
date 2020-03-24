package com.github.dreamylost.data;

import java.util.List;

/**
 * 电影角色
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public interface FilmCharacter {
    String getId();

    String getName();

    List<String> getFriends();

    List<Integer> getAppearsIn();
}
