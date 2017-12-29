package com.example.administrator.applicationtest2.view.test.userDefine;

import java.util.Date;

/**
 * Date:2017-12-29
 * Author:he
 * Description:
 */

public class ListViewEntity {
    private String sName;
    private int nAge;
    private int nSex;
    private Date dYear;
    private String sPlace;
    private String sHobby;
    private String sSchool;


    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getnAge() {
        return nAge;
    }

    public void setnAge(int nAge) {
        this.nAge = nAge;
    }

    public int getnSex() {
        return nSex;
    }

    public void setnSex(int nSex) {
        this.nSex = nSex;
    }

    public Date getdYear() {
        return dYear;
    }

    public void setdYear(Date dYear) {
        this.dYear = dYear;
    }

    public String getsPlace() {
        return sPlace;
    }

    public void setsPlace(String sPlace) {
        this.sPlace = sPlace;
    }

    public String getsHobby() {
        return sHobby;
    }

    public void setsHobby(String sHobby) {
        this.sHobby = sHobby;
    }

    public String getsSchool() {
        return sSchool;
    }

    public void setsSchool(String sSchool) {
        this.sSchool = sSchool;
    }
}
