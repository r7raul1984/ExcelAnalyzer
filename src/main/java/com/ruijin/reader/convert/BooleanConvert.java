package com.ruijin.reader.convert;

import com.ruijin.reader.model.Meta;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class BooleanConvert implements Convert {

  public Object converTo(Row row, List<Meta> metas)
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    return null;
  }

  public Object converTo(List<Cell> cells, String defaultValue) {
    if (cells.size() != 1) {
      return Boolean.valueOf(defaultValue);
    }
    Cell cell = cells.get(0);
    if (cell != null) {
      if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
        return this.converTo(cell.getStringCellValue());
      }
    }
    return Boolean.valueOf(defaultValue);
  }

  public Object converTo(Object initValue) {
    if (StringUtils.isNotBlank((String) initValue)) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }
}
