package com.ruijin.reader.convert;

import com.ruijin.reader.model.Meta;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class DateConvert implements Convert {


  public Object converTo(List<Cell> cells, String defaultValue) {
    if (cells.size() != 1) {
      return new DateTime(defaultValue);
    }
    Cell cell = cells.get(0);
    if (cell != null) {
      if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
        return this.converTo(cell.getDateCellValue());
      }
    }
    return new DateTime(defaultValue);
  }


  public DateTime converTo(Object initValue) {
    return new DateTime(initValue);
  }
}
