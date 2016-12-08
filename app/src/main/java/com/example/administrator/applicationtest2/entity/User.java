package com.example.administrator.applicationtest2.entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016-11-25.
 */
public class User extends BmobObject {
    private int id;
    private String name;
    private String Account;
    private String password;
    private int Amount;
    private int Phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int phone) {
        Phone = phone;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }
}
