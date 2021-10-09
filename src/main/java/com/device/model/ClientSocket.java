package com.device.model;

import com.device.utils.socket.client.SocketClient;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.ScheduledExecutorService;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

@Data
@AllArgsConstructor
public class ClientSocket {

	private SocketClient socketClient;

	private ScheduledExecutorService clientHeartExecutor;
}
