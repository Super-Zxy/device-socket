package com.util;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxy
 * @date 2020/12/16 16:24
 * @description
 */
public class YamlUtil {

    private static Map<String, Map<String, Object>> ymlMap = new HashMap<>();

    public YamlUtil(String ymlName)
    {
        Yaml yaml = new Yaml();
        try (InputStream in = YamlUtil.class.getClassLoader().getResourceAsStream(ymlName);) {
            ymlMap = yaml.loadAs(in, HashMap.class);
        } catch (Exception e) {
        }
    }

    public String getValue(String key) {
        String separator = ".";
        String[] separatorKeys = null;
        if (key.contains(separator)) {
            separatorKeys = key.split("\\.");
        } else {
            return ymlMap.get(key).toString();
        }
        Map<String, Object> finalValue = new HashMap<>();
        for (int i = 0; i < separatorKeys.length - 1; i++) {
            if (i == 0) {
                finalValue = (Map) ymlMap.get(separatorKeys[i]);
                continue;
            }
            if (finalValue == null) {
                break;
            }
            finalValue = (Map) finalValue.get(separatorKeys[i]);
        }
        if(finalValue==null)
        {
            return null;
        }
        String returnValue= (String) finalValue.get(separatorKeys[separatorKeys.length - 1]);
        return returnValue;
    }
}
