package com.controller;

import com.entity.RefundOpInfo;
import com.exception.NLErrorCode;
import com.exception.NLException;
import com.service.RefundOpInfoService;
import com.util.RespInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 登录界面
 * Created by Administrator on 2018/12/29.
 */
@RestController
@RequestMapping("/LoginController")
public class LoginController {

    private static String tag = "LoginController====";

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RefundOpInfoService refundOpInfoService;

    @RequestMapping(value = "/qryRefundOpInfo", method = RequestMethod.GET)
    @ApiOperation(value = "测试查询操作员信息", notes = "根据参数查询操作员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestMap", value = "操作员参数", required = true, dataType = "Map")
    })
    public RespInfo qryRefundOpInfo(Map<String, Object> requestMap) throws NLException {

        logger.info(tag + "qryRefundOpInfo接收的前台的数据：" + requestMap);

        List<RefundOpInfo> refundOpInfoList = null;
        try {
            refundOpInfoList = refundOpInfoService.qryRefundOpInfo(requestMap);
            return RespInfo.success(refundOpInfoList);
        } catch (Exception e) {
            throw new NLException(NLErrorCode.ERR_CLASS_SQL, NLErrorCode.ERR_TEST_QRY_REFUND_OP_INFO, tag + "qryRefundOpInfoErrorDB失败！", e);
        }
    }

    @RequestMapping(value = "/qryRefundOpInfoErrorDB", method = RequestMethod.GET)
    @ApiOperation(value = "测试查询操作员错误数据源", notes = "根据参数测试查询操作员错误数据源")
    @ApiParam(name = "requestMap", value = "操作员参数", required = true)
    public RespInfo qryRefundOpInfoErrorDB(Map<String, Object> requestMap) throws NLException {

        logger.info(tag + "qryRefundOpInfoErrorDB接收的前台的数据：" + requestMap);

        List<RefundOpInfo> refundOpInfoList = null;
        try {
            refundOpInfoList = refundOpInfoService.qryRefundOpInfoErrorDB(requestMap);
            return RespInfo.success(refundOpInfoList);
        } catch (Exception e) {
            throw new NLException(NLErrorCode.ERR_CLASS_SQL, NLErrorCode.ERR_TEST_QRY_REFUND_OP_INFO, tag + "qryRefundOpInfoErrorDB失败！", e);
        }
    }

    @RequestMapping(value = "/querySecurityDatabaseInfo", method = RequestMethod.GET)
    @ApiOperation(value = "测试查询数据库密码信息", notes = "根据参数测试查询数据库密码信息")
    @ApiParam(name = "requestMap", value = "数据源参数", required = true)
    public RespInfo querySecurityDatabaseInfo(Map<String, Object> requestMap) throws NLException {

        logger.info(tag + "querySecurityDatabaseInfo接收的前台的数据：" + requestMap);

        List<Map<String, String>> securityDatabaseInfoList = null;
        try {
            securityDatabaseInfoList = refundOpInfoService.queryDatabaseInfo(requestMap);
            return RespInfo.success(securityDatabaseInfoList);
        } catch (Exception e) {
            throw new NLException(NLErrorCode.ERR_CLASS_SQL, NLErrorCode.ERR_TEST_QRY_SECURITY_DATABASE_INFO, tag + "querySecurityDatabaseInfo失败！", e);
        }
    }

    @RequestMapping(value = "/querySecurityDatabaseInfoDefaultDB", method = RequestMethod.GET)
    @ApiOperation(value = "测试查询数据库密码信息默认数据源", notes = "根据参数测试查询数据库密码信息默认数据源")
    @ApiParam(name = "requestMap", value = "数据源参数", required = true)
    public RespInfo querySecurityDatabaseInfoDefaultDB(Map<String, Object> requestMap) throws NLException {

        logger.info(tag + "querySecurityDatabaseInfoDefaultDB接收的前台的数据：" + requestMap);

        List<Map<String, String>> securityDatabaseInfoList = null;
        try {
            securityDatabaseInfoList = refundOpInfoService.queryDatabaseInfoDefaultDB(requestMap);
            return RespInfo.success(securityDatabaseInfoList);
        } catch (Exception e) {
            throw new NLException(NLErrorCode.ERR_CLASS_SQL, NLErrorCode.ERR_TEST_QRY_SECURITY_DATABASE_INFO, tag + "querySecurityDatabaseInfo失败！", e);
        }
    }
}