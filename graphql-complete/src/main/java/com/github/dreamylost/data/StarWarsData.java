package com.github.dreamylost.data;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Arrays.asList;

/**
 * 这包含本示例中使用的数据
 */
@SuppressWarnings("unused")
/**
 * 人类
 *
 * @author 梦境迷离
 * @time 2020年03月24日17:57:08
 */
public class StarWarsData {


    static Human luke = new Human(
            "1000",
            "Luke Skywalker",
            asList("1002", "1003", "2000", "2001"),
            asList(4, 5, 6),
            "Tatooine"
    );

    static Human vader = new Human(
            "1001",
            "Darth Vader",
            asList("1004"),
            asList(4, 5, 6),
            "Tatooine"
    );

    static Human han = new Human(
            "1002",
            "Han Solo",
            asList("1000", "1003", "2001"),
            asList(4, 5, 6),
            null
    );

    static Human leia = new Human(
            "1003",
            "Leia Organa",
            asList("1000", "1002", "2000", "2001"),
            asList(4, 5, 6),
            "Alderaan"
    );

    static Human tarkin = new Human(
            "1004",
            "Wilhuff Tarkin",
            asList("1001"),
            asList(4),
            null
    );

    static Map<String, Human> humanData = new LinkedHashMap<>();

    static {
        humanData.put("1000", luke);
        humanData.put("1001", vader);
        humanData.put("1002", han);
        humanData.put("1003", leia);
        humanData.put("1004", tarkin);
    }

    static Droid threepio = new Droid(
            "2000",
            "C-3PO",
            asList("1000", "1002", "1003", "2001"),
            asList(4, 5, 6),
            "Protocol"
    );

    static Droid artoo = new Droid(
            "2001",
            "R2-D2",
            asList("1000", "1002", "1003"),
            asList(4, 5, 6),
            "Astromech"
    );

    static Map<String, Droid> droidData = new LinkedHashMap<>();

    static {
        droidData.put("2000", threepio);
        droidData.put("2001", artoo);

    }

    public static boolean isHuman(String id) {
        return humanData.get(id) != null;
    }

    public static Object getCharacterData(String id) {
        if (humanData.get(id) != null) {
            return humanData.get(id);
        } else if (droidData.get(id) != null) {
            return droidData.get(id);
        }
        return null;
    }


}
