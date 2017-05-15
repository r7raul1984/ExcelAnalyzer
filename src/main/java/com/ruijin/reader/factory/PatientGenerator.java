package com.ruijin.reader.factory;

import com.ruijin.reader.ApachePOIExcelRead;
import com.ruijin.reader.model.Meta;
import com.ruijin.reader.model.Patient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class PatientGenerator {

  public List<Patient> makePatients(String filePath, int sheetIndex, List<Meta> mappingMeta,
      int addRow)
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    List<Patient> result = new ArrayList<Patient>();
    FileInputStream excelFile = new FileInputStream(new File(filePath));
    Workbook workbook = new XSSFWorkbook(excelFile);
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    for (int rIndex = sheet.getFirstRowNum() + addRow; rIndex <= sheet.getLastRowNum(); rIndex++) {
      Row row = sheet.getRow(rIndex);
      Patient patient = new Patient();
      for (Meta meta : mappingMeta) {
        List<Integer> cellIndexs = meta.getCellIndexs();
        List<Cell> cells = new ArrayList<Cell>();
        for (int cellIndex : cellIndexs) {
          cells.add(row.getCell(cellIndex));
        }
        setValueForPatient(patient, meta, cells);
      }
      result.add(patient);
    }
    return result;
  }

  private void setValueForPatient(Patient patient, Meta meta, List<Cell> cells)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    java.lang.reflect.Method method = patient.getClass()
        .getMethod("set" + WordUtils.capitalize(meta.getFieldName()), meta.getFieldClass());
    method.invoke(patient, meta.getConvert().converTo(cells, meta.getDefaultValue()));
  }
}
