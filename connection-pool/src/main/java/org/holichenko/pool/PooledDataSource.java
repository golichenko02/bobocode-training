package org.holichenko.pool;

import org.holichenko.exception.CustomPoolException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;

public class PooledDataSource implements DataSource {
    private final int poolSize;

    private final Queue<Connection> connectionPool;
    private final String url;
    private final String username;
    private final String password;

    public PooledDataSource(String url, String username, String password, int poolSize) {
        checkArguments(url, username, password, poolSize);
        this.connectionPool = new ConcurrentLinkedQueue<>();
        this.url = url;
        this.username = username;
        this.password = password;
        this.poolSize = poolSize;
        initializePool();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return connectionPool.poll();
    }

    public String getDriverName() {
        try {
            return DriverManager.getDriver(this.url).toString();
        } catch (SQLException e) {
            throw new CustomPoolException("Exception occurred when trying to retrieve driverName", e);
        }
    }

    private void initializePool() {
        Optional.ofNullable(getDriverName()).orElseThrow(() -> new IllegalArgumentException("Cannot retrieve the driver"));
        for (int i = 0; i < poolSize; i++) {
            try {
                var connection = new ConnectionProxy(DriverManager.getConnection(url, username, password), connectionPool);
                connectionPool.offer(connection);
            } catch (SQLException e) {
                throw new CustomPoolException("Exception occurred when trying to get connection from DriverManager", e);
            }
        }
    }

    @Override
    public Connection getConnection(String username, String password) {
        return this.connectionPool.poll();
    }

    @Override
    public PrintWriter getLogWriter() {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) {

    }

    @Override
    public void setLoginTimeout(int seconds) {

    }

    @Override
    public int getLoginTimeout() {
        return 0;
    }

    @Override
    public <T> T unwrap(Class<T> iface) {
        if (iface.isAssignableFrom(getClass())) {
            return iface.cast(this);
        }
        throw new CustomPoolException("Cannot unwrap %s to %s".formatted(getClass(), iface));
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) {
        return iface.isAssignableFrom(getClass());
    }

    @Override
    public Logger getParentLogger() {
        return Logger.getLogger("org.holichenko");
    }

    private void checkArguments(String url, String username, String password, int poolSize) {
        Objects.requireNonNull(url, "url mustn't be null!");
        Objects.requireNonNull(url, "username mustn't be null!");
        Objects.requireNonNull(password, "password mustn't be null!");
        if (poolSize <= 0) {
            throw new IllegalArgumentException("pool size cannot be less than 1");
        }
    }
}
