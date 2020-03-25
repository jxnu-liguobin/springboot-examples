package com.github.dreamylost;

import java.io.Serializable;

/**
 * 用于测试的实体类
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private int uid;
    private String userName;
    private String passWord;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }


    public User(int uid, String userName, String passWord) {
        super();
        this.uid = uid;
        this.userName = userName;
        this.passWord = passWord;
    }

    public User() {
        super();
    }
}