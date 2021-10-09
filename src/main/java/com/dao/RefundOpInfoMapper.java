package com.dao;

import com.config.DataSourceAnnotation;
import com.config.DataSourceConstant;
import com.entity.RefundOpInfo;

import java.util.List;
import java.util.Map;

public interface RefundOpInfoMapper {

    @DataSourceAnnotation(dataSourceName = DataSourceConstant.LAMPBLACK_DB,dataSourceWeight = DataSourceConstant.DB_NAME_WEIGHT)
    List<RefundOpInfo> qryRefundOpInfo(Map map) throws Exception;

    @DataSourceAnnotation(dataSourceName = DataSourceConstant.DB11_SUB_SYS_BOSSPUB,dataSourceWeight = DataSourceConstant.DB_NAME_WEIGHT)
    List<RefundOpInfo> qryRefundOpInfoErrorDB(Map map) throws Exception;

    @DataSourceAnnotation(tableName = "security_database_info")
    List<Map<String,String>> qrySecurityDatabaseInfo(Map map) throws Exception;

    @DataSourceAnnotation
    List<Map<String,String>> queryDatabaseInfoDefaultDB(Map map) throws Exception;

}
