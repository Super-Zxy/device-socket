package com.device;

import com.device.utils.socket.client.SocketClient;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zxy
 * @date 2021/10/1 19:33
 * @description
 */

@Slf4j
public class SocketClientTest {

	public static void main(String[] args) {
		ExecutorService clientService = Executors.newCachedThreadPool();
		String userId = "dingxu";
		for (int i = 0; i < 2; i++) {
			int index = i;
			clientService.execute(() -> {
				try {
					SocketClient client;
					client = new SocketClient(InetAddress.getByName("localhost"), 8068);
////					登陆
//					ClientSendDto dto = new ClientSendDto();
//					dto.setFunctionCode(FunctionCodeEnum.LOGIN.getValue());
//					dto.setUserId(userId + index);
//					client.println(JSONObject.toJSONString(dto));
//					ScheduledExecutorService clientHeartExecutor = Executors.newSingleThreadScheduledExecutor(
//							r -> new Thread(r, "socket_client+heart_" + r.hashCode()));
//					clientHeartExecutor.scheduleWithFixedDelay(() -> {
//						try {
//							ClientSendDto heartDto = new ClientSendDto();
//							heartDto.setFunctionCode(FunctionCodeEnum.HEART.getValue());
//							client.println(JSONObject.toJSONString(heartDto));
//						} catch (Exception e) {
//							log.error("客户端异常,userId:{},exception：{}", userId, e.getMessage());
//							client.close();
//						}
//					}, 0, 5, TimeUnit.SECONDS);
					while (true){
						client.println("MN=123456;DataTime=20190312113200001;a01011-Rtd=11;a01012-Rtd=22;a01013-Rtd=33;a01014-Rtd=44;a01017-Rtd=55;a05024-Rtd=66;a19002-Rtd=77;a24088-Rtd=88;a34000-Rtd=99;a34041-Rtd=100;ga0601-Rtd=110;ga0611-Rtd=120;ga0701-Rtd=130;ga0801-Rtd=140;ga2001-Rtd=150;ga2011-Rtd=160;ga2101-Rtd=170;ga2201-Rtd=180;");
						Thread.sleep(10000);
					}
				} catch (Exception e) {
					log.error(e.getMessage());
				}

			});
		}
	}

}
