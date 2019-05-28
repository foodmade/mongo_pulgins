package com.generate.mongo;

import com.generate.common.comment.DialogComment;
import com.generate.common.exception.DataSourceLinkerException;
import com.generate.common.exception.ParamsInvalidException;
import com.generate.listener.MsgListener;
import com.generate.model.MongoOptions;
import com.generate.utils.Const;
import com.mongodb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;

public class MongoDBUtil {

    private static Logger logger = LoggerFactory.getLogger(MongoDBUtil.class);

    private static final Integer _SERVER_SELECTION_TIME_OUT = 5000;
    private static final Integer _CONNECT_TIME_OUT          = 1000;
    private static final Integer _SOCKET_TIME_OUT           = 1000;

    private static final String _LOG_PREFIX       = "【获取Mongo Linker 出现异常:】";
    private static final String _MSG_TYPE_DEFAULT = "Fetch Mongo Linker";

    public static DB getMongoDB(String host, String user, String password, String dataName, Integer port) throws DataSourceLinkerException,ParamsInvalidException{
        String key = buildMongoUrlKey(host,user,password,dataName,port);
        if(!paramsCheck(host,user,password,dataName,port)){
            MsgListener.processMsg(_MSG_TYPE_DEFAULT,"连接mongo的必要参数缺失");
            throw new ParamsInvalidException("dataBase Params Defect");
        }
        MsgListener.processMsg(null,"参数校验完毕,尝试连接数据库>>>");
        MongoClient mongoClient = MongoPool.getMongoClientToPool(key);
        if(checkConnectorIsUsable(mongoClient, dataName)){
            try {
                MongoCredential credential = MongoCredential.createCredential(user,dataName,password.toCharArray());
                ServerAddress addr = new ServerAddress(host, port);
                mongoClient = new MongoClient(addr, Collections.singletonList(credential),configOptions().build());
                if(checkConnectorIsUsable(mongoClient, dataName)){
                    return null;
                }
                MongoPool.addPool(key,mongoClient);
                return mongoClient.getDB(dataName);
            } catch (Exception e){
                logger.error(_LOG_PREFIX + " {}",e.getMessage());
                MsgListener.processMsg(_MSG_TYPE_DEFAULT,"连接Mongo服务器异常：" + e.getMessage());
                DialogComment.detailMessageDialog(Const._ERROR,"连接Mongo服务器异常","",e);
                throw new DataSourceLinkerException(_LOG_PREFIX + e.getMessage());
            }
        }
        return mongoClient.getDB(dataName);
    }

    private static MongoClientOptions.Builder configOptions(){
        MongoClientOptions.Builder options = new MongoClientOptions.Builder();
        options.serverSelectionTimeout(_SERVER_SELECTION_TIME_OUT);
        options.connectTimeout(_CONNECT_TIME_OUT);
        options.socketTimeout(_SOCKET_TIME_OUT);
        return options;
    }

    public static DB getMongoDB(MongoOptions mongoOptions){
        return getMongoDB(mongoOptions.getHost(),mongoOptions.getUser(),mongoOptions.getPassword(),mongoOptions.getDataName(),mongoOptions.getPort());
    }

    public static void removeMongoDB(MongoOptions mongoOptions){
        removeCacheMongo(mongoOptions.getHost(),mongoOptions.getUser(),mongoOptions.getPassword(),mongoOptions.getDataName(),mongoOptions.getPort());
    }

    public static void removeCacheMongo(String host, String user, String password, String dataName, Integer port){
        MongoPool.removMongoClient(buildMongoUrlKey(host, user, password, dataName, port));
    }

    private static boolean checkConnectorIsUsable(MongoClient mongoClient,String dataName){
        if(mongoClient == null){
            return true;
        }
        try {
            //随机获取一个表名
            mongoClient.getDatabase(dataName).listCollections().iterator().next().get("name");
            MsgListener.processMsg(null,"连接数据库成功 ^_^");
        } catch (Exception e) {
            MsgListener.processMsg(null,"连接数据库失败 o(╥﹏╥)o");
            MsgListener.processMsg(null,"失败信息：" + e.getMessage());
            throw e;
        }
        return false;
    }

    private static boolean paramsCheck(Object...args) {

        if(args == null || args.length == 0){
            return false;
        }

        for (Object o : args){
            if(o == null || "".equals(o)){
                return false;
            }
        }
        return true;
    }

    public static String buildMongoUrlKey(String host,String user,String password,String dataName,Integer port){
        return "mongo://" +
                user +
                ":" +
                password +
                "@" +
                host +
                ":" +
                port +
                "/" +
                dataName;
    }

}
