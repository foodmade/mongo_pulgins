package com.generate.mongo;

import com.generate.common.exception.ParamsInvalidException;
import com.generate.listener.MsgListener;
import com.generate.model.MongoOptions;
import com.generate.utils.CommonUtils;
import com.mongodb.DB;

public class DataSourceLinkerFetch extends Thread {

    private MongoOptions mongoOptions;

    private DB db;

    private boolean isFinish = false;

    public DB getDb() {
        return db;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public DataSourceLinkerFetch(MongoOptions mongoOptions) {
        this.mongoOptions = mongoOptions;
    }

    @Override
    public void run() {
        try {
            paramsCheck();
            db = MongoDBUtil.getMongoDB(mongoOptions.getHost(),mongoOptions.getUser(),mongoOptions.getPassword(),mongoOptions.getDataName(),mongoOptions.getPort());
        } finally {
            isFinish = true;
        }
    }


    private void paramsCheck() throws ParamsInvalidException {
        if(CommonUtils.isEmpty(mongoOptions.getHost(),mongoOptions.getPassword(),mongoOptions.getPort())){
            MsgListener.processMsg(null,"输入参数不完整");
            throw new ParamsInvalidException();
        }

        if(!CommonUtils.isHttpUrl(mongoOptions.getHost())){
            MsgListener.processMsg(null,"主机地址格式错误");
            throw new ParamsInvalidException();
        }

        if(!CommonUtils.isDigit(mongoOptions.getPort() + "") || mongoOptions.getPort() > 65536){
            MsgListener.processMsg(null,"端口只能为1~65536整数");
            throw new ParamsInvalidException();
        }
    }
}
