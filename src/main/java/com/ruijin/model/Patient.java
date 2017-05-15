package com.ruijin.model;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.*;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class Patient {

  private long id;//用住院号
  private String name;
  private DateTime admissionDate;
  private int age;

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
