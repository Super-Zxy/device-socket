package com.httpclient.wtc;

import com.dao.CallUigMapper;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Data
@Accessors(chain = true)
@Service
public class CrmHeadInfo {
    private static CrmHeadInfo crmHeadInfo = new CrmHeadInfo();

    @Autowired
    CallUigMapper callUigMapper;

    @PostConstruct
    public void init() {
        crmHeadInfo.callUigMapper = callUigMapper;
    }


    private static final long serialVersionUID = 1L;

    private String process_code;

    private String access_token;

    private String app_id;

    private String route_value;

    private String route_type;

    private String login_msisdn;

    private String sign;

    private String verify_code;

    private String sysfunc_id;

    private String operator_id;

    private String organ_id;

    private String request_time;

    private String request_seq;

    private String request_source;

    private String request_target;

    private String msg_version;

    private String cont_version;

    private String user_passwd;

    private String operator_ip;

    private String channelid;


}
