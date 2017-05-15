import com.ruijin.reader.convert.DateConvert;
import com.ruijin.reader.convert.IntegerConvert;
import com.ruijin.reader.convert.StringConvert;
import com.ruijin.reader.factory.PatientGenerator;
import com.ruijin.reader.model.Meta;
import com.ruijin.reader.model.Patient;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class TestConvert {

  private List<Meta> mappingMeta = new ArrayList<Meta>();
  private String filePath;
  private PatientGenerator patientGenerator = new PatientGenerator();

  @Before public void setUp() {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("test.xlsx").getFile());
    filePath = file.getAbsolutePath();
  }

  @Test public void testConvert() throws Exception {
    mappingMeta.add(new Meta("age", 3, Integer.class, new IntegerConvert()));
    mappingMeta.add(new Meta("admissionDate", 2, DateTime.class, new DateConvert()));
    mappingMeta.add(new Meta("id", 1, Integer.class, new IntegerConvert()));
    mappingMeta.add(new Meta("name", 0, String.class, new StringConvert()));

    List<Patient> result = patientGenerator.makePatients(filePath, 0, mappingMeta, 1);
    assertEquals(2, result.size());
    assertEquals(
        "Patient{id=33, name='jiji', admissionDate=2016-12-23T00:00:00.000+08:00, age=23, inspectInfos=[]}",
        result.get(0).toString());
    assertEquals(
        "Patient{id=-1, name='jiji2', admissionDate=1999-10-01T00:00:00.000+08:00, age=-1, inspectInfos=[]}",
        result.get(1).toString());
  }

  @Test public void testConvert1() throws Exception {
    mappingMeta.add(new Meta("age", 5, Integer.class, new IntegerConvert()));
    mappingMeta.add(new Meta("admissionDate", 2, DateTime.class, new DateConvert()));
    mappingMeta.add(new Meta("id", 1, Integer.class, new IntegerConvert()));
    mappingMeta.add(new Meta("name", 0, String.class, new StringConvert()));

    List<Patient> result = patientGenerator.makePatients(filePath, 0, mappingMeta, 1);
    assertEquals(2, result.size());
    assertEquals(
        "Patient{id=33, name='jiji', admissionDate=2016-12-23T00:00:00.000+08:00, age=-1, inspectInfos=[]}",
        result.get(0).toString());
    assertEquals(
        "Patient{id=-1, name='jiji2', admissionDate=1999-10-01T00:00:00.000+08:00, age=-1, inspectInfos=[]}",
        result.get(1).toString());
  }
}
