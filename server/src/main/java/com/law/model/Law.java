package com.law.model;

import java.util.List;

public class Law {
  private LawOrder lawOrder;
  private OrderPO orderPO;
  private List<LawPerson> lawPerson1;
  private List<LawPerson> lawPerson2;
  private List<LawPerson> lawPerson3;
  private String serviceProject;
  
  

  public OrderPO getOrderPO() {
	return orderPO;
}

public void setOrderPO(OrderPO orderPO) {
	this.orderPO = orderPO;
}

public LawOrder getLawOrder() {
    return lawOrder;
  }

  public void setLawOrder(LawOrder lawOrder) {
    this.lawOrder = lawOrder;
  }

  public List<LawPerson> getLawPerson1() {
    return lawPerson1;
  }

  public void setLawPerson1(List<LawPerson> lawPerson1) {
    this.lawPerson1 = lawPerson1;
  }

  public List<LawPerson> getLawPerson2() {
    return lawPerson2;
  }

  public void setLawPerson2(List<LawPerson> lawPerson2) {
    this.lawPerson2 = lawPerson2;
  }

  public List<LawPerson> getLawPerson3() {
    return lawPerson3;
  }

  public void setLawPerson3(List<LawPerson> lawPerson3) {
    this.lawPerson3 = lawPerson3;
  }

  public String getServiceProject() {
    return serviceProject;
  }

  public void setServiceProject(String serviceProject) {
    this.serviceProject = serviceProject;
  }
}
