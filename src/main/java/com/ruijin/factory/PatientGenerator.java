package com.ruijin.factory;

import com.google.common.collect.Lists;
import com.ruijin.model.*;
import com.ruijin.util.ReflectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class PatientGenerator {

  public List<Patient> makePatients(String filePath, int sheetIndex, int addRow, int headrow,
      List<Meta> pMeta, List<Meta> iMeta, List<Metas> dMeta) throws IOException {
    List<Patient> result = new ArrayList<Patient>();
    FileInputStream excelFile = new FileInputStream(new File(filePath));
    Workbook workbook = new XSSFWorkbook(excelFile);
    Sheet sheet = workbook.getSheetAt(sheetIndex);
    Row headRow = sheet.getRow(headrow);
    for (int rIndex = sheet.getFirstRowNum() + addRow; rIndex <= sheet.getLastRowNum(); rIndex++) {
      Row row = sheet.getRow(rIndex);

      Patient patient = new Patient();
      getPatientInfo(pMeta, row, patient);

      InspectInfo inspectInfo = new InspectInfo();
      getInspectInfo(iMeta, row, inspectInfo);

      for (Metas metas : dMeta) {
        inspectInfo.addDrugfasts(getDrugfastsInfo(metas, headRow, row));
      }
      patient.addInspectInfos(inspectInfo);
      result.add(patient);
    }
    return distinct(result);
  }

  private List<Patient> distinct(List<Patient> patients) {

    if (patients.isEmpty()) {
      return Collections.emptyList();
    }
    Map<String, Patient> keyToPatient = new HashMap<String, Patient>();
    for (Patient p : patients) {
      String id = p.getId();
      if (keyToPatient.containsKey(id)) {
        Patient pFromMap = keyToPatient.get(id);
        pFromMap.getInspectInfos().addAll(p.getInspectInfos());
      } else {
        keyToPatient.put(id, p);
      }
    }
    return Lists.newArrayList(keyToPatient.values());
  }

  private Drugfast getDrugfastsInfo(Metas metas, Row headRow, Row row) {
    Drugfast drugfast = new Drugfast();
    List<Meta> metaList = metas.getMetas();
    for (Meta meta : metaList) {
      List<Integer> cellIndexs = meta.getCellIndexs();
      List<Cell> cells = new ArrayList<Cell>();
      for (int cellIndex : cellIndexs) {
        if (cellIndex > 1000) {
          cells.add(headRow.getCell(cellIndex - 1000));
        } else {
          cells.add(row.getCell(cellIndex));
        }
      }
      setValue(drugfast, meta, cells);
    }
    return drugfast;
  }

  private void getInspectInfo(List<Meta> inspectMeta, Row row, InspectInfo inspectInfo) {
    for (Meta meta : inspectMeta) {
      List<Integer> cellIndexs = meta.getCellIndexs();
      List<Cell> cells = new ArrayList<Cell>();
      for (int cellIndex : cellIndexs) {
        cells.add(row.getCell(cellIndex));
      }
      setValue(inspectInfo, meta, cells);
    }
  }

  private void getPatientInfo(List<Meta> pMeta, Row row, Patient patient) {
    for (Meta meta : pMeta) {
      List<Integer> cellIndexs = meta.getCellIndexs();
      List<Cell> cells = new ArrayList<Cell>();
      for (int cellIndex : cellIndexs) {
        cells.add(row.getCell(cellIndex));
      }
      setValue(patient, meta, cells);
    }
  }

  private void setValue(Object object, Meta meta, List<Cell> cells) {
    Object value = meta.getConvert().converTo(cells, meta.getDefaultValue());
    ReflectionUtils.invokeSetterMethod(object, meta.getFieldName(), value, meta.getFieldClass());
  }

}
