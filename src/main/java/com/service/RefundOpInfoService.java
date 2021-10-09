package com.service;

import com.config.DataSourceAnnotation;
import com.entity.RefundOpInfo;

import java.util.List;
import java.util.Map;

public interface RefundOpInfoService {
    /**
     * 查询固定条件的操作员信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    List<RefundOpInfo> qryRefundOpInfo(Map map) throws Exception;

    List<RefundOpInfo> qryRefundOpInfoErrorDB(Map map) throws Exception;

    List<Map<String,String>> queryDatabaseInfo(Map map) throws Exception;

    List<Map<String,String>> queryDatabaseInfoDefaultDB(Map map) throws Exception;
}
