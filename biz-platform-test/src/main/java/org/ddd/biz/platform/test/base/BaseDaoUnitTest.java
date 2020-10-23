package org.ddd.biz.platform.test.base;

import org.ddd.biz.platform.test.utils.DataSourceHelper;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicLong;

@PowerMockIgnore({"java.lang.management.*", "javax.management.*", "javax.xml.*", "org.xml.sax.*", "org.apache.xerces.*", "org.w3c.*", "javax.net.ssl.*"})
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@Transactional
public abstract class BaseDaoUnitTest {
    public static final AtomicLong idGenerator = new AtomicLong(System.currentTimeMillis());

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("env", "dev");
        Flyway flyway = Flyway.configure().dataSource(DataSourceHelper.getH2DataSource("testDB")).load();
        flyway.migrate();
    }

    @Before
    public void init() {
    }
}
