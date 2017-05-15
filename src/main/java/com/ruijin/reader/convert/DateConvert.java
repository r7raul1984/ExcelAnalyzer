package com.ruijin.reader.convert;

import org.joda.time.DateTime;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class DateConvert implements Convert {

  public DateTime converTo(Object initValue) {
    return new DateTime(initValue);
  }
}
