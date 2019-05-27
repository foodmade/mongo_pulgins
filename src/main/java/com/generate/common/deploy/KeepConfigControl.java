package com.generate.common.deploy;

import com.abs.Node;
import com.generate.common.exception.CommonException;
import com.generate.model.ConfigNode;
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
        Assert.isNotNull(node,"【AddConfig GenerateNode Is Not Must Null】", CommonException.class);
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

    @Override
    public List<Node> readAllConfigByList() {
        return null;
    }

    @Override
    public HashMap<String, Node> readAllConfigByMap() throws Exception {
        return null;
    }
}
