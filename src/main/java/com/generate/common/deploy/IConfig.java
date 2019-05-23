package com.generate.common.deploy;

import com.generate.model.ConfigNode;

public interface IConfig {

    /**
     * 添加配置
     */
    void addConfig() throws Exception;

    /**
     * 读取配置
     * @return
     */
    ConfigNode readConfig() throws Exception;
}
