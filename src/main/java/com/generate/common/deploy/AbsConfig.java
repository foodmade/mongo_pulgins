package com.generate.common.deploy;

import com.abs.ConfigNode;

import java.util.HashMap;

public abstract class AbsConfig implements IConfig {

    static final String _GENERATE_CONFIG_NAME = "/config.ini";
    static final String _DATA_SOURCE_CONFIG   = "/dataSource.ini";

    /**
     * 配置项
     */
    ConfigNode configNode;
    /**
     * 节点名称
     */
    String nodeName;
    /**
     * 更新字段 + 值
     */
    HashMap<String,String> updateMap;
    /**
     * 新节点名称
     */
    String newNodeName;
    AbsConfig() {}

    public AbsConfig setConfig(String configName, ConfigNode configNode){
        this.configNode = configNode;
        this.nodeName = configName;
        return this;
    }

    public AbsConfig setConfig(String configName){
        this.nodeName = configName;
        return this;
    }

    public AbsConfig setConfig(String configName,HashMap<String,String> updateMap){
        this.nodeName = configName;
        this.updateMap = updateMap;
        return this;
    }

    public AbsConfig setConfig(String oldConfigName,String newConfigName){
        this.newNodeName = newConfigName;
        this.nodeName = oldConfigName;
        return this;
    }
}
