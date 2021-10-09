package com.config;

import com.entity.TQueryRateWebInfo;
import oracle.sql.Datum;
import oracle.sql.STRUCT;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库和java类型的数据自定义转化
 * Created by Administrator on 2018/12/17.
 */
public class CTypeHandler implements TypeHandler<Object> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Object getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(CallableStatement cs, int columnIndex) throws SQLException {
        cs.getConnection();
        // 定义结果集， CRow是我们自己定义的bean，用来与统计数量一行数据一一对应。
        List<TQueryRateWebInfo> dtos = new ArrayList<>();
        ResultSet rs = cs.getArray(columnIndex).getResultSet();
        TQueryRateWebInfo tQueryRateWeb;
        String relColoum;
        Datum[] data;
        while (rs.next()) {
            data = ((STRUCT) rs.getObject(2)).getOracleAttributes();
            tQueryRateWeb = new TQueryRateWebInfo();
            relColoum = String.valueOf(data[0]);
            tQueryRateWeb.setStrInfo(StringUtils.isNoneEmpty(relColoum) && relColoum != "null" ? relColoum.replace("\r", "<br>") : "");
            tQueryRateWeb.setConditionId(String.valueOf(data[1]));
            tQueryRateWeb.setConditionTemplateId(String.valueOf(data[2]));
            tQueryRateWeb.setFunctionId(String.valueOf(data[3]));
            // 获得list
            dtos.add(tQueryRateWeb);
        }
        return dtos;
    }
}
