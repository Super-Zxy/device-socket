package com.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;

public class RespInfo implements Serializable {
    private static final long serialVersionUID = 3276950235199932255L;
    public static final String RESP_SUCCESS = "1";
    public static final String RESP_FAILURE = "0";
    private String respResult;
    private String respErrorType;
    @JsonInclude(Include.NON_NULL)
    private String respErrorCode;
    @JsonInclude(Include.NON_NULL)
    private String respErrorDesc;
    @JsonInclude(Include.NON_NULL)
    private Object respData;
    @JsonInclude(Include.NON_NULL)
    private Integer dataTotalCount;

    public static RespInfo success() {
        return new RespInfo();
    }

    public static RespInfo success(Object respData) {
        return new RespInfo(respData);
    }

    public static RespInfo success(Object respData, Integer dataTotalCount) {
        return new RespInfo(respData, dataTotalCount);
    }

    public static RespInfo error() {
        return new RespInfo("0", "500", (String)null, (Object)null);
    }

    public static RespInfo error(String respErrorCode, String respErrorDesc) {
        return new RespInfo(respErrorCode, respErrorDesc);
    }

    public static RespInfo error(String respErrorType,String respErrorCode, String respErrorDesc) {
        return new RespInfo(respErrorType,respErrorCode, respErrorDesc);
    }

    public RespInfo() {
        this("1", (String)null, (String)null, (String)null, (Object)null, (Integer)null);
    }

    public RespInfo(Object respData) {
        this("1", (String)null,(String)null, (String)null, respData, (Integer)null);
    }

    public RespInfo(Object respData, Integer dataTotalCount) {
        this("1", (String)null, (String)null, (String)null, respData, dataTotalCount);
    }

    public RespInfo(String respErrorCode, String respErrorDesc) {
        this("0", "0",respErrorCode, respErrorDesc, (Object)null, (Integer)null);
    }

    public RespInfo(String respErrorType,String respErrorCode, String respErrorDesc) {
        this("0", respErrorType,respErrorCode, respErrorDesc, (Object)null, (Integer)null);
    }

    public RespInfo(String respResult, String respErrorCode, String respErrorDesc, Object respData) {
        this(respResult, "0",respErrorCode, respErrorDesc, respData, (Integer)null);
    }

    public RespInfo(String respResult, String respErrorType, String respErrorCode, String respErrorDesc, Object respData, Integer dataTotalCount) {
        this.respResult = respResult;
        this.respErrorType=respErrorType;
        this.respErrorCode = respErrorCode;
        this.respErrorDesc = respErrorDesc;
        this.respData = respData;
        this.dataTotalCount = dataTotalCount;
    }

    public String getRespResult() {
        return this.respResult;
    }

    public String getRespErrorCode() {
        return this.respErrorCode;
    }

    public String getRespErrorDesc() {
        return this.respErrorDesc;
    }

    public Object getRespData() {
        return this.respData;
    }

    public Integer getDataTotalCount() {
        return this.dataTotalCount;
    }

    public void setRespResult(String respResult) {
        this.respResult = respResult;
    }

    public void setRespErrorCode(String respErrorCode) {
        this.respErrorCode = respErrorCode;
    }

    public void setRespErrorDesc(String respErrorDesc) {
        this.respErrorDesc = respErrorDesc;
    }

    public void setRespData(Object respData) {
        this.respData = respData;
    }

    public void setDataTotalCount(Integer dataTotalCount) {
        this.dataTotalCount = dataTotalCount;
    }

    public String getRespErrorType() {
        return respErrorType;
    }

    public void setRespErrorType(String respErrorType) {
        this.respErrorType = respErrorType;
    }

    @Override
    public String toString() {
        return "RespInfo{" +
                "respResult='" + respResult + '\'' +
                ", respErrorType='" + respErrorType + '\'' +
                ", respErrorCode='" + respErrorCode + '\'' +
                ", respErrorDesc='" + respErrorDesc + '\'' +
                ", respData=" + respData +
                ", dataTotalCount=" + dataTotalCount +
                '}';
    }
}
