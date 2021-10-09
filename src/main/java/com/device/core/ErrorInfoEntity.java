package com.device.core;

/**
 * Created With User-Center
 *
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */
public interface ErrorInfoEntity {

	/**
	 * 获取错误信息
	 *
	 * @return 错误信息
	 */
	String getErrorMsg();

	/**
	 * 获取错误码
	 *
	 * @return 错误码
	 */
	Integer getErrorCode();
}
