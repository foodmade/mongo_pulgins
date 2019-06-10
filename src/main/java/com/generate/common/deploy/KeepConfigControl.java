package com.generate.common.deploy;

import com.abs.ConfigNode;
import com.generate.common.exception.CommonException;
import com.generate.model.ConfigConfigNode;
import com.generate.utils.Assert;
import com.generate.utils.FileUtils;
import com.generate.utils.IniReader;
import com.generate.utils.SystemPathUtil;

import java.util.HashMap;
import java.util.List;

/**
 * 配置中心  生成代码参数
 */
public class KeepConfigControl extends AbsConfig {

    public String configPath = SystemPathUtil.getProjectPath() + _GENERATE_CONFIG_NAME;

    @Override
    public void addConfig() throws Exception {
        Assert.isNotNull(configNode,"【AddConfig GenerateNode Is Not Must Null】", CommonException.class);
        ConfigConfigNode configNode = (ConfigConfigNode) this.configNode;
        FileUtils.judeFileExists(configPath);
        IniReader iniReader = new IniReader(configPath);
        iniReader.addIniConfig(nodeName,configNode);
    }

    @Override
    public ConfigConfigNode readConfig() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getIniConfig(nodeName, ConfigConfigNode.class);
    }

    @Override
    public void removeConfig() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        iniReader.removeNodeConfig(nodeName);
    }

    @Override
    public List<? extends ConfigNode> readAllConfigByList() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getAllConfig(new ConfigConfigNode());
    }

    @Override
    public HashMap<String, ? extends ConfigNode> readAllConfigByMap() throws Exception {
        IniReader iniReader = new IniReader(configPath);
        return iniReader.getAllConfigToMap(new ConfigConfigNode());
    }
}
