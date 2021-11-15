package com.config;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
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
        //读取配置文件device-mn.properties
        InputStream in=new BufferedInputStream(getClass().getResourceAsStream("/device-mn.properties"));
        Properties  prop = new Properties();
        prop.load(new InputStreamReader(in, "UTF-8"));
        prop.load(in);
        Set<String> deviceMnSet = prop.stringPropertyNames();
        for(String deviceType:deviceMnSet){
            LampblackConstant.deviceMnMap.put(deviceType,prop.getProperty(deviceType));

            InputStream deviceParamIn =  new BufferedInputStream(getClass().getResourceAsStream("/"+deviceType+"-params.properties"));
            Properties deviceParamProp = new Properties();
            deviceParamProp.load(new InputStreamReader(deviceParamIn, "UTF-8"));
            deviceParamProp.load(deviceParamIn);
            Set<String> deviceParamSet = deviceParamProp.stringPropertyNames();

            HashMap<String,String> deviceParams=new HashMap<>();
            for(String deviceParam:deviceParamSet){
                deviceParams.put(deviceParam,deviceParamProp.getProperty(deviceParam));
            }
            LampblackConstant.deviceTypeParamMap.put(deviceType,deviceParams);
        }

    }
}
