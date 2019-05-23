package com.generate.common.deploy;

import com.abs.Node;
import com.generate.utils.SystemPathUtil;

public abstract class AbsConfig implements IConfig {

    private static final String _DEFAULT_CONFIG_NAME = "/config.ini";
    public Node node;
    public String nodeName;
    public String configPath = SystemPathUtil.getProjectPath() + _DEFAULT_CONFIG_NAME;
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
