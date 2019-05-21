package com.generate.source;

import com.mongodb.DB;

import java.util.HashMap;

public class DBCacheControl {

    private static HashMap<String, DB> collectionMap = new HashMap<>();

    public static void putDB(String dataName,DB db){
        collectionMap.put(dataName,db);
    }

    public static DB getCacheDB(String dataName){
        return collectionMap.get(dataName);
    }
}
