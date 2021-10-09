package com.httpclient.wtc;


import com.dao.CallUigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed 文件名称：BaseHeadInfo.java
 * 文件描述：tuxedo头报文对象
 *
 * @author zhangyang
 * @date 2009-10-27
 */
@Service
public class TuxedoHeadInfo implements java.io.Serializable {
    private static TuxedoHeadInfo tuxedoHeadInfo = new TuxedoHeadInfo();

    @Autowired
    CallUigMapper callUigMapper;

    @PostConstruct
    public void init() {
        tuxedoHeadInfo.callUigMapper = callUigMapper;
    }


    private static final long serialVersionUID = 1L;

    private String process_code;

    private String verify_code;

    private String city_id;

    private String req_type;

    private String req_seq;

    private String req_source;

    private String req_time;

    private String oper_id;

    private String org_id;

    private String req_channel;

    private String accept_seq;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getOper_id() {
        return oper_id;
    }

    public void setOper_id(String oper_id) {
        this.oper_id = oper_id;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getProcess_code() {
        return process_code;
    }

    public void setProcess_code(String process_code) {
        this.process_code = process_code;
    }

    public String getReq_seq() {
        return req_seq;
    }

    public String getReq_time() {
        return req_time;
    }

    public String getReq_type() {
        return req_type;
    }

    public void setReq_type(String req_type) {
        this.req_type = req_type;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public TuxedoHeadInfo(String process_code, String city_id, String req_type,
                          String req_channel, String oper_id, String org_id, String req_Source) throws Exception {
        super();

        this.process_code = process_code;
        this.verify_code = TuxedoConst.VERIFY_CODE;
        this.city_id = city_id;
        String call_service_srl = tuxedoHeadInfo.callUigMapper.getCallServiceSrl();

        this.req_type = req_type;
        this.req_seq = call_service_srl;
        this.req_source = req_Source;
        this.req_time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        this.oper_id = oper_id;
        this.org_id = org_id;


        this.req_channel = "1";

        this.accept_seq = call_service_srl;

    }

    public TuxedoHeadInfo(String process_code, String city_id, String req_type,
                          String req_channel, String oper_id, String org_id)
            throws Exception {
        super();

        this.process_code = process_code;
        this.verify_code = TuxedoConst.VERIFY_CODE;
        this.city_id = city_id;
        String call_service_srl = tuxedoHeadInfo.callUigMapper.getCallServiceSrl();

        this.req_type = req_type;
        this.req_seq = call_service_srl;
        this.req_source = "1";
        this.req_time = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        this.oper_id = oper_id;
        this.org_id = org_id;
        this.req_channel = "1";
        this.accept_seq = call_service_srl;

    }

    public TuxedoHeadInfo() {
        super();
    }

    public String getReq_source() {
        return req_source;
    }

    public void setReq_source(String req_source) {
        this.req_source = req_source;
    }

    public String getReq_channel() {
        return req_channel;
    }

    public String getAccept_seq() {
        return accept_seq;
    }

}