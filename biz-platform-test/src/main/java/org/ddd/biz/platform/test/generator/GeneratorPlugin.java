package org.ddd.biz.platform.test.generator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.ddd.biz.platform.framework.dao.AbstractDataObject;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.internal.NullProgressCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GeneratorPlugin extends PluginAdapter {
    private static final String ID_COLUMN_NAME = "id";
    private static final String CREATED_DATE_COLUMN_NAME = "gmt_create";
    private static final String LAST_MODIFIED_DATE_COLUMN_NAME = "gmt_modified";
    private static final String IS_DELETE = "is_delete";

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        super.initialized(introspectedTable);
        introspectedTable.setBaseRecordType(introspectedTable.getBaseRecordType() + "DO");
    }

    @Override
    public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * " + MergeConstants.NEW_ELEMENT_TAG);
        interfaze.addJavaDocLine(" * 表名: " + introspectedTable.getFullyQualifiedTableNameAtRuntime());
        interfaze.addJavaDocLine(" * @date " + new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
        interfaze.addJavaDocLine(" */");
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType(new FullyQualifiedJavaType(Data.class.getCanonicalName()));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(Accessors.class.getCanonicalName()));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(EqualsAndHashCode.class.getCanonicalName()));
        topLevelClass.addImportedType(new FullyQualifiedJavaType(ToString.class.getCanonicalName()));
        topLevelClass.addAnnotation("@Data");
        topLevelClass.addAnnotation("@Accessors(chain = true)");
        topLevelClass.addAnnotation("@EqualsAndHashCode(callSuper = true)");
        topLevelClass.addAnnotation("@ToString(callSuper = true)");

        FullyQualifiedJavaType baseDoJavaType = new FullyQualifiedJavaType(AbstractDataObject.class.getCanonicalName());
        topLevelClass.addImportedType(baseDoJavaType);
        topLevelClass.setSuperClass(baseDoJavaType.getShortName());
        return true;
    }

    @Override
    public boolean modelFieldGenerated(Field field, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        if (StringUtils.equalsIgnoreCase(CREATED_DATE_COLUMN_NAME, introspectedColumn.getActualColumnName()) ||
                StringUtils.equalsIgnoreCase(LAST_MODIFIED_DATE_COLUMN_NAME, introspectedColumn.getActualColumnName())) {
            return false;
        }
        if (StringUtils.equalsIgnoreCase(ID_COLUMN_NAME, introspectedColumn.getActualColumnName())) {
            return false;
        }
        if (StringUtils.equalsIgnoreCase(IS_DELETE, introspectedColumn.getActualColumnName())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }

    private static final ThreadLocal<String> currentTable = new ThreadLocal<>();

    public static class VerboseProgressCallback extends NullProgressCallback {

        public VerboseProgressCallback() {
            super();
        }

        @Override
        public void startTask(String taskName) {
            System.out.println("日志:" + taskName);
            if (StringUtils.startsWith(taskName, "Introspecting table")) {
                currentTable.set(taskName.replace("Introspecting table ", ""));
            }
        }
    }

}
