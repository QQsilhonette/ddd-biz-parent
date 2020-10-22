package org.ddd.biz.platform.framework.ddd;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.annotation.Resource;
import java.util.Optional;

public abstract class DomainRepository<T extends Entity> {

    @Resource
    protected AutowireCapableBeanFactory spring;

    protected <E> E autowireEntity(E e) {
        spring.autowireBean(e);
        return e;
    }

    public void setSpring(AutowireCapableBeanFactory spring) {
        this.spring = spring;
    }


    /**
     * 根据id查找实体
     *
     * @param id
     * @return
     */
    public abstract Optional<T> findOneById(Object id);

    /**
     * 是否存在
     *
     * @param t
     * @return
     */
    public abstract boolean exists(T t);

    /**
     * 持久化
     *
     * @param t
     */
    public void persist(T t) {
        if (t.isPersisted()) {
            update(t);
        } else if (t.isRemoved()) {
            delete(t);
        } else {
            add(t);
            t.markAsPersisted();
            this.autowireEntity(t);
        }
    }

    /**
     * 新增
     *
     * @param t
     */
    protected abstract void add(T t);

    /**
     * 更新
     *
     * @param t
     */
    protected abstract void update(T t);

    /**
     * 删除
     *
     * @param t
     */
    protected void delete(T t) {
        throw new UnsupportedOperationException();
    }

}
