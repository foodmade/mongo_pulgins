package com.generate.mongo;

import com.mongodb.MongoClient;

import java.util.HashMap;
import java.util.Set;

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

    public static HashMap<String, MongoClient> getMongoMapperPool() {
        return mongoMapperPool;
    }

    public static void removMongoClient(String key){
        mongoMapperPool.remove(key);
    }
}
