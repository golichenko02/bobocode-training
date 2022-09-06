package bibernate.util;

public record EntityKey<T>(Class<T> entityType, Object id) {
}
