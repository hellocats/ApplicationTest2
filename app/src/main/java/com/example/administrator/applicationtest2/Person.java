package com.example.administrator.applicationtest2;

/**
 * Created by Administrator on 2016-08-18.
 */
public class Person {
    private int id;
    private String name;
    private int Amount;
    private int Phone;
    public Person(){

    }
    //有参数的构造器
    public Person(int id, String name, int Amount, int Phone){
            this.id = id;
            this.name = name;
            this.Amount = Amount;
            this.Phone = Phone;
        }
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
    public int getPhone() {
        return Phone;
    }
    public void setPhone(int Phone) {
        this.Phone = Phone;
    }
    public int getAmount() {
        return Amount;
    }
    public void setAmount(int Amount) {
        this.Amount = Amount;
    }
}
