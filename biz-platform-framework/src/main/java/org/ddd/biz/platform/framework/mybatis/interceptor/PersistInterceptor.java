package org.ddd.biz.platform.framework.mybatis.interceptor;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.ddd.biz.platform.framework.dao.AbstractDataObject;
import org.ddd.biz.platform.framework.mybatis.annotation.*;
import org.ddd.biz.platform.framework.security.SecurityContextUtils;

import java.lang.reflect.Field;
import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@Slf4j
@Intercepts(
        {
                @Signature(type = Executor.class,
                        method = "update",
                        args = {MappedStatement.class, Object.class}
                )
        }
)
public class PersistInterceptor implements Interceptor {
    private final LoadingCache<Class, Optional<Field>> gmtCreateFieldLoadCache;
    private final LoadingCache<Class, Optional<Field>> gmtModifiedFieldLoadCache;
    private final LoadingCache<Class, Optional<Field>> createdByFieldLoadCache;
    private final LoadingCache<Class, Optional<Field>> lastModifiedByFieldLoadCache;
    private final LoadingCache<Class, Optional<Field>> isDeleteFieldLoadCache;

    public PersistInterceptor() {
        this.gmtCreateFieldLoadCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterAccess(Duration.ofMinutes(15))
                .build(this::getGmtCreateField);
        this.createdByFieldLoadCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterAccess(Duration.ofMinutes(15))
                .build(this::getCreatedByField);
        this.gmtModifiedFieldLoadCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterAccess(Duration.ofMinutes(15))
                .build(this::getGmtModifiedField);
        this.lastModifiedByFieldLoadCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterAccess(Duration.ofMinutes(15))
                .build(this::getLastModifiedByField);
        this.isDeleteFieldLoadCache = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10000)
                .expireAfterAccess(Duration.ofMinutes(15))
                .build(this::getIsDeleteField);
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];
        if (SqlCommandType.INSERT == ms.getSqlCommandType()) {
            Object insertDataObject = args[1];
            populateForInsertParam(insertDataObject);
        }
        if (SqlCommandType.UPDATE == ms.getSqlCommandType()) {
            Object updateDataObject = args[1];
            populateForUpdateParam(updateDataObject);
        }
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }


    private void populateForInsertParam(Object param) {
        if (param instanceof AbstractDataObject) {
            populateForNewDataObject((AbstractDataObject) param);
        } else if (param.getClass().isArray()) {
            Object[] insertDataObjects = (Object[]) param;
            if (ArrayUtils.isNotEmpty(insertDataObjects)) {
                for (Object insertDataObject : insertDataObjects) {
                    if (insertDataObject instanceof AbstractDataObject) {
                        populateForNewDataObject((AbstractDataObject) insertDataObject);
                    }

                }
            }
        } else if (param instanceof Iterable) {
            Iterable iterable = (Iterable) param;
            iterable.forEach(dataObject -> {
                if (dataObject instanceof AbstractDataObject) {
                    populateForNewDataObject((AbstractDataObject) dataObject);
                }
            });
        } else if (param instanceof Map) {
            Map paramMap = (Map) param;
            if (MapUtils.isEmpty(paramMap)) {
                return;
            }
            paramMap.values().forEach(this::populateForInsertParam);
        }
    }

    private void populateForUpdateParam(Object param) {
        if (param instanceof AbstractDataObject) {
            populateForUpdateDataObject((AbstractDataObject) param);
        } else if (param.getClass().isArray()) {
            Object[] insertDataObjects = (Object[]) param;
            if (ArrayUtils.isNotEmpty(insertDataObjects)) {
                for (Object insertDataObject : insertDataObjects) {
                    if (insertDataObject instanceof AbstractDataObject) {
                        populateForUpdateDataObject((AbstractDataObject) insertDataObject);
                    }

                }
            }
        } else if (param instanceof Iterable) {
            Iterable iterable = (Iterable) param;
            iterable.forEach(dataObject -> {
                if (dataObject instanceof AbstractDataObject) {
                    populateForUpdateDataObject((AbstractDataObject) dataObject);
                }
            });
        } else if (param instanceof Map) {
            Map paramMap = (Map) param;
            if (MapUtils.isEmpty(paramMap)) {
                return;
            }
            paramMap.values().forEach(this::populateForUpdateParam);
        }
    }

    private void populateForNewDataObject(AbstractDataObject insertDataObject) {
        long currentTimestamps = Instant.now().toEpochMilli();
        gmtCreateFieldLoadCache.get(insertDataObject.getClass())
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(insertDataObject, currentTimestamps);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        createdByFieldLoadCache.get(insertDataObject.getClass())
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(insertDataObject, SecurityContextUtils.getCurrentUid());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        gmtModifiedFieldLoadCache.get(insertDataObject.getClass())
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(insertDataObject, currentTimestamps);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        lastModifiedByFieldLoadCache.get(insertDataObject.getClass())
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(insertDataObject, SecurityContextUtils.getCurrentUid());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        isDeleteFieldLoadCache.get(insertDataObject.getClass())
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(insertDataObject, 0L);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
//        if (insertDataObject.getId() == null) {
//            long id = populateId(insertDataObject);
//            insertDataObject.setId(id);
//        }
    }

    private void populateForUpdateDataObject(AbstractDataObject updateDataObject) {
        long currentTimestamps = Instant.now().toEpochMilli();
        gmtModifiedFieldLoadCache.get(updateDataObject.getClass())
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(updateDataObject, currentTimestamps);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        lastModifiedByFieldLoadCache.get(updateDataObject.getClass())
                .ifPresent(field -> {
                    try {
                        field.setAccessible(true);
                        field.set(updateDataObject, SecurityContextUtils.getCurrentUid());
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private Optional<Field> getGmtCreateField(Class<?> dataObjectClass) {
        Field[] fields = FieldUtils.getFieldsWithAnnotation(dataObjectClass, GmtCreate.class);
        if (ArrayUtils.isEmpty(fields)) {
            return Optional.empty();
        }
        return Optional.of(fields[0]);
    }
    private Optional<Field> getCreatedByField(Class<?> dataObjectClass) {
        Field[] fields = FieldUtils.getFieldsWithAnnotation(dataObjectClass, CreatedBy.class);
        if (ArrayUtils.isEmpty(fields)) {
            return Optional.empty();
        }
        return Optional.of(fields[0]);
    }

    private Optional<Field> getGmtModifiedField(Class<?> dataObjectClass) {
        Field[] fields = FieldUtils.getFieldsWithAnnotation(dataObjectClass, GmtModified.class);
        if (ArrayUtils.isEmpty(fields)) {
            return Optional.empty();
        }
        return Optional.of(fields[0]);
    }

    private Optional<Field> getLastModifiedByField(Class<?> dataObjectClass) {
        Field[] fields = FieldUtils.getFieldsWithAnnotation(dataObjectClass, LastModifiedBy.class);
        if (ArrayUtils.isEmpty(fields)) {
            return Optional.empty();
        }
        return Optional.of(fields[0]);
    }

    private Optional<Field> getIsDeleteField(Class<?> dataObjectClass) {
        Field[] fields = FieldUtils.getFieldsWithAnnotation(dataObjectClass, LogicDelete.class);
        if (ArrayUtils.isEmpty(fields)) {
            return Optional.empty();
        }
        return Optional.of(fields[0]);
    }

//    private long populateId(AbstractDataObject insertDataObject) {
//        SequenceGenerator sequenceGenerator = EnvironmentContext.getBean(SequenceGenerator.class);
//        return sequenceGenerator.next(buildKey(insertDataObject));
//    }

    private String buildKey(AbstractDataObject insertDataObject) {
        String name = insertDataObject.getClass().getSimpleName();
        return name.toLowerCase().substring(0, name.length() - 3) + "_id";
    }

}
