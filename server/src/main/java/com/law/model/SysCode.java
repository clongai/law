package com.law.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sys_code", schema = "law", catalog = "")
public class SysCode {
  private String codeDesc;
  private int codeId;
  private String codeName;
  private Integer codeSn;
  private String codeType;
  private int codeValue;

  @Basic
  @Column(name = "code_desc")
  public String getCodeDesc() {
    return codeDesc;
  }

  public void setCodeDesc(String codeDesc) {
    this.codeDesc = codeDesc;
  }

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "code_id")
  public int getCodeId() {
    return codeId;
  }

  public void setCodeId(int codeId) {
    this.codeId = codeId;
  }

  @Basic
  @Column(name = "code_name")
  public String getCodeName() {
    return codeName;
  }

  public void setCodeName(String codeName) {
    this.codeName = codeName;
  }

  @Basic
  @Column(name = "code_sn")
  public Integer getCodeSn() {
    return codeSn;
  }

  public void setCodeSn(Integer codeSn) {
    this.codeSn = codeSn;
  }

  @Basic
  @Column(name = "code_type")
  public String getCodeType() {
    return codeType;
  }

  public void setCodeType(String codeType) {
    this.codeType = codeType;
  }

  @Basic
  @Column(name = "code_value")
  public int getCodeValue() {
    return codeValue;
  }

  public void setCodeValue(int codeValue) {
    this.codeValue = codeValue;
  }

  @Override
  public int hashCode() {

    return Objects.hash(codeId, codeName, codeDesc, codeValue, codeSn);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SysCode sysCode = (SysCode) o;
    return codeId == sysCode.codeId &&
        codeValue == sysCode.codeValue &&
        Objects.equals(codeName, sysCode.codeName) &&
        Objects.equals(codeDesc, sysCode.codeDesc) &&
        Objects.equals(codeSn, sysCode.codeSn);
  }
}
