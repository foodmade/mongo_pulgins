package com.generate.common.deploy;

import com.abs.ConfigNode;
import com.generate.common.exception.CommonException;
import com.generate.model.MongoOptions;
import com.generate.utils.Assert;
import com.generate.utils.FileUtils;
import com.generate.utils.IniReader;
import com.generate.utils.SystemPathUtil;

import java.util.HashMap;
import java.util.List;

/**
 * mongo数据源配置
 */
public class MongoConfigControl extends AbsConfig {

    public String configPath = SystemPathUtil.getProjectPath() + _DATA_SOURCE_CONFIG;

    @Override
    public void addConfig() throws Exception {
        Assert.isNotNull(configNode,"【AddConfig MongoNode Is Not Must Null】", CommonException.class);
        MongoOptions configNode = (MongoOptions) this.configNode;
        FileUtils.judeFileExists(configPath);
        IniReader iniReader = new IniReader(configPath);
        iniReader.addIniConfig(nodeName,configNode);
    }

    @Override
    public MongoOptions readConfig() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getIniConfig(nodeName,MongoOptions.class);
    }

    @Override
    public void removeConfig() throws Exception {
        Assert.isNotNull(nodeName,"【removeConfig NodeName Is Not Must Null】", CommonException.class);

        IniReader iniReader = new IniReader(configPath);
        iniReader.removeNodeConfig(nodeName);
    }

    @Override
    public List<ConfigNode> readAllConfigByList() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getAllConfig(new MongoOptions());
    }

    @Override
    public HashMap<String, MongoOptions> readAllConfigByMap() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getAllConfigToMap(new MongoOptions());
    }
}
