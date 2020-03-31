package com.github.dreamylost.cache;

import java.util.HashMap;

/**
 * 给gql使用的缓存
 *
 * @author 梦境迷离
 * @time 2020年03月31日17:34:16:57:08
 */
public class LRUCache {

    private int capacity;
    //直接使用LinkedHashMap
    private HashMap<String, Node> map = new HashMap<>();
    private Node head = null;
    private Node end = null;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public Object get(String key) {
        if (map.containsKey(key)) {
            Node n = map.get(key);
            remove(n);
            setHead(n);
            return n.value;
        }
        return null;
    }

    public boolean containsKey(String key) {
        return get(key) != null;
    }

    public void clear() {
        capacity = 0;
        map.clear();
        head = null;
        end = null;
    }

    private void remove(Node n) {
        if (n.pre != null) {
            n.pre.next = n.next;
        } else {
            head = n.next;
        }

        if (n.next != null) {
            n.next.pre = n.pre;
        } else {
            end = n.pre;
        }
    }

    public void remove(String key) {
        if (map.containsKey(key)) {
            Node old = map.get(key);
            remove(old);
        }
    }

    private void setHead(Node n) {
        n.next = head;
        n.pre = null;

        if (head != null)
            head.pre = n;

        head = n;

        if (end == null)
            end = head;
    }

    public void put(String key, Object value) {
        if (map.containsKey(key)) {
            Node old = map.get(key);
            old.value = value;
            remove(old);
            setHead(old);
        } else {
            Node created = new Node(key, value);
            if (map.size() >= capacity) {
                map.remove(end.key);
                remove(end);
                setHead(created);
            } else {
                setHead(created);
            }
            map.put(key, created);
        }
    }
}

class Node {

    String key;
    Object value;
    Node pre;
    Node next;

    public Node(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}