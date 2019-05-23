package com.java;

import com.generate.common.deploy.IConfig;
import com.generate.common.deploy.KeepConfigControl;
import com.generate.model.ConfigNode;
import com.generate.utils.ClassUtil;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigTest {

    public static void main(String[] args) throws Exception {
        IConfig iConfig = new KeepConfigControl().setConfig("test");

        System.out.println(iConfig.readConfig().toString());



    /*    ConfigNode configNode = new ConfigNode();
        configNode.setDaoOutFilePath("11111");
        configNode.setFieldProjectPath("222222");
        configNode.setNeedAnnotation(false);
        HashMap<String,Object> nodeMap = ClassUtil.classTransMap(configNode);
        System.out.println(nodeMap);
*/
    }
}
