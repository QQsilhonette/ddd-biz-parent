package org.ddd.biz.platform.test.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.internal.types.JavaTypeResolverDefaultImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class CustomTypeResolver extends JavaTypeResolverDefaultImpl {

    public CustomTypeResolver() {
    }

    @Override
    public FullyQualifiedJavaType calculateJavaType(IntrospectedColumn introspectedColumn) {
        FullyQualifiedJavaType fullyQualifiedJavaType = super.calculateJavaType(introspectedColumn);
        if (Date.class.getCanonicalName().equals(fullyQualifiedJavaType.getFullyQualifiedName())) {
            if ("DATE".equals(typeMap.get(introspectedColumn.getJdbcType()).getJdbcTypeName())) {
                return new FullyQualifiedJavaType(LocalDate.class.getCanonicalName());
            } else {
                return new FullyQualifiedJavaType(LocalDateTime.class.getCanonicalName());
            }
        }
        if (Byte.class.getCanonicalName().equals(fullyQualifiedJavaType.getFullyQualifiedName())) {
            return new FullyQualifiedJavaType(Integer.class.getCanonicalName());
        }
        if (Short.class.getCanonicalName().equals(fullyQualifiedJavaType.getFullyQualifiedName())) {
            return new FullyQualifiedJavaType(Integer.class.getCanonicalName());
        }
        return fullyQualifiedJavaType;
    }
}
