package com.generate.dao;

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
    ConfigNode readConfig();
}
