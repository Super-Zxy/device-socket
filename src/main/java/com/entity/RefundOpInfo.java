package com.entity;

import java.util.Date;

public class RefundOpInfo {
    //操作员工号
    private Long opId;
    //操作员名称
    private String opName;
    //密码
    private String opPassword;
    //操作员权限等级
    private Integer refundLevel;
    //手机号码
    private String phoneNum;
    //归属组
    private Long groupId;
    //数据状态  0失效  1有效
    private Short status;
    //备注
    private String remark;
    //创建时间
    private Date createDate;
    //修改时间
    private Date modifyDate;
    //角色id
    private Long roleId;
    //区域
    private Integer areaId;

    private String groupDesc;
    private Long parentGroupId;
    private String parentName;

    private Integer isWeb;


    private Integer isOa;//是不是oa工号

    public long getOpId() {
        return opId;
    }

    public void setOpId(long opId) {
        this.opId = opId;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public int getRefundLevel() {
        return refundLevel;
    }

    public void setRefundLevel(int refundLevel) {
        this.refundLevel = refundLevel;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getOpPassword() {
        return opPassword;
    }

    public void setOpPassword(String opPassword) {
        this.opPassword = opPassword;
    }

    public String getGroupDesc() {
        return groupDesc;
    }

    public void setGroupDesc(String groupDesc) {
        this.groupDesc = groupDesc;
    }

    public Long getParentGroupId() {
        return parentGroupId;
    }

    public Integer getIsOa() {
        return isOa;
    }

    public void setIsOa(Integer isOa) {
        this.isOa = isOa;
    }

    public void setParentGroupId(Long parentGroupId) {
        this.parentGroupId = parentGroupId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getIsWeb() {
        return isWeb;
    }

    public void setIsWeb(Integer isWeb) {
        this.isWeb = isWeb;
    }

    @Override
    public String toString() {
        return "RefundOpInfo{" +
                "opId=" + opId +
                ", opName='" + opName + '\'' +
                ", refundLevel=" + refundLevel +
                ", opPassword=" + opPassword +
                ", phoneNum='" + phoneNum + '\'' +
                ", groupId=" + groupId +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", createDate=" + createDate +
                ", modifyDate=" + modifyDate +
                ", areaId=" + areaId +
                '}';
    }
}
