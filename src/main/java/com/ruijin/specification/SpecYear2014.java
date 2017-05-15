package com.ruijin.specification;

import com.ruijin.model.Patient;

public class SpecYear2014 implements ISpecification<Patient> {

  public boolean isSatisfiedBy(Patient patient) {
    return false;
  }
}
