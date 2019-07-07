package com.ask.orm.componet.feature.lambadInterface;

import com.ask.orm.componet.feature.DynamicPageTypeSelect;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicPageTypeAssembly<T> {
    public Predicate assembly(DynamicPageTypeSelect<T> dynamicPageTypeSelect);
}
