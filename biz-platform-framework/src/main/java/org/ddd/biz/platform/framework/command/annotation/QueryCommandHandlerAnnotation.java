package org.ddd.biz.platform.framework.command.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 被{@link QueryCommandHandlerAnnotation}注解的方法,通过设置{@link #value()}作为锁,在被调用时会被同步,可以解决分布式并发系列的问题。
 *
 * @author linshouhua
 * @date 2018/5/8
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QueryCommandHandlerAnnotation {

    String value() default "";
}
