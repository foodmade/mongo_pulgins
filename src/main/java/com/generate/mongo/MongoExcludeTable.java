package com.generate.mongo;

import java.util.HashSet;
import java.util.Set;

public class MongoExcludeTable {

    private static Set<String> tableNameSet = new HashSet<>();

    static {
        tableNameSet.add("system.indexes");
        tableNameSet.add("system.users");
    }

    public static Set<String> getTableNameSet() {
        return tableNameSet;
    }
}
