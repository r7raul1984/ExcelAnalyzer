package com.ruijin.model;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;

import java.util.*;

/**
 * 病患
 */
public class Patient {

  private long id;//住院号
  private String name;//姓名
  private DateTime admissionDate;//入院时间
  private int age;//年龄

  private List<InspectInfo> inspectInfos = new ArrayList<InspectInfo>();

  public Patient() {

  }

  public Patient(int id, String name, Date admissionDate, int age, List<InspectInfo> inspectInfos) {
    this.id = id;
    this.name = name;
    this.admissionDate = new DateTime(admissionDate);
    this.age = age;
    this.inspectInfos = new ArrayList<InspectInfo>(inspectInfos);
  }

  public long getId() {
    return id;
  }

  public int getInfectDays() {
    if (inspectInfos.isEmpty()) {
      return 0;
    }
    if (inspectInfos.size() == 1) {
      if (StringUtils.isNotBlank(inspectInfos.get(0).getPosition())) {
        return 1;
      }
      return 0;
    }
    Collections.sort(inspectInfos, new Comparator<InspectInfo>() {

      public int compare(InspectInfo o1, InspectInfo o2) {
        return o1.getInspectDate().compareTo(o2.getInspectDate());
      }
    });
    DateTime begin = inspectInfos.get(0).getInspectDate();
    DateTime end = inspectInfos.get(inspectInfos.size() - 1).getInspectDate();
    Days days = Days.daysBetween(begin, end);
    return days.getDays();
  }

  public boolean hasManyPostionInfect() {
    if (inspectInfos.isEmpty()) {
      return false;
    }
    if (inspectInfos.size() == 1) {
      return false;
    }
    Set<String> postions = new HashSet<String>();
    for (InspectInfo inspectInfo : inspectInfos) {
      String position = inspectInfo.getPosition();
      if (StringUtils.isNotBlank(position)) {
        postions.add(position);
      }
    }
    return postions.size() > 1;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAdmissionDate(DateTime admissionDate) {
    this.admissionDate = admissionDate;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public DateTime getAdmissionDate() {
    return new DateTime(admissionDate);
  }

  public List<InspectInfo> getInspectInfos() {
    return inspectInfos;
  }

  public void addInspectInfos(InspectInfo inspectInfo) {
    inspectInfos.add(inspectInfo);
  }

  @Override public String toString() {
    return "Patient{" + "id=" + id + ", name='" + name + '\'' + ", admissionDate=" + admissionDate
        + ", age=" + age + ", inspectInfos=" + inspectInfos + '}';
  }
}
