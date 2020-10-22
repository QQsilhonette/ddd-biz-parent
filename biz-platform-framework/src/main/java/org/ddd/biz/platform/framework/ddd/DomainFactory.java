package org.ddd.biz.platform.framework.ddd;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

import javax.annotation.Resource;

public abstract class DomainFactory  {

    @Resource
    protected AutowireCapableBeanFactory spring;

    protected <T> T autowireEntity(T t) {
        spring.autowireBean(t);
        return t;
    }

    public void setSpring(AutowireCapableBeanFactory spring) {
        this.spring = spring;
    }

}
