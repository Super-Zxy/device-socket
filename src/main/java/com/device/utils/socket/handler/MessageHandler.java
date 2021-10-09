package com.device.utils.socket.handler;

import com.device.utils.socket.dto.ServerReceiveDto;
import com.device.utils.socket.server.Connection;

/**
 * 处理消息的接口
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

public interface MessageHandler {

	/**
	 * 获得消息的处理函数
	 *
	 * @param connection 封装了客户端的socket
	 * @param dto        接收到的dto
	 */
	void onReceive(Connection connection, ServerReceiveDto dto);
}