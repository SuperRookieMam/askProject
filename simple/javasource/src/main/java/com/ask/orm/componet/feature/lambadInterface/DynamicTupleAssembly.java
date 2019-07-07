package com.ask.orm.componet.feature.lambadInterface;

import com.ask.orm.componet.feature.DynamicTupleSelect;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicTupleAssembly<T> {
    public Predicate assembly(DynamicTupleSelect<T> dynamicTupleSelect);
}
