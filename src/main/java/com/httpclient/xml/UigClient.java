package com.httpclient.xml;

import com.config.LampblackConstant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * UIG服务调用
 *
 * @author zxy
 *
 */
public class UigClient {

    private final static Logger log = LoggerFactory.getLogger(UigClient.class);

	/**
	 * http服务地址
	 */
	private String url;

	/**
	 * 字符编码
	 */
	private String charSet = "UTF-8";

	/**
	 *
	 * @param url
	 */
	public UigClient(String url) {
		this.url = url;
	}


	/**
	 * 请求服务
	 *
	 * @param xml
	 * @return
	 */
	public String requestService(String xml) throws Exception {
		return postMessage(xml,0);
	}

	private String postMessage(String xml,int sendTimes) throws Exception {
		PostMethod post = null;
		String resultMsg = "";
		try {
			post = new PostMethod(url);
			post.setRequestHeader("Content-type", "application/xml; charset=" + charSet);
			post.setRequestHeader("Connection", "close");
			//设置期望返回的报文头编码
			post.setRequestHeader("Accept", "*/*");

			post.setRequestBody(xml);
			HttpClient httpclient = new HttpClient();
			httpclient.getHttpConnectionManager().getParams().setConnectionTimeout(LampblackConstant.HTTP_TIME_OUT);
			httpclient.getHttpConnectionManager().getParams().setSoTimeout(LampblackConstant.HTTP_TIME_OUT);
			HttpClientParams hp = new HttpClientParams();
			hp.setContentCharset(charSet);
			httpclient.setParams(hp);
			int result = httpclient.executeMethod(post);
			switch (result) {
				case HttpStatus.SC_OK:
					resultMsg = post.getResponseBodyAsString();
					break;
				default:
					break;
			}
            if (null != post) {
                post.releaseConnection();
            }
            return resultMsg;
		} catch (Exception e) {
			throw e;
		}
	}
}
