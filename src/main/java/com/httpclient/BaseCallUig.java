package com.httpclient;

import com.httpclient.wtc.CrmHeadInfo;
import com.httpclient.wtc.TuxedoHeadInfo;
import com.httpclient.xml.NL_Document;
import com.httpclient.xml.NL_Element;
import com.util.StringTools;
import com.util.XmlTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseCallUig {
	public static Logger logger = LoggerFactory.getLogger(BaseCallUig.class);

	public NL_Document CallUig(TuxedoHeadInfo tuxedoHeadInfo,
							   NL_Element contentElement, String city_id) throws Exception {

		NL_Document reNL_Document;
		if (!verifyRequestMessage(tuxedoHeadInfo)) {
			logger.debug(" verifyRequestMessage tuxedoHeadInfo return false!! ");
			return null;
		}
		String requestMessage = creatRequestMessage(tuxedoHeadInfo,
				contentElement);
		//uig不用再转换了，bv,tuxedo环境是iso-18859-1的，jsp,uig是gb2312的。
		//所以bv从jsp得到的gb2312的转成iso8859-1的调tuxedo插的表是中文，转过后打印成日志也是中文。
		//调uig的时候，从jsp获取到的是gb2312,而uig又是gb2312就不需要转换了。但是日志里就乱了，为了打出中文的日志，可以转换下再打印日志。
		logger.info("--uig log chinese-----"+ StringTools.get8859ByGBK((String) requestMessage));
		logger.info("---uig requestMessage---"+requestMessage);
		int url_flag = getUigUrl(city_id);
		UigClient uigClient = new UigClient(url_flag);
		logger.info("..--uig begin--..");
		String responseXml = uigClient.requestService(requestMessage);
		// 打印出返回报文
		logger.info("---uig responseXML---" + responseXml);
		reNL_Document = XmlTools.strToDocument(responseXml.trim());
		return reNL_Document;
	}

	public NL_Document CallUig(CrmHeadInfo crmHeadInfo,
							   NL_Element contentElement, String city_id) throws Exception {

		NL_Document reNL_Document;

		String requestMessage = creatRequestMessage(crmHeadInfo,
				contentElement);
		//uig不用再转换了，bv,tuxedo环境是iso-18859-1的，jsp,uig是gb2312的。
		//所以bv从jsp得到的gb2312的转成iso8859-1的调tuxedo插的表是中文，转过后打印成日志也是中文。
		//调uig的时候，从jsp获取到的是gb2312,而uig又是gb2312就不需要转换了。但是日志里就乱了，为了打出中文的日志，可以转换下再打印日志。
		logger.info("--uig log chinese-----"+ StringTools.get8859ByGBK((String) requestMessage));
		logger.info("---uig requestMessage---"+requestMessage);
		int url_flag = getCrmUigUrl(city_id);
		UigClient uigClient = new UigClient(url_flag);
		logger.info("..--uig begin--..");
		String responseXml = uigClient.requestService(requestMessage);
		// 打印出返回报文
		logger.info("---uig responseXML---" + responseXml);
		reNL_Document = XmlTools.strToDocument(responseXml.trim());
		return reNL_Document;
	}
	public BaseCallUig() {
	}

	/**
	 * 生成请求报文
	 * 
	 * @param tuxedoHeadInfo
	 * @return
	 */
	private String creatRequestMessage(TuxedoHeadInfo tuxedoHeadInfo,
			NL_Element contentElement) {

		// 初始化报文，设置根节点
		NL_Document requestMessageDocument = null;
		requestMessageDocument = XmlTools.creatNL_Document("operation_in");
		NL_Element rootElement = requestMessageDocument.getRootElement();

		// 报文头信息
		rootElement.addChildElement("process_code", tuxedoHeadInfo.getProcess_code());

		rootElement.addChildElement("verify_code", tuxedoHeadInfo.getVerify_code());

		rootElement.addChildElement("city_id", tuxedoHeadInfo.getCity_id());

		rootElement.addChildElement("req_type", tuxedoHeadInfo.getReq_type());
		rootElement.addChildElement("req_seq", tuxedoHeadInfo.getReq_seq());
		rootElement.addChildElement("req_source", tuxedoHeadInfo.getReq_source());

		rootElement.addChildElement("req_time", tuxedoHeadInfo.getReq_time());
		rootElement.addChildElement("oper_id", tuxedoHeadInfo.getOper_id());
		rootElement.addChildElement("org_id", tuxedoHeadInfo.getOrg_id());

		rootElement.addChildElement("req_channel", tuxedoHeadInfo.getReq_channel());
		rootElement.addChildElement("accept_seq", tuxedoHeadInfo.getAccept_seq());

		rootElement.addChildElement("app_id", "100000000003");
		rootElement.addChildElement("access_token", "L65a6L0089j3jyw2lo91");
		rootElement.addChildElement("sign", "");

		rootElement.addChildElement("request_source", tuxedoHeadInfo.getReq_source());
		rootElement.addChildElement("route_value", tuxedoHeadInfo.getCity_id());
		rootElement.addChildElement("request_seq", "");
		rootElement.addChildElement("request_time", "");
		rootElement.addChildElement("request_type", "1");
		rootElement.addChildElement("route_type", "1");
		rootElement.addChildElement("organ_id", "");
		rootElement.addChildElement("sysfunc_id", "");


		// 设置报文体
		rootElement.addChildElement(contentElement);

		// 转String
		requestMessageDocument.setXMLEncoding("GBK");
		String strRequestMessage = requestMessageDocument.documentToStr();

		return strRequestMessage;
	}
	private String creatRequestMessage(CrmHeadInfo crmHeadInfo,
									   NL_Element contentElement) {

		// 初始化报文，设置根节点
		NL_Document requestMessageDocument = null;
		requestMessageDocument = XmlTools.creatNL_Document("operation_in");
		NL_Element rootElement = requestMessageDocument.getRootElement();

		// 报文头信息
		rootElement.addChildElement("process_code", crmHeadInfo.getProcess_code());

		rootElement.addChildElement("access_token", crmHeadInfo.getAccess_token());

		rootElement.addChildElement("app_id", crmHeadInfo.getApp_id());

		rootElement.addChildElement("route_value", crmHeadInfo.getRoute_value());
		rootElement.addChildElement("route_type", crmHeadInfo.getRoute_type());

		rootElement.addChildElement("login_msisdn", crmHeadInfo.getLogin_msisdn());
		rootElement.addChildElement("organ_id", crmHeadInfo.getOrgan_id());
		rootElement.addChildElement("request_source", "");
		rootElement.addChildElement("request_seq", "");
		rootElement.addChildElement("request_type", "");



		// 设置报文体
		rootElement.addChildElement(contentElement);

		// 转String
		requestMessageDocument.setXMLEncoding("GBK");
		String strRequestMessage = requestMessageDocument.documentToStr();

		return strRequestMessage;
	}
	/**
	 * 报文头验证
	 * 
	 * @param tuxedoHeadInfo
	 * @return
	 */
	private boolean verifyRequestMessage(TuxedoHeadInfo tuxedoHeadInfo) {
		boolean succ = false;

		if (tuxedoHeadInfo != null) {
			if (tuxedoHeadInfo.getCity_id() != null
					&& tuxedoHeadInfo.getOper_id() != null
					&& tuxedoHeadInfo.getOrg_id() != null
					&& tuxedoHeadInfo.getProcess_code() != null
					&& tuxedoHeadInfo.getReq_source() != null
					&& tuxedoHeadInfo.getReq_seq() != null
					&& tuxedoHeadInfo.getReq_time() != null
					&& tuxedoHeadInfo.getReq_type() != null
					&& tuxedoHeadInfo.getVerify_code() != null
					&& tuxedoHeadInfo.getAccept_seq() != null
					&& tuxedoHeadInfo.getReq_channel() != null) {
				if (!"".endsWith(tuxedoHeadInfo.getCity_id().trim())
						&& !"".endsWith(tuxedoHeadInfo.getOper_id().trim())
						&& !"".endsWith(tuxedoHeadInfo.getOrg_id().trim())
						&& !""
								.endsWith(tuxedoHeadInfo.getProcess_code()
										.trim())
						&& !"".endsWith(tuxedoHeadInfo.getReq_source().trim())
						&& !"".endsWith(tuxedoHeadInfo.getReq_seq().trim())
						&& !"".endsWith(tuxedoHeadInfo.getReq_time().trim())
						&& !"".endsWith(tuxedoHeadInfo.getReq_type().trim())
						&& !"".endsWith(tuxedoHeadInfo.getVerify_code().trim())
						&& !"".endsWith(tuxedoHeadInfo.getAccept_seq().trim())
						&& !"".endsWith(tuxedoHeadInfo.getReq_channel().trim())) {
					succ = true;
				}

			}
		}

		return succ;
	}
	private int getUigUrl(String city_id) throws Exception {
		int u_flag=0;
		if(!"".equals(city_id)){
			if("20".equals(city_id) || "23".equals(city_id)){
				u_flag= 11;
			}else if("21".equals(city_id) || "22".equals(city_id)){
				u_flag= 12;
			}else if("19".equals(city_id)){
				u_flag= 21;
			}else if("17".equals(city_id) || "12".equals(city_id) || "18".equals(city_id)){
				u_flag= 22;
			}else if("14".equals(city_id)){
				u_flag= 31;
			}else if("16".equals(city_id) || "15".equals(city_id) || "13".equals(city_id)){
				u_flag= 32;
			}else if("11".equals(city_id)){
				u_flag= 42;
			}
		}
		return u_flag;
	}
    //华为鉴权接口
	private int getCrmUigUrl(String city_id) throws Exception {
		int u_flag=0;
		if(!"".equals(city_id)){
			if("20".equals(city_id) || "23".equals(city_id) || "22".equals(city_id) || "21".equals(city_id)){
				u_flag= 111;
			}else if("18".equals(city_id) || "12".equals(city_id) || "17".equals(city_id) || "19".equals(city_id)){
				u_flag= 112;
			}else if("14".equals(city_id) || "16".equals(city_id) || "15".equals(city_id) || "13".equals(city_id)){
				u_flag= 113;
			} else if("11".equals(city_id)){
				u_flag= 114;
			}
		}
		return u_flag;
	}
}