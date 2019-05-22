package com.generate.utils;

import com.generate.model.Annotation;
import com.generate.model.Bean;
import com.generate.model.ValNode;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.util.Date;
import java.util.List;

/**
 * 模板引擎加载器
 */
public class EngineUtils {

    /**
     * 初始化模板引擎
     * @return
     */
    public static VelocityEngine loadVelocityEngine(){
        VelocityEngine ve = new VelocityEngine();
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, EngineConst._CLASSPATH);
        ve.setProperty(EngineConst._CLASSPATH_RESOURCE_LOAD_DEFAULT_PATH, ClasspathResourceLoader.class.getName());
        ve.setProperty("input.encoding", EngineConst._DEFAULT_INPUT_FILE_ENCODING);
        ve.setProperty("output.encoding", EngineConst._DEFAULT_OUTPUT_FILE_ENCODING);
        ve.init();
        return ve;
    }

    public static VelocityContext initBeanAttr(String filePath,String packageUrl){
        VelocityContext velocityContext = new VelocityContext();

        Bean bean = new Bean();
        bean.setName(filePath);
        bean.setLowerName(CommonUtils.getLowercaseChar(bean.getName()));
        bean.setPackageUrl(packageUrl);

        Annotation annotation = new Annotation();
        annotation.setAuthorMail("2210465185@qq.com");
        annotation.setAuthorName("jackies");
        annotation.setDate(DateUtils.formatYMDHMS(new Date()));

        velocityContext.put("bean",bean);
        velocityContext.put("annotation",annotation);

        return velocityContext;
    }

    /**
     * 设置类属性
     * @param valNodeList  属性字段集合
     */
    public static void loadVelocityContext(VelocityContext velocityContext, List<ValNode> valNodeList){
        if(valNodeList == null || valNodeList.isEmpty()){
            return;
        }

        velocityContext.put("attrList",valNodeList);
    }
}
