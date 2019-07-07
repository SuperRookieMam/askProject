package com.ask.orm.componet.feature.lambadInterface;

import com.ask.orm.componet.feature.DynamicUpdate;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicUpdateAssembly<T> {
    public Predicate assembly(DynamicUpdate<T> DynamicUpdate);
}
