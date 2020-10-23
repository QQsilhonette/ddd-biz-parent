package org.ddd.biz.platform.test.utils;

import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;

public class DataSourceHelper {

    public static DataSource getH2DataSource(String databaseName) {
        JdbcDataSource h2DataSource = new JdbcDataSource();
        h2DataSource.setURL("jdbc:h2:mem:" + databaseName + ";MODE=MYSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=FALSE");
        h2DataSource.setUser("sa");
        h2DataSource.setPassword("");
        return h2DataSource;
    }

}
