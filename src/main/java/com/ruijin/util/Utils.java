package com.ruijin.util;

import com.ruijin.model.Constant;

public class Utils {

  public static int pointToHeadRow(int cellIndex) {
    return Constant.HEADROW_FLAG + cellIndex;
  }

  public static boolean isPointToHeadRow(int cellIndex) {
    return cellIndex >= Constant.HEADROW_FLAG;
  }

  public static int getRealCellIndex(int cellIndexWithHeadRowFlag) {
    return cellIndexWithHeadRowFlag - Constant.HEADROW_FLAG;
  }

}
