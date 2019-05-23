package com.generate.dao.abs;

import com.generate.dao.IConfig;

public abstract class AbsConfig implements IConfig {

    public Node node;

    public String configName;

    public AbsConfig() {}

    public AbsConfig setConfig(String configName,Node node){
        this.node       = node;
        this.configName = configName;
        return this;
    }
}
