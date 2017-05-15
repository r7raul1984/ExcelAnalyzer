package com.ruijin.reader.model;

import com.ruijin.reader.convert.Convert;
import org.joda.time.DateTime;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class Meta {

  private String fieldName = "";
  private int type = Constant.TYPE_STRING;
  private String defaultValue = "";
  private int cellIndex = 0;
  private Class fieldClass;
  private Convert convert;

  public Meta(String fieldName, int cellIndex, Class fieldClass, Convert convert) {
    this.fieldName = fieldName;
    this.cellIndex = cellIndex;
    this.fieldClass = fieldClass;
    this.convert = convert;
    makeTypeInfo(fieldClass);
  }

  private void makeTypeInfo(Class fieldClass) {
    if (fieldClass == Integer.class) {
      this.defaultValue = "-1";
      this.type = Constant.TYPE_NUMERIC;
    } else if (fieldClass == DateTime.class) {
      this.defaultValue = "1999-10-1";
      this.type = Constant.TYPE_DATE;
    } else if (fieldClass == String.class) {
      this.defaultValue = "";
      this.type = Constant.TYPE_STRING;
    } else {
      this.defaultValue = "UNKNOW";
      this.type = Constant.TYPE_UNKNOW;
    }
  }

  public Convert getConvert() {
    return convert;
  }
  public String getFieldName() {
    return fieldName;
  }

  public int getType() {
    return type;
  }

  public String getDefaultValue() {
    return defaultValue;
  }

  public int getCellIndex() {
    return cellIndex;
  }

  public Class getFieldClass() {
    return fieldClass;
  }
}
