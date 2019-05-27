package com.java;

import com.generate.model.ConfigNode;
import com.generate.utils.IniReader;

public class ConfigTest {

    public static void main(String[] args) throws Exception {
/*        IConfig iConfig = new KeepConfigControl().setConfig("test");

        System.out.println(iConfig.readConfig().toString());*/

        IniReader iniReader = new IniReader("D:\\util\\git_project\\mongo_plugins\\config.ini");

        iniReader.getAllConfigToMap(new ConfigNode());

    }
}
