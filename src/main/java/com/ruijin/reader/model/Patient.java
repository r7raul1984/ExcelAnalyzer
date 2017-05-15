package com.ruijin.reader.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class Patient {

  private int id;
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

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public void setId(Integer id) {
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

  public void setInspectInfos(List<InspectInfo> inspectInfos) {
    this.inspectInfos = inspectInfos;
  }

  @Override public String toString() {
    return "Patient{" + "id=" + id + ", name='" + name + '\'' + ", admissionDate=" + admissionDate
        + ", age=" + age + ", inspectInfos=" + inspectInfos + '}';
  }
}
