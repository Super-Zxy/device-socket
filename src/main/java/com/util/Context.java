package com.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 常量
 * Created by Administrator on 2018/11/12.
 */
public class Context {

    private static final Logger logger = LoggerFactory.getLogger(Context.class);

    /**
     * 分割线
     */
    public static String S_FG_LINE = "===================================================================================================";

    public static Integer OPER_TYPE_SELECT = 4;
    public static Integer OPER_TYPE_COMMIT = 3;
    public static Integer OPER_TYPE_CREATE_SCRIPT = 2;
    public static Integer OPER_TYPE_SAVE = 1;


    public static void main(String[] args) {
        System.out.println(Context.getMoney("65.44"));
        System.out.println(Context.getRoundMoney("-65.44444444444"));

        System.out.println(UUID.randomUUID().toString().replace("-", ""));

        System.out.println("111_2_2".indexOf("_"));
        System.out.println("111_2_2".substring("111_2_2".indexOf("_") + 1, "111_2_2".length()));
    }

    /**
     * 获取请求中的参数
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static Map<String, Object> getRequestParam(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration enu = request.getParameterNames();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            if (StringUtils.isBlank(request.getParameter(paraName))) {
                params.put(paraName, "");
            } else {
                params.put(paraName, request.getParameter(paraName));
            }
        }
        return params;
    }

    public static String getMoney(Object money) {
        try {
            if (money != null && StringUtils.isNotEmpty(String.valueOf(money).trim())) {
                logger.info("getMoney request money:"+money);
                BigDecimal beMoney = new BigDecimal(String.valueOf(money).trim());
                BigDecimal multi = new BigDecimal(String.valueOf(1000));
                Long afMoney = beMoney.multiply(multi).longValue();

                return String.valueOf(afMoney);
            } else {
                return null;
            }
        } catch (Exception e) {
            logger.error("getMoney error:"+money+"  ====",e);

            return null;
        }
    }

    public static String getRoundMoney(String money) {
        logger.info("getRoundMoney request money:"+money);
        try {
            if (StringUtils.isNotEmpty(money)&&(!"null".equals(money))) {
                BigDecimal beMoney = new BigDecimal(money.trim());
                Double f = beMoney.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

                return String.valueOf(f);
            } else {
                return "";
            }
        } catch (Exception e) {
            logger.error("getRoundMoney error:",e);
            return "";
        }
    }

    /**
     * 提供精确的加法运算。
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     *
     * @param url
     * @return
     */
    public static boolean getAccFlag(String url){
        String[] urlAttr={"/lampblack/refunOrder/downFile","/lampblack/batchRefund/downLoadReundTemplate","/lampblack/refundRegisterCountController/downloadRefundOrderDtlFile","/lampblack/refundQualityTesting/downSystemCheckOrder","/lampblack/refundOpInfo/downOpModel"};

        boolean accFlag=false;
        for (String urlTemp:urlAttr) {
            if(url.indexOf(urlTemp) != -1){
                accFlag=true;
                break;
            }
        }

        return accFlag;
    }
}
