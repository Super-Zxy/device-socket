package com.device;

import com.device.utils.socket.client.SocketClient;
import com.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
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
        for (int i = 0; i < 1; i++) {
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
                String loginMsg="##0400QN=20121231010101001;ST=91;CN=9021;PW=123456;MN=22990569424231;Flag=1;CP=&&&&";
                String getTimeMsg="##0400QN=20121231010101001;ST=31;CN=1011;PW=123456;MN=22990569424231;Flag=1;CP=&&&&";
                String sendMsg="##0400ST=31;CN=2011;PW=000000;MN=22990569424231;CP=&&DataTime=20211105140700;SB0-Rtd=00.00;SB0-RS=3;SB0-Class=4;SB1-Rtd=00.00;SB1-RS=2;B01-Rtd=00.58;B01-ZsAvg=00.58;B01-Class=3;B01-Flag=N;B03-Rtd=00.09;B03-ZsAvg=00.09;B03-Class=3;B03-Flag=N;B02-Rtd=00.13;B02-ZsAvg=00.13;B02-Class=3;B02-Flag=N;B04-Rtd=00.00;B04-ZsAvg=00.00;B04-Flag=U;B05-Rtd=00.00;S03-Rtd=024.0;S05-Rtd=061.0;S08-Rtd=101.3;S02-Rtd=0";

//                //删除##0030头,以;分隔数据
//                sendMsg=sendMsg.substring(6);
//                String soutStr="";
//                String[] clinetInputHeadArray=sendMsg.split("CP=&&");
//                soutStr=clinetInputHeadArray[0];
//                String[] clinetInputCPArray=clinetInputHeadArray[1].split("&&");
//                if(StringUtils.isNotEmpty(clinetInputCPArray[0])){
//                    soutStr+=clinetInputCPArray[0]+";";
//                }

//                System.out.println(soutStr);


                client.println(loginMsg);
                Thread.sleep(5000);
                client.println(getTimeMsg);
                Thread.sleep(5000);
                client.println(sendMsg);
//                String returnMsg=client.readLine();
//                System.out.println(returnMsg);


                Thread.sleep(10000);
                client.close();
            } catch (Exception e) {
                log.error(e.getMessage());
                SocketClientTest.deal();
            }
        }
    }

}
