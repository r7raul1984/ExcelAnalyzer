package com.ruijin.convert;

import com.ruijin.model.Meta;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

    public Object converTo(Object rawValue) {
        if (StringUtils.isNotBlank((String) rawValue)) {
            if ("耐药".equalsIgnoreCase((String) rawValue) || "R".equalsIgnoreCase((String) rawValue)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
