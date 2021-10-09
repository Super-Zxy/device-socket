package com.httpclient.wtc;


/**
 * 
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed 文件名称：TuxedoConst.java
 * 文件描述：Tuxedo静态常量定义
 * 
 * @author zhangyang
 * @date 2009-10-29
 */
public class TuxedoConst implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 验证码 双方约定BOSS提供,定期修改
	public static String VERIFY_CODE = "VERIFY_CODE";

	// 请求类型定义: 查询不涉及数据变更
	public static String REQUEST_SELECT = "01";

	// 请求类型定义: 业务涉及到数据变更
	public static String REQUEST_DEAL = "02";

	

	
}
