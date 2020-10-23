package org.ddd.all.generator;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.comments.Comment;
import org.ddd.biz.platform.test.generator.GeneratorPlugin;
import org.ddd.biz.platform.test.utils.DataSourceHelper;
import org.flywaydb.core.Flyway;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class MybatisGenerator {
    private static final String SYS_PREF = "";
    private static final String REPLACE_SYS_PREF = "^All";
    private static final String TARGET_PATH = "biz-platform-all/all-infrastructure/src/main";
    private static final String GENERATE_PACKAGE = "org.ddd.all.infrastructure.generated";
    private static final String DB_NAME = "testDB";

    public static void main(String[] args) {
        Flyway flyway = Flyway.configure().dataSource(DataSourceHelper.getH2DataSource(DB_NAME)).load();
        flyway.migrate();

        System.out.println("输入要生成的表名(demo:表 " + SYS_PREF + "demo),q (quit) 退出");

        String tables;
        boolean quit = false;
        while (!quit) {
            Scanner in = new Scanner(System.in);
            tables = in.nextLine();
            if ("q".equalsIgnoreCase(tables)) {
                quit = true;
            } else {
                generateForTable(tables);
                System.out.println("输入要生成的表名(demo:表 " + SYS_PREF + "demo),q (quit) 退出");
            }
        }
        System.out.println("结束");
    }

    private static void generateForTable(String tables) {
        /**
         * 替换生成代码的项目路径
         */
        String generatorTargetDir = new File(TARGET_PATH).getAbsolutePath();
        try {
            /*配置xml配置项*/
            List<String> warnings = new ArrayList<>();
            Configuration config = new Configuration();
            Context context = new Context(ModelType.CONDITIONAL);
            context.setTargetRuntime("MyBatis3Simple");
            context.setId("defaultContext");
            //自动识别数据库关键字，默认false，如果设置为true，
            //根据SqlReservedWords中定义的关键字列表；一般保留默认值，遇到数据库关键字（Java关键字），
            //使用columnOverride覆盖
            context.addProperty("autoDelimitKeywords", "true");
            //生成的Java文件的编码
            context.addProperty("javaFileEncoding", "utf-8");
            context.addProperty("beginningDelimiter", "`");
            context.addProperty("endingDelimiter", "`");
            //格式化java代码
            context.addProperty("javaFormatter", "org.mybatis.generator.api.dom.DefaultJavaFormatter");
            //格式化xml代码
            context.addProperty("xmlFormatter", "org.mybatis.generator.api.dom.DefaultXmlFormatter");


            //格式化信息
            PluginConfiguration pluginConfiguration = new PluginConfiguration();
            pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.SerializablePlugin");
            pluginConfiguration.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
            pluginConfiguration.setConfigurationType("org.ddd.biz.platform.test.generator.GeneratorPlugin");
            context.addPluginConfiguration(pluginConfiguration);

            //设置是否生成注释
            CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
            commentGeneratorConfiguration.setConfigurationType("org.ddd.biz.platform.test.generator.CommentGenerator");
            context.setCommentGeneratorConfiguration(commentGeneratorConfiguration);

            //设置连接数据库
            JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
            jdbcConnectionConfiguration.setDriverClass("org.h2.Driver");
            jdbcConnectionConfiguration.setConnectionURL("jdbc:h2:mem:" + DB_NAME + ";MODE=MYSQL;DB_CLOSE_DELAY=-1");
            jdbcConnectionConfiguration.setUserId("sa");
            jdbcConnectionConfiguration.setPassword("");
            context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);


            JavaTypeResolverConfiguration javaTypeResolverConfiguration = new JavaTypeResolverConfiguration();
            //是否使用bigDecimal， false可自动转化以下类型（Long, Integer, Short, etc.）
            javaTypeResolverConfiguration.addProperty("forceBigDecimals", "false");
            javaTypeResolverConfiguration.setConfigurationType("org.ddd.biz.platform.test.generator.CustomTypeResolver");
            context.setJavaTypeResolverConfiguration(javaTypeResolverConfiguration);

            /**
             * 替换生成实体类的路径
             */
            JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
            javaModelGeneratorConfiguration.setTargetProject(generatorTargetDir + File.separator + "java");
            javaModelGeneratorConfiguration.setTargetPackage(GENERATE_PACKAGE );
            javaModelGeneratorConfiguration.addProperty("enableSubPackages", "true");
            javaModelGeneratorConfiguration.addProperty("trimStrings", "true");
            context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);

            /**
             * 替换生成Mapper类的路径
             */
            JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
            javaClientGeneratorConfiguration.setConfigurationType("XMLMAPPER");
            javaClientGeneratorConfiguration.setTargetProject(generatorTargetDir + File.separator + "java");
            javaClientGeneratorConfiguration.setTargetPackage(GENERATE_PACKAGE);
            javaClientGeneratorConfiguration.addProperty("enableSubPackages", "true");
            context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);

            /**
             * 替换生成xml的路径
             */
            SqlMapGeneratorConfiguration sqlMapGeneratorConfiguration = new SqlMapGeneratorConfiguration();
            sqlMapGeneratorConfiguration.setTargetProject(generatorTargetDir + File.separator + "resources");
            sqlMapGeneratorConfiguration.setTargetPackage("generated");
            sqlMapGeneratorConfiguration.addProperty("enableSubPackages", "true");
            context.setSqlMapGeneratorConfiguration(sqlMapGeneratorConfiguration);
            TableConfiguration tableConfiguration = new TableConfiguration(context);

            /**
             * 表名的前缀需要根据具体项目替换
             */
            tableConfiguration.setTableName(SYS_PREF + tables);
            DomainObjectRenamingRule domainObjectRenamingRule = new DomainObjectRenamingRule();
            domainObjectRenamingRule.setReplaceString("");
            domainObjectRenamingRule.setSearchString(REPLACE_SYS_PREF);
            tableConfiguration.setDomainObjectRenamingRule(domainObjectRenamingRule);
            //tableConfiguration.addIgnoredColumn(new IgnoredColumn(STATUS_COLUMN_NAME));
            context.addTableConfiguration(tableConfiguration);
            config.addContext(context);


            DefaultShellCallback shellCallback = new DefaultShellCallback(false) {
                @Override
                public boolean isMergeSupported() {
                    return true;
                }

                @Override
                public String mergeJavaFile(String newFileSource, File existingFile, String[] javadocTags, String fileEncoding) {
                    CompilationUnit newCompilationUnit = JavaParser.parse(newFileSource);
                    CompilationUnit existingCompilationUnit;
                    try {
                        existingCompilationUnit = JavaParser.parse(existingFile);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    Set<String> importNames = new LinkedHashSet<>();
                    NodeList<ImportDeclaration> imports = new NodeList<>();

                    for (ImportDeclaration im : newCompilationUnit.getImports()) {
                        if (!importNames.contains(im.getNameAsString())) {
                            imports.add(im);
                            importNames.add(im.getNameAsString());
                        }
                    }
                    for (ImportDeclaration im : existingCompilationUnit.getImports()) {
                        if (!importNames.contains(im.getNameAsString())) {
                            imports.add(im);
                            importNames.add(im.getNameAsString());
                        }
                    }
                    newCompilationUnit.setImports(imports);
                    NodeList<TypeDeclaration<?>> oldType = existingCompilationUnit.getTypes();
                    for (int i = 0; i < oldType.size(); i++) {
                        for (FieldDeclaration fieldDeclaration : oldType.get(i).getFields()) {
                            final Optional<Comment> comment = fieldDeclaration.getComment();
                            if (!comment.isPresent() || (comment.isPresent() && !comment.get().getContent().contains(MergeConstants.NEW_ELEMENT_TAG))) {
                                newCompilationUnit.getType(i).getMembers().add(fieldDeclaration);
                            }
                        }
                    }
                    for (int i = 0; i < oldType.size(); i++) {
                        for (MethodDeclaration methodDeclaration : oldType.get(i).getMethods()) {
                            final Optional<Comment> comment = methodDeclaration.getComment();
                            if (!comment.isPresent() || (comment.isPresent() && !comment.get().getContent().contains(MergeConstants.NEW_ELEMENT_TAG))) {
                                newCompilationUnit.getType(i).getMembers().add(methodDeclaration);
                            }
                        }
                    }

                    return newCompilationUnit.toString();
                }
            };
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, shellCallback, warnings);
            myBatisGenerator.generate(new GeneratorPlugin.VerboseProgressCallback(), new HashSet<String>(), new HashSet<String>());
        } catch (InvalidConfigurationException | SQLException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}

