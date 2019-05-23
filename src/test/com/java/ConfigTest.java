package com.java;

import com.generate.model.ConfigNode;
import com.generate.utils.ClassUtil;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigTest {

    public static void main(String[] args) throws Exception {
        Ini ini = new Ini(new File("D:\\config.ini"));

        ConfigNode configNode = new ConfigNode();
        configNode.setDaoOutFilePath("11111");
        configNode.setFieldProjectPath("222222");
        configNode.setNeedAnnotation(false);
        HashMap<String,Object> nodeMap = ClassUtil.classTransMap(configNode);
        System.out.println(nodeMap);

    }
}
