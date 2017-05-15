package com.ruijin.factory;

import com.ruijin.model.*;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

  public List<Patient> makePatients(String filePath, int sheetIndex, int addRow, int headrow,
      List<Meta> pMeta, List<Meta> iMeta, List<Metas> dMeta)
      throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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
    return result;
  }

  private Drugfast getDrugfastsInfo(Metas metas, Row headRow, Row row)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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
      setValueForDrugfast(drugfast, meta, cells);
    }
    return drugfast;
  }

  private void setValueForDrugfast(Drugfast drugfast, Meta meta, List<Cell> cells)
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    java.lang.reflect.Method method = drugfast.getClass()
        .getMethod("set" + WordUtils.capitalize(meta.getFieldName()), meta.getFieldClass());
    method.invoke(drugfast, meta.getConvert().converTo(cells, meta.getDefaultValue()));
  }

  private void getInspectInfo(List<Meta> inspectMeta, Row row, InspectInfo inspectInfo)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    for (Meta meta : inspectMeta) {
      List<Integer> cellIndexs = meta.getCellIndexs();
      List<Cell> cells = new ArrayList<Cell>();
      for (int cellIndex : cellIndexs) {
        cells.add(row.getCell(cellIndex));
      }
      setValueForInspectInfo(inspectInfo, meta, cells);
    }
  }

  private void getPatientInfo(List<Meta> pMeta, Row row, Patient patient)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    for (Meta meta : pMeta) {
      List<Integer> cellIndexs = meta.getCellIndexs();
      List<Cell> cells = new ArrayList<Cell>();
      for (int cellIndex : cellIndexs) {
        cells.add(row.getCell(cellIndex));
      }
      setValueForPatient(patient, meta, cells);
    }
  }

  private void setValueForInspectInfo(InspectInfo inspectInfo, Meta meta, List<Cell> cells)
      throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
    java.lang.reflect.Method method = inspectInfo.getClass()
        .getMethod("set" + WordUtils.capitalize(meta.getFieldName()), meta.getFieldClass());
    method.invoke(inspectInfo, meta.getConvert().converTo(cells, meta.getDefaultValue()));
  }

  private void setValueForPatient(Patient patient, Meta meta, List<Cell> cells)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    java.lang.reflect.Method method = patient.getClass()
        .getMethod("set" + WordUtils.capitalize(meta.getFieldName()), meta.getFieldClass());
    method.invoke(patient, meta.getConvert().converTo(cells, meta.getDefaultValue()));
  }
}
