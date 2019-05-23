package com.generate.common.create;

import com.generate.model.ValNode;

import java.util.HashMap;
import java.util.List;

public interface ICreateJava {

    /**
     *
     * @param fileName   文件名称
     * @param fieldList  类属性集合
     * @param packageUrl 包路径
     * @param outPath    输入路径
     * todo:适用于需要动态生成字段属性
     */
    void createEntity(String fileName, List<ValNode> fieldList,String packageUrl,String outPath);

    /**
     * @param fileName   文件名称
     * @param packageUrl 路径
     * @param outPath    输入路径
     * todo:适用于单一模板使用
     */
    void createDao(String fileName,String packageUrl,String outPath);

    /**
     * @param otherConfig
     * todo:适用于有很多配置项
     */
    void createEntity(String fileName, List<ValNode> fieldList, String packageUrl, String outPath, HashMap<String,Object> otherConfig);
}
