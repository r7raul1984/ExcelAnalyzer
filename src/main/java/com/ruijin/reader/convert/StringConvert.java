package com.ruijin.reader.convert;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class StringConvert implements Convert {

  public String converTo(Object initValue) {
    return StringUtils.trim((String) initValue);
  }
}
