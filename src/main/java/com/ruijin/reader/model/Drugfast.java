package com.ruijin.reader.model;

/**
 * 耐药
 */
public class Drugfast {

  private String drugName = "";
  private boolean isfast = false;

  public Drugfast(String drugName, boolean isfast) {
    this.drugName = drugName;
    this.isfast = isfast;
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

  public void setIsfast(boolean isfast) {
    this.isfast = isfast;
  }

  @Override public String toString() {
    return "Drugfast{" + "drugName='" + drugName + '\'' + ", isfast=" + isfast + '}';
  }
}
