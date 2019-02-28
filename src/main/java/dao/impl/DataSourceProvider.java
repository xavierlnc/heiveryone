package dao.impl;


import javax.sql.DataSource;
import java.sql.SQLException;
import org.mariadb.jdbc.MariaDbDataSource;

public class DataSourceProvider {

    private static MariaDbDataSource dataSource;

    public static DataSource getDataSource() throws SQLException {
        if (dataSource == null) {
            dataSource = new MariaDbDataSource();
            dataSource.setServerName("localhost");
            dataSource.setPort(3306);
            dataSource.setDatabaseName("heiveryone_database");
            dataSource.setUser("root");
            dataSource.setPassword(null);
        }
        return dataSource;
    }
}