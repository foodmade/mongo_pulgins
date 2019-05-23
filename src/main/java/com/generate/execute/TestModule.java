package com.generate.execute;

import com.generate.common.create.ICreateJava;
import com.generate.common.create.CreateJavaImpl;
import com.generate.model.MongoOptions;
import com.generate.mongo.MongoDBUtil;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.Set;


public class TestModule {

    public static void main(String[] args) {
        ICreateJava ICreateJava = new CreateJavaImpl();

        MongoOptions options = new MongoOptions();
        options.setDataName("movie");
        options.setHost("106.75.233.202");
        options.setUser("movies");
        options.setPassword("chen19960119");
        options.setPort(27017);

        DB db = MongoDBUtil.getMongoDB(options);

        //获取当前库中所有的表字段
        Set<String> collectionNames = db.getCollectionNames();
        collectionNames.forEach(System.out::println);
        //获取表中所有的字段
        DBCollection users = db.getCollection("account");
        DBObject dbObejct = users.findOne();
        dbObejct.keySet().forEach(key ->{
            System.out.println("field: " + key);
            System.out.println("type:  " + dbObejct.get(key).getClass().getName());
        });

//        ICreateJava.createEntity("Test",attrList, EngineConst._PACKAGE_DEFAULT_PATH ,EngineConst._PACKAGE_FILE_OUT_PATH);
    }

}
