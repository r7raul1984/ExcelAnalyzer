package com.ruijin.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class Metas {
  private List<Meta> metas = new ArrayList<Meta>();

  public List<Meta> getMetas() {
    return metas;
  }

  public void addMeta(Meta meta) {
    metas.add(meta);
  }
}
