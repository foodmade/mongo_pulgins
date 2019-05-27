package com.generate.common.deploy;

import com.abs.Node;
import com.generate.utils.SystemPathUtil;

public abstract class AbsConfig implements IConfig {

    public static final String _GENERATE_CONFIG_NAME = "/config.ini";
    public static final String _DATA_SOURCE_CONFIG   = "/dataSource.ini";

    public Node node;
    public String nodeName;
    public AbsConfig() {}

    public AbsConfig setConfig(String configName,Node node){
        this.node       = node;
        this.nodeName = configName;
        return this;
    }

    public AbsConfig setConfig(String configName){
        this.nodeName = configName;
        return this;
    }
}
