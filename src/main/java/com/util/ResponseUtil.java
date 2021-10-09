package com.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回值信息
 * Created by Administrator on 2018/11/14.
 */
public class ResponseUtil {

    /**
     * 请求返回值状态
     *
     * @param relFlag 请求是否处理成功
     * @return
     */
    public static Map getMapString(boolean relFlag) {
        Map map = new HashMap();
        map.put("success", relFlag);
        return map;
    }
}
