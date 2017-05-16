import com.google.common.collect.Lists;
import com.ruijin.convert.*;
import com.ruijin.factory.PatientGenerator;
import com.ruijin.model.Meta;
import com.ruijin.model.Metas;
import com.ruijin.model.Patient;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestConvert {

  private List<Meta> pMeta = new ArrayList<Meta>();
  private List<Meta> iMeta = new ArrayList<Meta>();
  private List<Metas> dMetas = new ArrayList<Metas>();
  private String filePath;
  private PatientGenerator patientGenerator = new PatientGenerator();

  @Before public void setUp() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("test.xlsx").getFile());
    filePath = file.getAbsolutePath();
  }

  @Test public void testConvert() throws Exception {
    pMeta.add(new Meta("age", Lists.newArrayList(3), Integer.class, new IntegerConvert()));
    pMeta.add(new Meta("admissionDate", Lists.newArrayList(2), DateTime.class, new DateConvert()));
    pMeta.add(new Meta("id", Lists.newArrayList(1), String.class, new StringConvert()));
    pMeta.add(new Meta("name", Lists.newArrayList(0), String.class, new StringConvert()));

    iMeta.add(new Meta("grem", Lists.newArrayList(6), String.class, new StringConvert()));
    iMeta.add(new Meta("position", Lists.newArrayList(9), String.class, new StringConvert()));

    iMeta.add(new Meta("inspectType", Lists.newArrayList(5), String.class, new StringConvert()));
    iMeta.add(new Meta("inspectDate", Lists.newArrayList(4), DateTime.class, new DateConvert()));

    Metas metas = new Metas();
    metas
        .addMeta(new Meta("drugName", Lists.newArrayList(1007), String.class, new StringConvert()));
    metas.addMeta(new Meta("isfast", Lists.newArrayList(7), Boolean.class, new BooleanConvert()));
    Metas metas1 = new Metas();
    metas1
        .addMeta(new Meta("drugName", Lists.newArrayList(1008), String.class, new StringConvert()));
    metas1.addMeta(new Meta("isfast", Lists.newArrayList(8), Boolean.class, new BooleanConvert()));
    dMetas.add(metas);
    dMetas.add(metas1);

    List<Patient> result = patientGenerator.makePatients(filePath, 0, 1, 0, pMeta, iMeta, dMetas);
    assertEquals(3, result.size());
    assertEquals(
        "Patient{id=, name='jiji2', admissionDate=1999-10-01T00:00:00.000+08:00, age=-1, inspectInfos=[InspectInfo{inspectDate=1999-10-01T00:00:00.000+08:00, inspectType='', grem='', position='', drugfasts=[Drugfast{drugName='药1', isfast=false}, Drugfast{drugName='药2', isfast=false}]}]}",
        result.get(0).toString());
    assertEquals(false, result.get(0).hasManyPostionInfect());
    assertEquals(0, result.get(0).getInfectDays());
    assertEquals(
        "Patient{id=z495256, name='jiji', admissionDate=2016-12-23T00:00:00.000+08:00, age=23, inspectInfos=[InspectInfo{inspectDate=2014-12-23T00:00:00.000+08:00, inspectType='3,4', grem='牛逼', position='胸部', drugfasts=[Drugfast{drugName='药1', isfast=true}, Drugfast{drugName='药2', isfast=false}]}, InspectInfo{inspectDate=2015-12-23T00:00:00.000+08:00, inspectType='3,4', grem='牛逼2', position='鸡巴', drugfasts=[Drugfast{drugName='药1', isfast=false}, Drugfast{drugName='药2', isfast=true}]}]}" ,
        result.get(1).toString());
    assertEquals(true, result.get(1).hasManyPostionInfect());
    assertEquals(365, result.get(1).getInfectDays());
    assertEquals(
        "Patient{id=z495257, name='jiji3', admissionDate=2017-08-23T00:00:00.000+08:00, age=25, inspectInfos=[InspectInfo{inspectDate=2013-12-23T00:00:00.000+08:00, inspectType='5', grem='牛逼3', position='鸡巴', drugfasts=[Drugfast{drugName='药1', isfast=true}, Drugfast{drugName='药2', isfast=true}]}]}",
        result.get(2).toString());
    assertEquals(false, result.get(2).hasManyPostionInfect());
    assertEquals(1, result.get(2).getInfectDays());
  }

  @Test public void testConvert1() throws Exception {
    pMeta.add(new Meta("age", Lists.newArrayList(5), Integer.class, new IntegerConvert()));
    pMeta.add(new Meta("admissionDate", Lists.newArrayList(2), DateTime.class, new DateConvert()));
    pMeta.add(new Meta("id", Lists.newArrayList(1), String.class, new StringConvert()));
    pMeta.add(new Meta("name", Lists.newArrayList(0), String.class, new StringConvert()));

    List<Patient> result = patientGenerator.makePatients(filePath, 0, 1, 0, pMeta, iMeta, dMetas);
    assertEquals(3, result.size());
    assertEquals(
        "Patient{id=, name='jiji2', admissionDate=1999-10-01T00:00:00.000+08:00, age=-1, inspectInfos=[InspectInfo{inspectDate=null, inspectType='', grem='', position='', drugfasts=[]}]}",
        result.get(0).toString());
    assertEquals(false, result.get(0).hasManyPostionInfect());
    assertEquals(0, result.get(0).getInfectDays());
    assertEquals(
        "Patient{id=z495256, name='jiji', admissionDate=2016-12-23T00:00:00.000+08:00, age=-1, inspectInfos=[InspectInfo{inspectDate=null, inspectType='', grem='', position='', drugfasts=[]}, InspectInfo{inspectDate=null, inspectType='', grem='', position='', drugfasts=[]}]}",
        result.get(1).toString());
    assertEquals(false, result.get(1).hasManyPostionInfect());
    assertEquals(0, result.get(1).getInfectDays());
    assertEquals(
        "Patient{id=z495257, name='jiji3', admissionDate=2017-08-23T00:00:00.000+08:00, age=5, inspectInfos=[InspectInfo{inspectDate=null, inspectType='', grem='', position='', drugfasts=[]}]}",
        result.get(2).toString());
    assertEquals(false, result.get(2).hasManyPostionInfect());
    assertEquals(0, result.get(2).getInfectDays());

  }
}
