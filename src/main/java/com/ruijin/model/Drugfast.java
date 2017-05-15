package com.ruijin.model;

/**
 * 耐药性
 */
public class Drugfast {

  private String drugName = "";//药品名
  private boolean isfast = false;//是否耐药

  public Drugfast(String drugName, boolean isfast) {
    this.drugName = drugName;
    this.isfast = isfast;
  }

  public Drugfast() {
  }

  public String getDrugName() {
    return drugName;
  }

  public void setDrugName(String drugName) {
    this.drugName = drugName;
  }

  public boolean isIsfast() {
    return isfast;
  }

  public void setIsfast(Boolean isfast) {
    this.isfast = isfast;
  }

  @Override public String toString() {
    return "Drugfast{" + "drugName='" + drugName + '\'' + ", isfast=" + isfast + '}';
  }
}
