package com.device.service;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

public interface SocketClientService {

	/**
	 * 开始一个socket客户端
	 *
	 * @param userId 用户id
	 */
	void startOneClient(String userId);

	void closeOneClient(String userId);
}
