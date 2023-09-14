package com.open.eoss.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Enumeration;
import java.util.Properties;

/**
 * @Author ：jelly.liu
 * @Date ：Created At 4:28 PM 2019/1/19
 * @Description：${description}
 */

public class PropertyUtil {
    private static Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    public static Properties applicationProperty;
    public static Properties customProperty;
    public static Properties properties = new Properties();

    static {
        loadApplication();
        loadCustomConfig();
    }

    private static void loadApplication(){
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yaml"));
        applicationProperty = yaml.getObject();

        logger.info("**************** LOAD CONFIG APPLICATION YAML START ****************");
        Enumeration enumeration = applicationProperty.propertyNames();
        while (enumeration.hasMoreElements()){
            String key = (String)enumeration.nextElement();
            properties.put(key, applicationProperty.getProperty(key));
            logger.info(key + "=" + applicationProperty.getProperty(key));
        }
        logger.info("**************** LOAD CONFIG APPLICATION YAML END ****************");
    }

    private static void loadCustomConfig(){
        try {
            customProperty = new Properties();
            customProperty.load(PropertyUtil.class.getClassLoader().getResourceAsStream("config.properties"));

            logger.info("**************** LOAD CONFIG CUSTOM START ****************");
            Enumeration enumeration = customProperty.propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                properties.put(key, customProperty.getProperty(key));
                logger.info(key + "=" + customProperty.getProperty(key));
            }
            logger.info("**************** LOAD CONFIG CUSTOM END ****************");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getProperty(String name){
        return properties.getProperty(name);
    }
}
