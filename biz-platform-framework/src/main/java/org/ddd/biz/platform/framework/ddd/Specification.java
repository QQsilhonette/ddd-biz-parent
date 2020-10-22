package org.ddd.biz.platform.framework.ddd;

import java.util.function.Predicate;

public abstract class Specification<T extends Entity> {
    private final Predicate<T> predicate;

    public Specification(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    boolean isMatch(T entity) {
        return predicate.test(entity);
    }
    
}
