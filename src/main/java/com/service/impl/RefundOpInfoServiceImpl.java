package com.service.impl;

import com.dao.RefundOpInfoMapper;
import com.entity.RefundOpInfo;
import com.service.RefundOpInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RefundOpInfoServiceImpl implements RefundOpInfoService {
    private final Logger logger = LoggerFactory.getLogger(RefundOpInfoServiceImpl.class);

    @Autowired
    private RefundOpInfoMapper refundOpInfoMapper;

    @Override
    public List<RefundOpInfo> qryRefundOpInfo(Map map) throws Exception {
        return refundOpInfoMapper.qryRefundOpInfo(map);
    }

    @Override
    public List<RefundOpInfo> qryRefundOpInfoErrorDB(Map map) throws Exception {
        return refundOpInfoMapper.qryRefundOpInfoErrorDB(map);
    }

    @Override
    public List<Map<String, String>> queryDatabaseInfo(Map map) throws Exception {
        return refundOpInfoMapper.qrySecurityDatabaseInfo(map);
    }

    @Override
    public List<Map<String, String>> queryDatabaseInfoDefaultDB(Map map) throws Exception {
        return refundOpInfoMapper.queryDatabaseInfoDefaultDB(map);
    }
}
