package com.generate.model;

import com.abs.ConfigNode;
import com.generate.mongo.MongoDBUtil;

public class MongoOptions extends ConfigNode {

    private String host;

    private String user;

    private String password;

    private String dataName;

    private Integer port;

    private String saveName;

    private String url;

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getUrl() {
        return MongoDBUtil.buildMongoUrlKey(host,user,password,dataName,port);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "MongoOptions{" +
                "host='" + host + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", dataName='" + dataName + '\'' +
                ", port=" + port +
                ", saveName='" + saveName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
