package com.ask.orm.componet.feature.lambadInterface;
import com.ask.orm.componet.feature.DynamicPageTupleSelect;

import javax.persistence.criteria.Predicate;

@FunctionalInterface
public interface DynamicPageTupleAssembly<T> {
    public Predicate assembly(DynamicPageTupleSelect<T> dynamicPageTupleSelect);
}
