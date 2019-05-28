package com.generate.common.deploy;

import com.abs.ConfigNode;

import java.util.HashMap;
import java.util.List;

public interface IConfig {

    /**
     * 添加配置
     */
    void addConfig() throws Exception;

    /**
     * 读取配置
     */
    ConfigNode readConfig() throws Exception;

    /**
     * 获取所有配置项 返回List
     */
    <V extends ConfigNode> List<V> readAllConfigByList() throws Exception;

    /**
     * 获取所有配置项 返回Map
     */
    <V extends ConfigNode> HashMap<String,V> readAllConfigByMap() throws Exception;

    /**
     * 删除配置
     */
    void removeConfig() throws Exception;

}
