package com.example.administrator.applicationtest2.view.test.userDefine;

/**
 * Date:2017-12-29
 * Author:he
 * Description:
 */

public class RuleEntity {
    private String sName;
    private int nLine;
    private int nColumn;

    public RuleEntity(){

    }
    public RuleEntity(String sName,int nLine,int nColumn){
        this.sName = sName;
        this.nLine = nLine;
        this.nColumn = nColumn;
    }


    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public int getnLine() {
        return nLine;
    }

    public void setnLine(int nLine) {
        this.nLine = nLine;
    }

    public int getnColumn() {
        return nColumn;
    }

    public void setnColumn(int nColumn) {
        this.nColumn = nColumn;
    }
}
