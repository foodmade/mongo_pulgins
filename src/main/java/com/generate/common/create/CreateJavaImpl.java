package com.generate.common.create;

import com.generate.model.BaseGenerateNode;
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

public class CreateJavaImpl implements ICreateJava {

    @Override
    public void createEntity(String fileName, List<ValNode> fieldList,String packageUrl,String outPath) {
        //获取模板
        Template template = loadTemplate(EngineConst._ENGINE_ENTITY_TEMPLATE_VM_DEFAULT_PATH);
        //写入Java文件
        writeJavaFile(fileName,outPath,fieldList,packageUrl,template);
    }

    @Override
    public void createDao(String fileName, String packageUrl, String outPath) {
        Template template = loadTemplate(EngineConst._ENGINE_DAO_TEMPLATE_VM_DEFAULT_PATH);
        writeJavaFile(fileName,outPath,null,packageUrl,template);
    }

    private Template loadTemplate(String vmPath){
        VelocityEngine ve = EngineUtils.loadVelocityEngine();
        return ve.getTemplate(vmPath);
    }

    private <T> void writeJavaFile(String fileName,String outPath,T fieldList,String packageUrl,Template template){
        //初始化属性
        VelocityContext velocityContext = EngineUtils.initBeanAttr(fileName,outPath);
        //添加字段
        EngineUtils.loadVelocityContext(velocityContext,fieldList);
        //写入字符串流
        StringWriter stringWriter = new StringWriter();
        String contextPath = buildFilePath(packageUrl, outPath);
        //使用模板加载
        template.merge(velocityContext, stringWriter);
        try {
            FileUtils.newFile(contextPath,fileName + ".java",stringWriter.toString(),stringWriter.toString().length(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String buildFilePath(String packageUrl,String outPath){
        return packageUrl + "/"+ outPath.replaceAll("\\.","/");
    }

    @Override
    public void createEntity(String fileName, List<ValNode> fieldList, String packageUrl, String outPath, HashMap<String, Object> otherConfig) {

    }

    @Override
    public void createEntity(String fileName, BaseGenerateNode baseGenerateNode, String packageUrl, String outPath) {
        //获取模板
        Template template = loadTemplate(EngineConst._ENGINE_ENTITY_TEMPLATE_VM_DEFAULT_PATH);
        //写入Java文件
        writeJavaFile(fileName,outPath,baseGenerateNode,packageUrl,template);
    }
}
