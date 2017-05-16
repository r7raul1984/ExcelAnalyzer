package com.ruijin;

import com.google.common.collect.Lists;
import com.ruijin.convert.BooleanConvert;
import com.ruijin.convert.DateConvert;
import com.ruijin.convert.IntegerConvert;
import com.ruijin.convert.StringConvert;
import com.ruijin.factory.PatientGenerator;
import com.ruijin.model.*;
import com.ruijin.specification.ISpecification;
import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Report {

  public static final String GREM_NAME = "肺炎克雷伯菌";
  private static Map<String, ISpecification> yearToSpec = new HashMap<String, ISpecification>();
  private static Map<String, List<Meta>> yearToPMeta = new HashMap<String, List<Meta>>();
  private static Map<String, List<Meta>> yearToIMeta = new HashMap<String, List<Meta>>();
  private static Map<String, List<Metas>> yearToDMeta = new HashMap<String, List<Metas>>();
  private static Map<String, String> yearToFilePath = new HashMap<String, String>();
  private static Map<String, Integer> yearToHeadRow = new HashMap<String, Integer>();
  private static Map<String, Integer> yearToAddRow = new HashMap<String, Integer>();

  static {
    Pepare2016Meta pepare2016Meta = new Pepare2016Meta().invoke();
    List<Meta> pMeta = pepare2016Meta.getpMeta();
    List<Meta> iMeta = pepare2016Meta.getiMeta();
    List<Metas> dMetas = pepare2016Meta.getdMetas();
    pepare("2016", pMeta, iMeta, dMetas, null, 0, 1,
        "C:\\Users\\Administrator\\Desktop\\2016年汇总.xlsx");

    Pepare2015Meta pepare2015Meta = new Pepare2015Meta().invoke();
    pMeta = pepare2015Meta.getpMeta();
    iMeta = pepare2015Meta.getiMeta();
    dMetas = pepare2015Meta.getdMetas();
    pepare("2015", pMeta, iMeta, dMetas, null, 4, 5,
        "C:\\Users\\Administrator\\Desktop\\2015年汇总.xlsx");

    Pepare2014Meta pepare2014Meta = new Pepare2014Meta().invoke();
    pMeta = pepare2014Meta.getpMeta();
    iMeta = pepare2014Meta.getiMeta();
    dMetas = pepare2014Meta.getdMetas();
    pepare("2014", pMeta, iMeta, dMetas, null, 4, 5,
        "C:\\Users\\Administrator\\Desktop\\2014全年.xlsx");

    Pepare2013Meta pepare2013Meta = new Pepare2013Meta().invoke();
    pMeta = pepare2013Meta.getpMeta();
    iMeta = pepare2013Meta.getiMeta();
    dMetas = pepare2013Meta.getdMetas();
    pepare("2013", pMeta, iMeta, dMetas, null, 0, 1,
        "C:\\Users\\Administrator\\Desktop\\2013.xlsx");

  }

  private static void pepare(String year, List<Meta> pMeta, List<Meta> iMeta, List<Metas> dMetas,
      ISpecification spec, int headRow, int addRow, String filePath) {
    yearToSpec.put(year, spec);
    yearToPMeta.put(year, pMeta);
    yearToIMeta.put(year, iMeta);
    yearToDMeta.put(year, dMetas);
    yearToHeadRow.put(year, headRow);
    yearToAddRow.put(year, addRow);
    yearToFilePath.put(year, filePath);
  }

  public static void main(String args[])
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {
    String[] years = new String[] { "2016", "2015", "2014", "2013" };
    PatientGenerator patientGenerator = new PatientGenerator();
    for (String year : years) {
      System.out.println("year:  " + year);
      String filePath = yearToFilePath.get(year);
      List<Meta> pMeta = yearToPMeta.get(year);
      List<Meta> iMeta = yearToIMeta.get(year);
      List<Metas> dMetas = yearToDMeta.get(year);
      List<Patient> result = patientGenerator
          .makePatients(filePath, 0, yearToAddRow.get(year), yearToHeadRow.get(year), pMeta, iMeta,
              dMetas);

      List<Patient> filteredP = new ArrayList<Patient>();
      for (Patient p : result) {
        List<InspectInfo> inspectInfos = p.getInspectInfos();
        List<InspectInfo> newinspectInfos = new ArrayList<InspectInfo>(inspectInfos.size());
        for (InspectInfo inspectInfo : inspectInfos) {
          if (GREM_NAME.equals(inspectInfo.getGrem())) {
            List<Drugfast> drugfasts = inspectInfo.getDrugfasts();
            for (Drugfast drugfast : drugfasts) {
              if (drugfast.isIsfast()) {
                newinspectInfos.add(inspectInfo);
                break;
              }
            }
          }
        }
        if (!newinspectInfos.isEmpty()) {
          p.setInspectInfos(newinspectInfos);
          filteredP.add(p);
        }
      }
      int count = 0;
      int days = 0;
      for (Patient eachP : filteredP) {
        System.out.println(eachP.getId() + " " + eachP.getName());
        if (eachP.hasManyPostionInfect()) {
          count++;
        }
        days += eachP.getInfectDays();
      }
      List<String> reportRs = new ArrayList<String>();
      reportRs.add(count + "");
      reportRs.add(days + "");
      for (String rs : reportRs) {
        System.out.println(rs);
      }
    }
  }

  private static class Pepare2016Meta {

    private List<Meta> pMeta;
    private List<Meta> iMeta;
    private List<Metas> dMetas;

    public List<Meta> getpMeta() {
      return pMeta;
    }

    public List<Meta> getiMeta() {
      return iMeta;
    }

    public List<Metas> getdMetas() {
      return dMetas;
    }

    public Pepare2016Meta invoke() {
      pMeta = new ArrayList<Meta>();
      iMeta = new ArrayList<Meta>();
      dMetas = new ArrayList<Metas>();
      pMeta.add(new Meta("age", Lists.newArrayList(1), Integer.class, new IntegerConvert()));
      pMeta
          .add(new Meta("admissionDate", Lists.newArrayList(8), DateTime.class, new DateConvert()));
      pMeta.add(new Meta("id", Lists.newArrayList(23), String.class, new StringConvert()));
      pMeta.add(new Meta("name", Lists.newArrayList(11), String.class, new StringConvert()));

      iMeta.add(new Meta("grem", Lists.newArrayList(18), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectType", Lists.newArrayList(5), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectDate", Lists.newArrayList(15), DateTime.class, new DateConvert()));
      iMeta.add(new Meta("position", Lists.newArrayList(16), String.class, new StringConvert()));

      Metas metas = new Metas();
      metas.addMeta(
          new Meta("drugName", Lists.newArrayList(1054), String.class, new StringConvert()));
      metas
          .addMeta(new Meta("isfast", Lists.newArrayList(54), Boolean.class, new BooleanConvert()));
      Metas metas1 = new Metas();
      metas1.addMeta(
          new Meta("drugName", Lists.newArrayList(1066), String.class, new StringConvert()));
      metas1
          .addMeta(new Meta("isfast", Lists.newArrayList(66), Boolean.class, new BooleanConvert()));
      dMetas.add(metas);
      dMetas.add(metas1);
      return this;
    }
  }

  private static class Pepare2015Meta {

    private List<Meta> pMeta;
    private List<Meta> iMeta;
    private List<Metas> dMetas;

    public List<Meta> getpMeta() {
      return pMeta;
    }

    public List<Meta> getiMeta() {
      return iMeta;
    }

    public List<Metas> getdMetas() {
      return dMetas;
    }

    public Pepare2015Meta invoke() {
      pMeta = new ArrayList<Meta>();
      iMeta = new ArrayList<Meta>();
      dMetas = new ArrayList<Metas>();
      pMeta.add(new Meta("age", Lists.newArrayList(1), Integer.class, new IntegerConvert()));
      pMeta
          .add(new Meta("admissionDate", Lists.newArrayList(8), DateTime.class, new DateConvert()));
      pMeta.add(new Meta("id", Lists.newArrayList(12), String.class, new StringConvert()));
      pMeta.add(new Meta("name", Lists.newArrayList(11), String.class, new StringConvert()));

      iMeta.add(new Meta("grem", Lists.newArrayList(19), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectType", Lists.newArrayList(5), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectDate", Lists.newArrayList(16), DateTime.class, new DateConvert()));
      iMeta.add(new Meta("position", Lists.newArrayList(17), String.class, new StringConvert()));

      Metas metas = new Metas();
      metas.addMeta(
          new Meta("drugName", Lists.newArrayList(1037), String.class, new StringConvert()));
      metas
          .addMeta(new Meta("isfast", Lists.newArrayList(37), Boolean.class, new BooleanConvert()));
      Metas metas1 = new Metas();
      metas1.addMeta(
          new Meta("drugName", Lists.newArrayList(1038), String.class, new StringConvert()));
      metas1
          .addMeta(new Meta("isfast", Lists.newArrayList(38), Boolean.class, new BooleanConvert()));
      dMetas.add(metas);
      dMetas.add(metas1);
      return this;
    }
  }

  private static class Pepare2014Meta {

    private List<Meta> pMeta;
    private List<Meta> iMeta;
    private List<Metas> dMetas;

    public List<Meta> getpMeta() {
      return pMeta;
    }

    public List<Meta> getiMeta() {
      return iMeta;
    }

    public List<Metas> getdMetas() {
      return dMetas;
    }

    public Pepare2014Meta invoke() {
      pMeta = new ArrayList<Meta>();
      iMeta = new ArrayList<Meta>();
      dMetas = new ArrayList<Metas>();
      pMeta.add(new Meta("age", Lists.newArrayList(1), Integer.class, new IntegerConvert()));
      pMeta
          .add(new Meta("admissionDate", Lists.newArrayList(7), DateTime.class, new DateConvert()));
      pMeta.add(new Meta("id", Lists.newArrayList(11), String.class, new StringConvert()));
      pMeta.add(new Meta("name", Lists.newArrayList(10), String.class, new StringConvert()));

      iMeta.add(new Meta("grem", Lists.newArrayList(18), String.class, new StringConvert()));
      iMeta
          .add(new Meta("inspectType", Lists.newArrayList(100), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectDate", Lists.newArrayList(15), DateTime.class, new DateConvert()));
      iMeta.add(new Meta("position", Lists.newArrayList(16), String.class, new StringConvert()));

      Metas metas = new Metas();
      metas.addMeta(
          new Meta("drugName", Lists.newArrayList(1036), String.class, new StringConvert()));
      metas
          .addMeta(new Meta("isfast", Lists.newArrayList(36), Boolean.class, new BooleanConvert()));
      Metas metas1 = new Metas();
      metas1.addMeta(
          new Meta("drugName", Lists.newArrayList(1037), String.class, new StringConvert()));
      metas1
          .addMeta(new Meta("isfast", Lists.newArrayList(37), Boolean.class, new BooleanConvert()));
      dMetas.add(metas);
      dMetas.add(metas1);
      return this;
    }
  }

  private static class Pepare2013Meta {

    private List<Meta> pMeta;
    private List<Meta> iMeta;
    private List<Metas> dMetas;

    public List<Meta> getpMeta() {
      return pMeta;
    }

    public List<Meta> getiMeta() {
      return iMeta;
    }

    public List<Metas> getdMetas() {
      return dMetas;
    }

    public Pepare2013Meta invoke() {
      pMeta = new ArrayList<Meta>();
      iMeta = new ArrayList<Meta>();
      dMetas = new ArrayList<Metas>();
      pMeta.add(new Meta("age", Lists.newArrayList(1), Integer.class, new IntegerConvert()));
      pMeta
          .add(new Meta("admissionDate", Lists.newArrayList(7), DateTime.class, new DateConvert()));
      pMeta.add(new Meta("id", Lists.newArrayList(11), String.class, new StringConvert()));
      pMeta.add(new Meta("name", Lists.newArrayList(10), String.class, new StringConvert()));

      iMeta.add(new Meta("grem", Lists.newArrayList(18), String.class, new StringConvert()));
      iMeta
          .add(new Meta("inspectType", Lists.newArrayList(100), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectDate", Lists.newArrayList(15), DateTime.class, new DateConvert()));
      iMeta.add(new Meta("position", Lists.newArrayList(16), String.class, new StringConvert()));

      Metas metas = new Metas();
      metas.addMeta(
          new Meta("drugName", Lists.newArrayList(1036), String.class, new StringConvert()));
      metas
          .addMeta(new Meta("isfast", Lists.newArrayList(36), Boolean.class, new BooleanConvert()));
      Metas metas1 = new Metas();
      metas1.addMeta(
          new Meta("drugName", Lists.newArrayList(1037), String.class, new StringConvert()));
      metas1
          .addMeta(new Meta("isfast", Lists.newArrayList(37), Boolean.class, new BooleanConvert()));
      dMetas.add(metas);
      dMetas.add(metas1);
      return this;
    }
  }
}
