package com.ruijin.convert;

import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public interface Convert {

  Object converTo(List<Cell> cells, String defaultValue);

  Object converTo(Object initValue);

}
