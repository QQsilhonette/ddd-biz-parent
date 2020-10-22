package org.ddd.biz.platform.framework.ddd;

/**
 * @author wujun
 * 聚合根
 * @param <T>
 */
public interface AggregateRoot<T extends Entity> {

    T getRootEntity();
}
