package me.zhengjie;

import org.apache.groovy.util.SystemUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by 13 on 2019/5/24.
 */
@Component
public class Constants implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static String getUploadDir() {
        Environment environment = applicationContext.getEnvironment();
        String fileUploadDir = environment.getProperty("file.upload.dir");
        return fileUploadDir;

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Constants.applicationContext = applicationContext;

    }
}
