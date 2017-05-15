package com.ruijin.specification;

import com.ruijin.model.Patient;

/**
 * Created by tangjijun on 2017/5/15.
 */
public class SpecYear2014 implements ISpecification<Patient>{

  public boolean isSatisfiedBy(Patient patient) {
    return false;
  }
}
