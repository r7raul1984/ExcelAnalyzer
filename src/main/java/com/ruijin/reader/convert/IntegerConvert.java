package com.ruijin.reader.convert;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class IntegerConvert implements Convert {

  public Integer converTo(Object initValue) {
    return ((Double)initValue).intValue();
  }
}
