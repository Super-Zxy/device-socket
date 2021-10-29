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
        SocketClientTest.deal();
    }

    public static void deal() {
        String userId = "dingxu";
        for (int i = 0; i < 3; i++) {
            int index = i;
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

                client.println("##0181QN=20211011123600000;ST=51;CN=2011;PW=123456;MN=DDDD;Flag=5;CP=&&DataTime=20211011123600000;ga2101-Rtd=1;ga0701-Rtd=1;a34041-Rtd=2.04;a34000-Rtd=1.41;a24088-Rtd=5.67;&&?A");
                Thread.sleep(10000);
                client.close();
            } catch (Exception e) {
                log.error(e.getMessage());
                SocketClientTest.deal();
            }
        }
    }

}
