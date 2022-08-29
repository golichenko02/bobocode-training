package org.holichenko.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.holichenko.exception.CustomPoolException;
import org.postgresql.ds.PGSimpleDataSource;

public class PooledDataSource extends PGSimpleDataSource {

    private final int POOL_SIZE = 10;
    private final Queue<Connection> connectionPool;

    public PooledDataSource(String url, String username, String password) {
        this.connectionPool = new ConcurrentLinkedQueue<>();
        super.setURL(url);
        super.setUser(username);
        super.setPassword(password);
        initializePool();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connectionPool.poll();
    }

    private void initializePool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            try{
                var connection = new ConnectionProxy(super.getConnection(), connectionPool);
                connectionPool.offer(connection);
            } catch (SQLException e) {
                throw new CustomPoolException("Exception occurred when trying to get connection from datasource", e);
            }
        }
    }
}
