package com.github.dreamylost.webflux.model;

public class User {
    private Long uid;
    private String name;
    private String psw;

    public Long getUid() {
        return uid;
    }

    public User() {
    }

    public User(Long uid, String name, String psw) {
        this.uid = uid;
        this.name = name;
        this.psw = psw;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", psw='" + psw + '\'' +
                '}';
    }
}
