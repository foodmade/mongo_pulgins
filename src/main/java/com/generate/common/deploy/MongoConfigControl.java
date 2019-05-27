package com.generate.common.deploy;

import com.abs.Node;
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
        Assert.isNotNull(node,"【AddConfig MongoNode Is Not Must Null】", CommonException.class);
        MongoOptions configNode = (MongoOptions) node;
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
    public List<Node> readAllConfigByList() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getAllConfig(new MongoOptions());
    }

    @Override
    public HashMap<String, MongoOptions> readAllConfigByMap() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getAllConfigToMap(new MongoOptions());
    }
}
