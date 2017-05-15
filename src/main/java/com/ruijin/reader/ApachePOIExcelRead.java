package com.ruijin.reader;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

public class ApachePOIExcelRead {

  private static final String FILE_NAME = "/tmp/MyFirstExcel.xlsx";

  public  void parse(String fileName) {

    try {

      FileInputStream excelFile = new FileInputStream(new File(fileName));
      Workbook workbook = new XSSFWorkbook(excelFile);
      Sheet datatypeSheet = workbook.getSheetAt(0);
      Iterator<Row> iterator = datatypeSheet.iterator();

      while (iterator.hasNext()) {

        Row currentRow = iterator.next();
        Iterator<Cell> cellIterator = currentRow.iterator();

        while (cellIterator.hasNext()) {

          Cell currentCell = cellIterator.next();
          //getCellTypeEnum shown as deprecated for version 3.15
          //getCellTypeEnum ill be renamed to getCellType starting from version 4.0
          if (currentCell.getCellType() == Cell.CELL_TYPE_STRING) {
            System.out.print(currentCell.getStringCellValue() + "--");
          } else if (currentCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            System.out.print(currentCell.getNumericCellValue() + "--");
          }

        }
        System.out.println();

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
