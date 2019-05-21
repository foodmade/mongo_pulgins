package com.generate.dao;

import com.generate.model.ValNode;

import java.util.HashMap;
import java.util.List;

public interface CreateJava {

    /**
     *
     * @param fileName   文件名称
     * @param fieldList  类属性集合
     * @param packageUrl 包路径
     * @param outPath    输入路径
     */
    void createEntity(String fileName, List<ValNode> fieldList,String packageUrl,String outPath);

    void createEntity(String fileName, List<ValNode> fieldList, String packageUrl, String outPath, HashMap<String,Object> otherConfig);
}
