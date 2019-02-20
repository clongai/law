package com.law.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "law_person", schema = "law", catalog = "")
public class LawPerson {
  private Integer orderId;
  private Integer personAge;
  private String personCardNo;
  private int personId;
  private String personName;
  private String personPhone;
  private String personType;
  private String landline;
  private String personEmail;
  

  public String getLandline() {
	return landline;
}

public void setLandline(String landline) {
	this.landline = landline;
}


@Column(name="person_email")
public String getPersonEmail() {
	return personEmail;
}

public void setPersonEmail(String personEmail) {
	this.personEmail = personEmail;
}

@Basic
  @Column(name = "order_id", nullable = true)
  public Integer getOrderId() {
    return orderId;
  }

  public void setOrderId(Integer orderId) {
    this.orderId = orderId;
  }

  @Basic
  @Column(name = "person_age", nullable = true)
  public Integer getPersonAge() {
    return personAge;
  }

  public void setPersonAge(Integer personAge) {
    this.personAge = personAge;
  }

  @Basic
  @Column(name = "person_card_no", nullable = true, length = 20)
  public String getPersonCardNo() {
    return personCardNo;
  }

  public void setPersonCardNo(String personCardNo) {
    this.personCardNo = personCardNo;
  }

  @Id
  @Column(name = "person_id", nullable = false)
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  public int getPersonId() {
    return personId;
  }

  public void setPersonId(int personId) {
    this.personId = personId;
  }

  @Basic
  @Column(name = "person_name", nullable = true, length = 255)
  public String getPersonName() {
    return personName;
  }

  public void setPersonName(String personName) {
    this.personName = personName;
  }

  @Basic
  @Column(name = "person_phone", nullable = true, length = 20)
  public String getPersonPhone() {
    return personPhone;
  }

  public void setPersonPhone(String personPhone) {
    this.personPhone = personPhone;
  }

  @Basic
  @Column(name = "person_type", nullable = true, length = 255)
  public String getPersonType() {
    return personType;
  }

  public void setPersonType(String personType) {
    this.personType = personType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(personId, personType, personName, personAge, personCardNo, personPhone);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    LawPerson lawPerson = (LawPerson) o;
    return personId == lawPerson.personId &&
        Objects.equals(personType, lawPerson.personType) &&
        Objects.equals(personName, lawPerson.personName) &&
        Objects.equals(personAge, lawPerson.personAge) &&
        Objects.equals(personCardNo, lawPerson.personCardNo) &&
        Objects.equals(personPhone, lawPerson.personPhone);
  }
}
