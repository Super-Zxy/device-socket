package com.service.impl;

import com.dao.LogMapper;
import com.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Administrator on 2020/1/9.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public void inserLog(Map map) {
        logMapper.inserLog(map);
    }
}
