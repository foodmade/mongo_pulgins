package com.generate.dao.impl;

import com.generate.dao.CreateJava;
import com.generate.model.ValNode;
import com.generate.utils.EngineConst;
import com.generate.utils.EngineUtils;
import com.generate.utils.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

public class CreateJavaImpl implements CreateJava {


    @Override
    public void createEntity(String fileName, List<ValNode> fieldList,String packageUrl,String outPath) {

        //获取模板引擎
        VelocityEngine ve = EngineUtils.loadVelocityEngine();
        //获取模板
        Template template = ve.getTemplate(EngineConst._ENGINE_TEMPLATE_VM_DEFAULT_PATH);
        //初始化属性
        VelocityContext velocityContext = EngineUtils.initBeanAttr(fileName,outPath);
        //添加字段
        EngineUtils.loadVelocityContext(velocityContext,fieldList);
        //写入字符串流
        StringWriter stringWriter = new StringWriter();
        String contextPath = packageUrl + "/"+ outPath.replaceAll("\\.","/");

        //使用模板加载
        template.merge(velocityContext, stringWriter);
        try {
            FileUtils.newFile(contextPath,fileName + ".java",stringWriter.toString(),stringWriter.toString().length(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createEntity(String fileName, List<ValNode> fieldList, String packageUrl, String outPath, HashMap<String, Object> otherConfig) {

    }
}
