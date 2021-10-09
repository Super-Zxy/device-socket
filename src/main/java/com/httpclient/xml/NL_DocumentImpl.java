package com.httpclient.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed 文件名称：NL_DocumentImpl.java
 * 文件描述：org.dom4j.Document封装
 *
 */
public class NL_DocumentImpl implements NL_Document {

	// org.dom4j.Document
	private Document document;

	/**
	 * 构造方法
	 * 
	 * @param document
	 */
	public NL_DocumentImpl(Document document) {
		this.document = document;
	}

	/**
	 * 获取xml文档的根节点
	 *
	 * @return rootElement 根节点
	 */
	public NL_Element getRootElement() {

		Element rootElement = null;

		if (document != null) {
			rootElement = document.getRootElement();
		}

		return (NL_Element) new NL_ElementImpl(rootElement);
	}

	/**
	 * document转String报文
	 *
	 * @return strDocument字符串
	 */
	public String documentToStr() {
		String strDocument = null;

		if (document != null) {
			strDocument = document.asXML();
		}

		return strDocument;
	}

	/**
	 * 遍历文档取节点值（所取节点必须为叶子节点）
	 *
	 * @param elementName
	 *            节点名称
	 * @return List<String> 节点值集合，如节点名唯一，只有一条结果
	 */
	public List<String> getElementValueList(String elementName) {

		List<String> ls = new ArrayList<String>();

		Element rootElement = document.getRootElement();

		ls = getElementValue(rootElement, elementName, ls);

		return ls;
	}

	/**
	 * 
	 * @param parentElement
	 * @param childElementName
	 * @param ls
	 * @return
	 */
	private List<String> getElementValue(Element parentElement,
			String childElementName, List<String> ls) {

		for (Iterator i = parentElement.elementIterator(); i.hasNext();) {
			Element sonElement = (Element) i.next();
			String sonElementName = sonElement.getName();

			if (sonElementName == childElementName) {
				ls.add(sonElement.getTextTrim());
			} else {
				ls = getElementValue(sonElement, childElementName, ls);
			}
		}

		return ls;
	}

	/**
	 * 根据XPath快速找到节点
	 *
	 * @param xmlPath
	 *            （由根节点开始，例：/operatingContext/head/logIsDebug）
	 * @return 节点列表
	 */
	public List<NL_Element> getElementList(String xmlPath) {
		List<NL_Element> elementList = null;

		if (document != null && xmlPath != null) {
			System.out.print("xPath");
			elementList = document.selectNodes(xmlPath);
			System.out.print(elementList.size());
		}

		return elementList;
	}

	/**
	 * 解析XML形式的文本,得到Document对象
	 * 
	 * @param strDocument
	 *            XML形式的文本
	 * @return Document对象
	 * @throws IOException
	 */
	public NL_Document strToDocument(String strDocument) {

		// SAXReader saxReader = new SAXReader();
		Document document = null;

		try {

			// document = saxReader.read(new
			// ByteArrayInputStream(strDocument.getBytes()));
			document = DocumentHelper.parseText(strDocument);

			return (NL_Document) new NL_DocumentImpl(document);
		} catch (DocumentException e) {
			// TODOAuto-generatedcatchblock
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 设置报文编码
	 * 
	 * @param Encoding
	 */
	public void setXMLEncoding(String Encoding) {
		if (Encoding != null) {
			if (!"".equals(Encoding.trim())) {
				this.document.setXMLEncoding(Encoding.trim());
			}
		}
	}
}
