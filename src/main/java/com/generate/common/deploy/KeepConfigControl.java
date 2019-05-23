package com.generate.common.deploy;

import com.generate.common.exception.CommonException;
import com.generate.model.ConfigNode;
import com.generate.utils.Assert;
import com.generate.utils.FileUtils;
import com.generate.utils.IniReader;

/**
 * 配置中心
 */
public class KeepConfigControl extends AbsConfig {

    @Override
    public void addConfig() throws Exception {
        Assert.isNotNull(node,"【AddConfig Node Is Not Must Null】", CommonException.class);
        ConfigNode configNode = (ConfigNode)node;
        FileUtils.judeFileExists(configPath);
        IniReader iniReader = new IniReader(configPath);
        iniReader.addIniConfig(nodeName,configNode);
    }

    @Override
    public ConfigNode readConfig() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getIniConfig(nodeName,ConfigNode.class);
    }
}
