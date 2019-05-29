package com.java;

import com.generate.common.deploy.IConfig;
import com.generate.common.deploy.MongoConfigControl;
import com.generate.model.ConfigConfigNode;
import com.generate.utils.DateUtils;
import com.generate.utils.IniReader;

import java.util.Date;
import java.util.Optional;

public class ConfigTest {

    public static void main(String[] args) throws Exception {
       /* IConfig iConfig = new MongoConfigControl().setConfig("2222");

        System.out.println(iConfig.readConfig().toString());

        IniReader iniReader = new IniReader("D:\\util\\git_project\\mongo_plugins\\config.ini");

        iniReader.getAllConfigToMap(new ConfigConfigNode());*/
        System.out.println(DateUtils.getDayOfWeek(DateUtils.formatYMD(new Date())));

    }
}
