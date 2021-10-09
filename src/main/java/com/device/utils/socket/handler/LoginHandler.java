package com.device.utils.socket.handler;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

public interface LoginHandler {

	/**
	 * client登陆的处理函数
	 *
	 * @param userId 用户id
	 *
	 * @return 是否验证通过
	 */
	boolean canLogin(String userId);
}
