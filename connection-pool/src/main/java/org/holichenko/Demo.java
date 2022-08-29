package org.holichenko;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.holichenko.pool.PooledDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Demo {

    public static void main(String[] args) {
        var mySqlDataSource = initMySqlPooledDataSource();
        var postgresDataSource = initPostgresPooledDataSource();
        var h2DataSource = initH2PooledDataSource();
        benchmark(mySqlDataSource);
        benchmark(postgresDataSource);
        benchmark(h2DataSource);
    }

    private static void benchmark(DataSource dataSource) {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            try (Connection connection = dataSource.getConnection()) {

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println(String.format("%s - result time: %d ms", dataSource.getClass().getSimpleName(), (end - start)));
    }

    private static DataSource initPostgresPooledDataSource() {
        return new PooledDataSource(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Abcd1234",
                10);
    }

    private static DataSource initMySqlPooledDataSource() {
        return new PooledDataSource(
                "jdbc:mysql://localhost:3306/mysql",
                "root",
                "root",
                10);
    }

    private static DataSource initH2PooledDataSource() {
        return new PooledDataSource(
                "jdbc:h2:mem:local;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;DB_CLOSE_ON_EXIT=FALSE",
                "test",
                "root",
                10);
    }
}
