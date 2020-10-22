package org.ddd.biz.platform.framework.ddd;


public abstract class Aggregate<T extends Entity> {
    private static final long serialVersionUID = 1L;

    protected T aggregateRoot;

    public Aggregate(T aggregateRoot) {
        this.aggregateRoot = aggregateRoot;
    }

    public T getAggregateRoot() {
        return aggregateRoot;
    }

    public abstract void persist();
}
