package com.law.model;


import javax.persistence.*;

/**
 * @Auther: nonghz
 * @Date: 2018/10/31 11:14
 * @Description:
 */
@Entity
@Table(name = "law_case", schema = "law", catalog = "")
public class LawCase {
    private int caseId;
    private String caseName;
    private String caseDesc;
    private int parentCase;
    private int caseType;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "case_id")
    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    @Basic
    @Column(name = "case_name")
    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    @Basic
    @Column(name = "case_desc")
    public String getCaseDesc() {
        return caseDesc;
    }

    public void setCaseDesc(String caseDesc) {
        this.caseDesc = caseDesc;
    }

    @Basic
    @Column(name = "parent_case")
    public int getParentCase() {
        return parentCase;
    }

    public void setParentCase(int parentCase) {
        this.parentCase = parentCase;
    }

    @Basic
    @Column(name = "case_type")
    public int getCaseType() {
        return caseType;
    }

    public void setCaseType(int caseType) {
        this.caseType = caseType;
    }
}
