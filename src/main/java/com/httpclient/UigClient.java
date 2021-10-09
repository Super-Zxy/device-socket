package com.httpclient;

import com.httpclient.xml.NL_Element;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

import java.io.InputStream;
import java.util.Properties;


/**
 * 厂商暂时先写2个，如果以后业务需要，同学们可以根据具体情况增加厂商类型并且在配置文件中添加相应的节点
 */
public class UigClient {

    /**
     * http服务地址
     */
    private String url;

    /**
     * 字符编码
     */
    private String charSet = "GBK";

    /**
     * 厂商: 华为
     */
    public final static int HUAWEI = 1;

    /**
     * 厂商: 新大陆
     */
    public final static int NL = 2;

    /**
     * @param url
     */
    public UigClient(String url) {
        this.url = url;
    }

    /**
     * @param factory
     */
    public UigClient(int factory) throws Exception {
        switch (factory) {
            case HUAWEI:
                url = getAddr("addr_huawei");
                break;
            case NL:
                url = getAddr("addr_nl");
                break;
            case 11:
                url = getAddr("addr_11");
                break;
            case 12:
                url = getAddr("addr_12");
                break;
            case 21:
                url = getAddr("addr_21");
                break;
            case 22:
                url = getAddr("addr_22");
                break;
            case 31:
                url = getAddr("addr_31");
                break;
            case 32:
                url = getAddr("addr_32");
                break;
            case 42:
                url = getAddr("addr_42");
                break;
            case 111:
                url = getAddr("addr_111");
                break;
            case 112:
                url = getAddr("addr_112");
                break;
            case 113:
                url = getAddr("addr_113");
                break;
            case 114:
                url = getAddr("addr_114");
                break;
            default:
                url = getAddr("addr_11");
                break;
        }
    }

    /**
     * 请求服务
     *
     * @param element
     * @return
     */
    public String requestService(NL_Element element) throws Exception {
        return postMessage(element.elementToStr());
    }

    /**
     * 请求服务
     *
     * @param xml
     * @return
     */
    public String requestService(String xml) throws Exception {
        return postMessage(xml);
    }

    private String postMessage(String xml) throws Exception {
        PostMethod post = null;
        String resultMsg = "";
        try {
            post = new PostMethod(url);
            post.setRequestHeader("Content-type", "text/xml; charset=" + charSet);
            post.setRequestBody(xml);
            HttpClient httpclient = new HttpClient();
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != post) {
                post.releaseConnection();
            }
        }

        return resultMsg;
    }

    /**
     * 根据配置获取服务器端的地址
     *
     * @return
     */
    private synchronized String getAddr(String key) throws Exception {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream("/config.properties");
        prop.load(in);
        return (String) prop.get(key);
    }
}
