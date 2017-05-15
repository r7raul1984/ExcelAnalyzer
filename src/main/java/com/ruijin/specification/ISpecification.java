package com.ruijin.specification;

public interface ISpecification<T> {

  boolean isSatisfiedBy(T candidate);
}