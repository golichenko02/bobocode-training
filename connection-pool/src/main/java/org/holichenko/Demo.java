package org.holichenko;

import org.holichenko.pool.PooledDataSource;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class Demo {

    public static void main(String[] args) {
        var dataSource = initDataSource();
        var customDataSource = initCustomPooledDataSource();
        benchmark(dataSource);
        benchmark(customDataSource);
    }

    private static void benchmark(DataSource dataSource) {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            try(Connection connection = dataSource.getConnection()) {

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Long end = System.currentTimeMillis();
        System.out.println(String.format("%s - result time: %d ms", dataSource.getClass().getSimpleName(), (end - start)));
    }

    private static DataSource initDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setURL("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("Abcd1234");
        return dataSource;
    }

    private static DataSource initCustomPooledDataSource() {
        return new PooledDataSource(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Abcd1234");
    }
}
