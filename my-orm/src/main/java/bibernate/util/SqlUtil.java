package bibernate.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlUtil {

    public static final String SELECT_BY_COLUMN_QUERY_TEMPLATE = "SELECT * FROM %S WHERE %s = ?;";
}
