package com.httpclient.xml;

import java.io.IOException;
import java.util.List;

/**
 * 
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed 
 * 文件名称：NL_Document.java
 * 文件描述：org.dom4j.Document封装
 *
 */
public interface NL_Document {

	/**
	 * 获取xml文档的根节点
	 *
	 * @return rootElement 根节点
	 */
	public NL_Element getRootElement();

	/**
	 * document转String报文
	 *
	 * @return strDocument字符串
	 */
	public String documentToStr();

	/**
	 * 遍历文档取节点值（所取节点必须为叶子节点）
	 *
	 * @param elementName
	 *            节点名称
	 * @return List<String> 节点值集合，如节点名唯一，只有一条结果
	 */
	public List<String> getElementValueList(String elementName);

	/**
	 * 根据XPath快速找到节点
	 *
	 * @param xmlPath
	 *            （由根节点开始，例：/operatingContext/head/logIsDebug）
	 * @return 节点列表
	 */
	public List<NL_Element> getElementList(String xmlPath);

	/**
	 * 设置报文编码
	 * 
	 * @param Encoding
	 */
	public void setXMLEncoding(String Encoding);

	/**
	 * 解析XML形式的文本,得到Document对象
	 * 
	 * @param strDocument
	 *            XML形式的文本
	 * @return Document对象
	 * @throws IOException
	 */
	public NL_Document strToDocument(String strDocument);
}
