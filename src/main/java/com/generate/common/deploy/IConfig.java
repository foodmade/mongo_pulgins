package com.generate.common.deploy;

import com.abs.Node;

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
    Node readConfig() throws Exception;

    /**
     * 获取所有配置项 返回List
     */
    <V extends Node> List<V> readAllConfigByList() throws Exception;

    /**
     * 获取所有配置项 返回Map
     */
    <V extends Node> HashMap<String,V> readAllConfigByMap() throws Exception;

}
