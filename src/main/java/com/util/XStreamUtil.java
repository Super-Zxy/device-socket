package com.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import org.apache.commons.lang3.StringUtils;

/**
 * javaBean和xml相互转换
 * Created by Administrator on 2018/11/13.
 */
public class XStreamUtil {

    /**
     * 把javaBean转化成xml的string格式
     *
     * @param object 类
     * @return xml的string
     */
    public static String getXmlString(Object object) {
        if (object != null) {
            //创建xStream对象
            XStream xStream = new XStream();
            xStream.autodetectAnnotations(true);
            return xStream.toXML(object);
        } else {
            return null;
        }

    }

    /**
     * 把xml字符串转化成对象
     *
     * @param xmlString xml字符串
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObjectFromXml(String xmlString, Class<T> clazz) {
        if (StringUtils.isNotEmpty(xmlString)) {
            XStream xstream = new XStream(new DomDriver());
            xstream.processAnnotations(clazz);
            Object object = xstream.fromXML(xmlString);
            T cast = clazz.cast(object);
            return cast;
        } else {
            return null;
        }
    }
}
