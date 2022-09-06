package bibernate.util;

import bibernate.annotation.Table;
import bibernate.annotation.Column;
import bibernate.annotation.Id;
import bibernate.exception.BibernateException;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class EntityUtil {

    public static <T> String readTableName(Class<T> entityType) {
        return Optional.ofNullable(entityType.getAnnotation(Table.class))
                .map(Table::name)
                .orElseGet(() -> entityType.getSimpleName().toLowerCase());
    }

    public static <T> String getIdField(Class<T> entityType) {
        return Arrays.stream(entityType.getDeclaredFields())
                .filter(EntityUtil::isIdField)
                .findAny()
                .map(field -> field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).name() : field.getName())
                .orElseThrow(() -> new BibernateException("Cannot find a field marked with @Id in class " + entityType.getSimpleName()));
    }

    public static String resolveColumnName(Field field) {
        return Optional.ofNullable(field.getAnnotation(Column.class))
                .map(Column::name)
                .orElseGet(field::getName);
    }

    public static <T> T createEntityFromResultSet(Class<T> entityType, ResultSet resultSet) {
        try {
            var constructor = entityType.getConstructor();
            T entity = constructor.newInstance();
            for (Field field : entityType.getDeclaredFields()) {
                field.setAccessible(true);
                String columnName = resolveColumnName(field);
                Object columnValue = resultSet.getObject(columnName);
                field.set(entity, columnValue);
            }
            return entity;
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException |
                 SQLException e) {
            throw new BibernateException("Cannot create instance of class %s when parsing result set ".formatted(entityType), e);
        }
    }


    private boolean isIdField(Field field) {
        return field.isAnnotationPresent(Id.class);
    }
}
