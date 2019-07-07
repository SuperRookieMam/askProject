package com.ask.orm.componet.feature.lambadInterface;

import com.ask.orm.componet.feature.DynamicTypeSelect;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicTypeAssembly<T>  {
    public Predicate assembly(DynamicTypeSelect<T> dynamicTypeSelect);
}
