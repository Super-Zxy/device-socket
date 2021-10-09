package com.device.utils.socket.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

@Data
public class ClientSendDto implements Serializable {

	private static final long serialVersionUID = 97085384412852967L;

	/**
	 * 功能码 0 心跳 1 登陆 2 登出 3 发送消息
	 */
	private Integer functionCode;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 这边假设是string的消息体
	 */
	private String message;

}
