package org.ddd.all.generator;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.ddd.all.infrastructure.mapper.PmsBrandMapper;
import org.ddd.biz.platform.test.utils.DataSourceHelper;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class BaseDaoTest {

    protected static PmsBrandMapper pmsBrandMapper;

    private static DataSource dataSource;
    private static SqlSession session;

    private static final String DB_NAME = "h2";

    @BeforeAll
    static void setUp() throws Exception {
        dataSource = DataSourceHelper.getH2DataSource(DB_NAME);
        Flyway flyway = Flyway.configure().dataSource(DataSourceHelper.getH2DataSource(DB_NAME)).load();
        flyway.migrate();
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);

        URL uri = BaseDaoTest.class.getResource("/");
        assertNotNull(uri);
        String basePath = uri.getPath() + "../../../all-infrastructure/src/main/resources/";
//        String configLocationUrl = basePath + "mybatis/mybatis-config.xml";
//        factory.setConfigLocation(new FileUrlResource(configLocationUrl));

        File mapperLocationDir = new File(basePath + "mapper");
        String[] suffix = new String[]{"xml"};
        Resource[] mapperLocations = FileUtils.listFiles(mapperLocationDir, suffix, false).stream().map(p -> {
            try {
                return new FileUrlResource(p.getAbsolutePath());
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }).toArray(org.springframework.core.io.Resource[]::new);
        factory.setMapperLocations(mapperLocations);

        SqlSessionFactory sqlSessionFactory = factory.getObject();
        assertNotNull(sqlSessionFactory);
        session = sqlSessionFactory.openSession();

        pmsBrandMapper = session.getMapper(PmsBrandMapper.class);
    }

    @AfterAll
    static void tearDown() {
        Optional.ofNullable(session).ifPresent(SqlSession::close);
//        Optional.ofNullable(dataSource).ifPresent(DataSource::close);
    }


    protected String nextId() {
        return UUID.randomUUID().toString();
    }
}
