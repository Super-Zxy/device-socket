package com.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;

/**
 * @author zxy
 * @date 2021/10/1 22:54
 * @description
 */
@Component
public class LoadPropertiesToCache {
    @PostConstruct
    public void  init() throws Exception {
        //读取配置文件device-params.properties
        InputStream in =  new BufferedInputStream(getClass().getResourceAsStream("/device-params.properties"));
        Properties prop = new Properties();
        prop.load(new InputStreamReader(in, "UTF-8"));
        prop.load(in);
        Set<String> deviceParamSet = prop.stringPropertyNames();
        for(String deviceParam:deviceParamSet){
            LampblackConstant.deviceParams.put(deviceParam,prop.getProperty(deviceParam));
        }
    }
}
