package com.generate.utils;

import com.generate.common.exception.CommonException;
import com.generate.dao.abs.Node;
import org.ini4j.Ini;
import org.ini4j.Profile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * INI 配置文件操作类
 */
public class IniReader {

    private static Logger logger = LoggerFactory.getLogger(IniReader.class);

    private Ini ini;

    /**
     * 构造函数
     */
    public IniReader(String filename) throws Exception {
        Assert.isNotNull(filename,"ini构造器出现异常,配置文件路径不能为空",CommonException.class);
        try{
            ini = new Ini(new File(filename));
        }catch (Exception e){
            logger.error("【初始化ini配置文件出现异常 {}】",e.getMessage());
        }
    }

    /**
     * 读取配置文件
     * @param key  节点名称
     * @param cls  读取后转换的类
     */
    public <T> T getIniConfig(String key, Class cls) throws Exception {
        Assert.isNotNull(cls,"读取配置文件时,带转换的类加载器不能为空", CommonException.class);
        T configBean = null;
        try {
            configBean = (T)cls.newInstance();
            ini.get(key).to(configBean);
        }catch (Exception e){
            logger.error("【读取ini配置文件异常 {}】",e.getMessage());
        }
        return configBean;
    }

    public void addIniConfig(String key, Node node) throws Exception {
        Assert.isNotNull(node,"写入ini配置文件时,node节点为空",CommonException.class);
        Assert.isNotNull(ini,"ini加载器为空",CommonException.class);

        HashMap<String,Object> nodeMap = ClassUtil.classTransMap(node);
        if(nodeMap == null){
            return;
        }
        storeIniConfig(key,nodeMap);
    }

    private void storeIniConfig(String key, HashMap<String,Object> nodeMap) throws IOException {

        Profile.Section section = ini.get(key);

        nodeMap.keySet().forEach(configKey -> {
            if(nodeMap.get(configKey) == null){
                return;
            }
            section.put(key,nodeMap.get(configKey));
        });

        ini.store();
    }

}
