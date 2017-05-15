package com.ruijin.convert;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class StringConvert implements Convert {

  public Object converTo(List<Cell> cells, String defaultValue) {
    if (cells.size() != 1) {
      return new Integer(defaultValue);
    }
    Cell cell = cells.get(0);
    if (cell != null) {
      if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
        return this.converTo(cell.getStringCellValue());
      }
    }
    return new String(defaultValue);
  }


  public String converTo(Object initValue) {
    return StringUtils.trim((String) initValue);
  }
}
