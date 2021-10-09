package com.exception;

/**
 * @author zxy
 * @date 2021/7/2 9:39
 * @description
 */
public class NLErrorCode {
    //------------------------错误大类定义------------------------
    public static final int ERR_CLASS_SUCCESS = 0;                                  //成功

    public static final int ERR_CLASS_SUBSYS = 1;                                  //子系统内部业务错误

    public static final int ERR_CLASS_SQL = -1;                                 //数据库错误

    public static final int ERR_CLASS_ERRNO = -2;                                 //操作系统错误

    public static final int ERR_CLASS_ERRCODE = -3;                                 //中间件错误

    public static final int ERR_CLASS_TIMEOUT = -4;                                 //超时错误

    public static final int ERR_CLASS_PAKERR = -5;                                 //数据报文格式错误

    public static final int ERR_CLASS_OTHER = -6;                                 //其他异常

    //===================测试异常码=====================
    public static final int ERR_TEST = -11000;
    public static final int ERR_TEST_QRY_SECURITY_DATABASE_INFO = ERR_TEST - 1;               //查询SECURITY_DATABASE_INFO错误
    public static final int ERR_TEST_QRY_REFUND_OP_INFO = ERR_TEST - 2;               //查询REFUND_OP_INFO错误

}
