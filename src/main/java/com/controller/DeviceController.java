package com.controller;

import com.exception.NLException;
import com.util.RespInfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zxy
 * @date 2021/10/1 14:13
 * @description 设备接口
 */
@RestController
@RequestMapping("/device")
public class DeviceController {

    private static String tag = "DeviceController====";

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);


    @RequestMapping(value = "/qryDeviceInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询设备信息", notes = "查询设备信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestMap", value = "参数", required = true, dataType = "Map")
    })
    public RespInfo qryRefundOpInfo(Map<String, Object> requestMap) throws NLException {

        logger.info(tag + "qryRefundOpInfo查询设备信息-参数：" + requestMap);

        return RespInfo.success(null);
    }
}
