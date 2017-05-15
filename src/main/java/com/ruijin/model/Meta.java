package com.ruijin.model;

import com.ruijin.convert.Convert;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class Meta {

  private String fieldName = "";
  private int type = Constant.TYPE_STRING;
  private String defaultValue = "";
  private List<Integer> cellIndexs = new ArrayList<Integer>();
  private Class fieldClass;
  private Convert convert;

  public Meta(String fieldName, List<Integer> cellIndexs, Class fieldClass, Convert convert) {
    this.fieldName = fieldName;
    this.cellIndexs = new ArrayList<Integer>(cellIndexs);
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
    }else if (fieldClass == Boolean.class) {
      this.defaultValue = "false";
      this.type = Constant.TYPE_BOOLEAN;
    } else if (fieldClass == Long.class) {
      this.defaultValue = "-1";
      this.type = Constant.TYPE_LONG;
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

  public List<Integer> getCellIndexs() {
    return cellIndexs;
  }

  public Class getFieldClass() {
    return fieldClass;
  }
}
