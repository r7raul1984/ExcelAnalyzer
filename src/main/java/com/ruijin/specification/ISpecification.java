package com.ruijin.specification;

/**
 * Created by tangjijun on 2017/5/15.
 */
public interface ISpecification<T>
{
  boolean isSatisfiedBy(T candidate);
}