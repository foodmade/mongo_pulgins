package com.generate.mongo;

import com.mongodb.MongoClient;

import java.util.HashMap;

public class MongoPool {

    private static HashMap<String, MongoClient> mongoMapperPool = new HashMap<>();

    public static void addPool(String key,MongoClient mongoClient){
        if(clientIsExist(key)){
            return;
        }
        mongoMapperPool.put(key,mongoClient);
    }

    public static boolean clientIsExist(String key){
        return mongoMapperPool.containsKey(key);
    }

    public static MongoClient getMongoClientToPool(String key){
        return mongoMapperPool.get(key);
    }
}
