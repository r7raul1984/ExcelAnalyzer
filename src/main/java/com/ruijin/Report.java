package com.ruijin;

import com.google.common.collect.Lists;
import com.ruijin.convert.BooleanConvert;
import com.ruijin.convert.DateConvert;
import com.ruijin.convert.IntegerConvert;
import com.ruijin.convert.StringConvert;
import com.ruijin.factory.PatientGenerator;
import com.ruijin.model.Meta;
import com.ruijin.model.Metas;
import com.ruijin.model.Patient;
import com.ruijin.specification.ISpecification;
import com.ruijin.specification.SpecYear2014;
import org.joda.time.DateTime;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Report {

  private static Map<String, ISpecification> yearToSpec = new HashMap<String, ISpecification>();
  private static Map<String, List<Meta>> yearToPMeta = new HashMap<String, List<Meta>>();
  private static Map<String, List<Meta>> yearToIMeta = new HashMap<String, List<Meta>>();
  private static Map<String, List<Metas>> yearToDMeta = new HashMap<String, List<Metas>>();
  private static Map<String, String> yearToFilePath = new HashMap<String, String>();
  private static Map<String, Integer> yearToHeadRow = new HashMap<String, Integer>();
  private static Map<String, Integer> yearToAddRow = new HashMap<String, Integer>();

  static {
    Pepare2014Meta pepare2014Meta = new Pepare2014Meta().invoke();
    List<Meta> pMeta = pepare2014Meta.getpMeta();
    List<Meta> iMeta = pepare2014Meta.getiMeta();
    List<Metas> dMetas = pepare2014Meta.getdMetas();
    pepare("2014", pMeta, iMeta, dMetas, new SpecYear2014(), 0, 1, "C://");

   /* Pepare2015Meta pepare2015Meta = new Pepare2015Meta().invoke();
    pMeta = pepare2015Meta.getpMeta();
    iMeta = pepare2015Meta.getiMeta();
    dMetas = pepare2015Meta.getdMetas();
    pepare("2015", pMeta, iMeta, dMetas, new SpecYear2015(), 0, 1, "C://");*/

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
    String[] years = new String[] { "2014" };
    PatientGenerator patientGenerator = new PatientGenerator();
    for (String year : years) {
      System.out.println("year:  "+year);
      String filePath = yearToFilePath.get(year);
      List<Meta> pMeta = yearToPMeta.get(year);
      List<Meta> iMeta = yearToIMeta.get(year);
      List<Metas> dMetas = yearToDMeta.get(year);
      List<Patient> result = patientGenerator.makePatients(filePath, 0, 1, 0, pMeta, iMeta, dMetas);
      ISpecification spec = yearToSpec.get(year);
      List<String> reportRs = new ArrayList<String>();
      for (Patient p : result) {
        spec.isSatisfiedBy(p);
        reportRs.add("");
      }
      for (String eachRs : reportRs) {
        System.out.println(eachRs);
      }
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
      pMeta.add(new Meta("age", Lists.newArrayList(3), Integer.class, new IntegerConvert()));
      pMeta
          .add(new Meta("admissionDate", Lists.newArrayList(2), DateTime.class, new DateConvert()));
      pMeta.add(new Meta("id", Lists.newArrayList(1), Integer.class, new IntegerConvert()));
      pMeta.add(new Meta("name", Lists.newArrayList(0), String.class, new StringConvert()));

      iMeta.add(new Meta("grem", Lists.newArrayList(6), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectType", Lists.newArrayList(5), String.class, new StringConvert()));
      iMeta.add(new Meta("inspectDate", Lists.newArrayList(4), DateTime.class, new DateConvert()));

      Metas metas = new Metas();
      metas.addMeta(
          new Meta("drugName", Lists.newArrayList(1007), String.class, new StringConvert()));
      metas.addMeta(new Meta("isfast", Lists.newArrayList(7), Boolean.class, new BooleanConvert()));
      Metas metas1 = new Metas();
      metas1.addMeta(
          new Meta("drugName", Lists.newArrayList(1008), String.class, new StringConvert()));
      metas1
          .addMeta(new Meta("isfast", Lists.newArrayList(8), Boolean.class, new BooleanConvert()));
      dMetas.add(metas);
      dMetas.add(metas1);
      return this;
    }
  }
}
