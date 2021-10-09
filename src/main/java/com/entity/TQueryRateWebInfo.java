package com.entity;

/**
 * Created by Administrator on 2019/3/21.
 */
public class TQueryRateWebInfo {
    /**
     * 数据信息
     */
    private String strInfo;

    /**
     * 条件id
     */
    private String conditionId;

    /**
     * 条件模板id
     */
    private String conditionTemplateId;

    /**
     * 函数id
     */
    private String functionId;

    public String getStrInfo() {
        return strInfo;
    }

    public void setStrInfo(String strInfo) {
        this.strInfo = strInfo;
    }

    public String getConditionId() {
        return conditionId;
    }

    public void setConditionId(String conditionId) {
        this.conditionId = conditionId;
    }

    public String getConditionTemplateId() {
        return conditionTemplateId;
    }

    public void setConditionTemplateId(String conditionTemplateId) {
        this.conditionTemplateId = conditionTemplateId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    @Override
    public String toString() {
        return "TQueryRateWebInfo{" +
                "strInfo='" + strInfo + '\'' +
                ", conditionId=" + conditionId +
                ", conditionTemplateId=" + conditionTemplateId +
                ", functionId=" + functionId +
                '}';
    }
}
