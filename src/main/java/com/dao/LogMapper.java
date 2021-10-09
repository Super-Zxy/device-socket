package com.dao;

import java.util.Map;

/**
 * 插入日志信息
 */
public interface LogMapper {

    /**
     * 插入日志信息
     *
     * @param map 日志
     */
    public void inserLog(Map map);
}
