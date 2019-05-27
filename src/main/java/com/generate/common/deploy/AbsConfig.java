package com.generate.common.deploy;

import com.abs.ConfigNode;

public abstract class AbsConfig implements IConfig {

    public static final String _GENERATE_CONFIG_NAME = "/config.ini";
    public static final String _DATA_SOURCE_CONFIG   = "/dataSource.ini";

    public ConfigNode configNode;
    public String nodeName;
    public AbsConfig() {}

    public AbsConfig setConfig(String configName, ConfigNode configNode){
        this.configNode = configNode;
        this.nodeName = configName;
        return this;
    }

    public AbsConfig setConfig(String configName){
        this.nodeName = configName;
        return this;
    }
}
