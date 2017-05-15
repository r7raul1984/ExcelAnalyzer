package com.ruijin.reader.model;

import org.joda.time.DateTime;

import java.util.HashSet;
import java.util.Set;

public class InspectInfo {

  private DateTime inspectDate;//检验时间
  private String inspectType = "";//3,4
  private String grem = "";//细菌名称
  private Set<Drugfast> drugfasts = new HashSet<Drugfast>();

  public InspectInfo(DateTime inspectDate, String inspectType, String grem,
      Set<Drugfast> drugfasts) {
    this.inspectDate = inspectDate;
    this.inspectType = inspectType;
    this.grem = grem;
    this.drugfasts = new HashSet<Drugfast>(drugfasts);
  }

  public InspectInfo() {

  }

  public Set<Drugfast> getDrugfasts() {
    return drugfasts;
  }

  public void setDrugfasts(Set<Drugfast> drugfasts) {
    this.drugfasts = drugfasts;
  }

  public DateTime getInspectDate() {
    return inspectDate;
  }

  public void setInspectDate(DateTime inspectDate) {
    this.inspectDate = inspectDate;
  }

  public String getInspectType() {
    return inspectType;
  }

  public void setInspectType(String inspectType) {
    this.inspectType = inspectType;
  }

  public String getGrem() {
    return grem;
  }

  public void setGrem(String grem) {
    this.grem = grem;
  }

  @Override public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;

    InspectInfo that = (InspectInfo) o;

    if (inspectDate != null ? !inspectDate.equals(that.inspectDate) : that.inspectDate != null)
      return false;
    if (inspectType != null ? !inspectType.equals(that.inspectType) : that.inspectType != null)
      return false;
    if (grem != null ? !grem.equals(that.grem) : that.grem != null)
      return false;
    return drugfasts != null ? drugfasts.equals(that.drugfasts) : that.drugfasts == null;
  }

  @Override public int hashCode() {
    int result = inspectDate != null ? inspectDate.hashCode() : 0;
    result = 31 * result + (inspectType != null ? inspectType.hashCode() : 0);
    result = 31 * result + (grem != null ? grem.hashCode() : 0);
    result = 31 * result + (drugfasts != null ? drugfasts.hashCode() : 0);
    return result;
  }
}
