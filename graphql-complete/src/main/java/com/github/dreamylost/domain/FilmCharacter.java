package com.github.dreamylost.domain;

import java.util.List;

/**
 * 电影角色
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public interface FilmCharacter {

    //角色的id 姓名 朋友 出演的电影
    String getId();

    String getName();

    List<FilmCharacter> getFriends();

    List<Episode> getAppearsIn();
}
