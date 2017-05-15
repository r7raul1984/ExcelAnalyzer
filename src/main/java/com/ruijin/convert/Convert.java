package com.ruijin.convert;

import org.apache.poi.ss.usermodel.Cell;

import java.util.List;


public interface Convert {

  Object converTo(List<Cell> cells, String defaultValue);

  Object converTo(Object initValue);

}
