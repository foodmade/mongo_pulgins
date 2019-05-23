package com.generate.dao.impl;

import com.generate.common.exception.CommonException;
import com.generate.dao.abs.AbsConfig;
import com.generate.model.ConfigNode;
import com.generate.utils.Assert;

/**
 * 配置中心
 */
public class KeepConfigControl extends AbsConfig {

    @Override
    public void addConfig() throws Exception {
        Assert.isNotNull(node,"【AddConfig Node Is Not Must Null】", CommonException.class);
        ConfigNode configNode = (ConfigNode)node;

    }

    @Override
    public ConfigNode readConfig() {
        return null;
    }
}
