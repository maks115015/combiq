package ru.atott.combiq.dao.es;

import org.apache.commons.lang3.StringUtils;

public class NameVersionUtils {
    public static Long getVersion(String indexName, String delimiter) {
        if (!StringUtils.contains(indexName, delimiter)) {
            return null;
        }
        String[] parts = StringUtils.split(indexName, delimiter);
        return Long.valueOf(parts[parts.length - 1]);
    }

    public static String getName(String indexName, String delimiter) {
        if (!StringUtils.contains(indexName, delimiter)) {
            return null;
        }
        String[] parts = StringUtils.split(indexName, delimiter);
        return parts[0];
    }
}
