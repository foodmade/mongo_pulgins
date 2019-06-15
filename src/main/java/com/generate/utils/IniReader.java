package com.generate.utils;

import com.generate.common.exception.CommonException;
import com.abs.ConfigNode;
import com.generate.model.ConfigConfigNode;
import org.ini4j.Wini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * INI 配置文件操作类
 */
public class IniReader {

    private static Logger logger = LoggerFactory.getLogger(IniReader.class);

    private static String _DEFAULT_INIT_SECTION_NAME = "Base";

    private Wini ini;

    /**
     * 构造函数
     */
    public IniReader(String filename) throws Exception {
        Assert.isNotNull(filename,"ini构造器出现异常,配置文件路径不能为空",CommonException.class);
        try{
            ini = new Wini(new File(filename));
        }catch (Exception e){
            logger.error("【初始化ini配置文件出现异常 {}】",e.getMessage());
        }
    }

    public Wini getIni() {
        return ini;
    }

    /**
     * 读取配置文件
     * @param key  节点名称
     * @param cls  读取后转换的类
     */
    public <T> T getIniConfig(String key, Class<? extends ConfigNode> cls) throws Exception {
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

    /**
     * 添加配置文件节点
     * @param key   节点名称
     * @param configNode  节点内容
     */
    public void addIniConfig(String key, ConfigNode configNode) throws Exception {
        Assert.isNotNull(configNode,"写入ini配置文件时,node节点为空",CommonException.class);
        Assert.isNotNull(ini,"ini加载器为空",CommonException.class);

        HashMap<String,Object> nodeMap = ClassUtil.classTransMap(configNode);
        if(nodeMap == null){
            return;
        }
        storeIniConfig(key,nodeMap);
    }

    /**
     * 刷新ini文件
     */
    private void storeIniConfig(String key, HashMap<String,Object> nodeMap) throws Exception {

        Assert.isNotNull(ini,"ini加载器为空", CommonException.class);
        nodeMap.keySet().forEach(configKey -> {
            if(nodeMap.get(configKey) == null){
                return;
            }
            ini.put(key,configKey,nodeMap.get(configKey));
        });
        ini.store();
    }

    /**
     * 获取配置文件中所有的配置 只包含配置信息 不包含配置key
     * @param t  转换的对象类构造器
     */
    public <T extends ConfigNode> List<T> getAllConfig(T t) throws Exception {
        Assert.isNotNull(ini,"ini加载器为空", CommonException.class);

        Set<String> keySet =  ini.keySet();

        List<T> configs = new ArrayList<>();

        keySet.forEach(key ->{
            try {
                configs.add(getIniConfig(key,t.getClass()));
            } catch (Exception e) {
                logger.error("【读取ini配置文件失败：】{}",e.getMessage());
            }
        });
        return configs;
    }

    /**
     * 获取配置文件中所有的配置项,带key
     */
    public <T extends ConfigNode> HashMap<String,T> getAllConfigToMap(T t) throws Exception {
        Assert.isNotNull(ini,"ini加载器为空", CommonException.class);

        Set<String> keySet =  ini.keySet();

        HashMap<String,T> configMap = new HashMap<>();

        keySet.forEach(key ->{
            try {
                configMap.put(key,getIniConfig(key,t.getClass()));
            } catch (Exception e) {
                logger.error("【读取ini配置文件失败：】{}",e.getMessage());
            }
        });
        return configMap;
    }

    /**
     * 删除节点
     * @param nodeName 节点名称
     */
    public void removeNodeConfig(String nodeName) throws Exception {
        Assert.isNotNull(ini,"ini加载器为空", CommonException.class);
        ini.remove(ini.get(nodeName));
        ini.store();
    }

    /**
     * 更新节点名称
     */
    public void updateNodeName(String oldNodeName,String newNodeName) throws Exception {
        Assert.isNotNull(ini,"ini加载器为空",CommonException.class);
        Assert.isNotNull(oldNodeName,"oldNodeName不能为空",CommonException.class);

        ConfigConfigNode configConfigNode = getIniConfig(oldNodeName, ConfigConfigNode.class);
        Assert.isNotNull(configConfigNode,"不存在的配置项：" + oldNodeName,CommonException.class);

        removeNodeConfig(oldNodeName);

        addIniConfig(newNodeName,configConfigNode);
    }
}
