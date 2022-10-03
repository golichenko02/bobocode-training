package bibernate.session.impl;

import bibernate.exception.BibernateException;
import bibernate.session.Session;
import bibernate.util.EntityKey;
import bibernate.util.EntityUtil;
import bibernate.util.SqlUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class SessionImpl implements Session {

    private final DataSource dataSource;
    private final Map<EntityKey<?>, Object> entitiesByKey = new HashMap<>();

    @Override
    public <T> T find(Class<T> entityType, Object id) {
        log.info("Finding entity {} by id = {}", entityType.getSimpleName(), id);
        EntityKey<T> entityKey = new EntityKey<>(entityType, id);
        Object entity = entitiesByKey.get(entityKey);
        if (entity != null) {
            log.trace("Returning cached instance of entity from the context {}", entity);
            return entityType.cast(entity);
        }
        log.trace("No cached entity found. Loading entity from the DB...");
        entity = findByKey(entityKey);
        entitiesByKey.put(entityKey, entity);
        return entityType.cast(entity);
    }

    @Override
    public void close() {
        entitiesByKey.clear();
    }

    private <T> T findByKey(EntityKey<T> entityKey) {
        String tableName = EntityUtil.readTableName(entityKey.entityType());
        String idField = EntityUtil.getIdField(entityKey.entityType());
        String query = String.format(SqlUtil.SELECT_BY_COLUMN_QUERY_TEMPLATE, tableName, idField);
        try (var connection = dataSource.getConnection();
             var statement = connection.prepareStatement(query)) {
            statement.setObject(1, entityKey.id());
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return EntityUtil.createEntityFromResultSet(entityKey.entityType(), resultSet);
        } catch (SQLException e) {
            throw new BibernateException("Exception occurred when trying to establish connection with data source and execute SELECT query", e);
        }
    }
}
    