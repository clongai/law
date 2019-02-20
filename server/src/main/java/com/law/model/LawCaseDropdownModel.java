package com.law.model;

/**
 * @Auther: nonghz
 * @Date: 2018/10/31 11:46
 * @Description:
 */
public class LawCaseDropdownModel {
    private int caseID;
    private String value;
    private String parent;

    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }
}
