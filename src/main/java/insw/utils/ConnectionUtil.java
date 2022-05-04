package insw.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectionUtil {
    private static HikariDataSource dataSource;

    static {
        String dbUrl = "jdbc:mysql://localhost:3306/sakila";
        String username = "root";
        String password = "171198";

        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl(dbUrl);
        config.setUsername(username);
        config.setPassword(password);

        // pool configuration
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(60_000);
        config.setMaxLifetime(10 * 60_000);

        // create connection
        dataSource = new HikariDataSource(config);
    }

    public static HikariDataSource getDataSource() {
        return dataSource;
    }
}
