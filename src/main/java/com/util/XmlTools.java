package com.util;

import com.httpclient.xml.NL_Document;
import com.httpclient.xml.NL_DocumentImpl;
import com.httpclient.xml.NL_Element;
import com.httpclient.xml.NL_ElementImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
 * 
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed 
 * 文件名称：XmlTools.java
 * 文件描述：xml工具函数库
 *
 */
public class XmlTools {

	/**
	 * 过滤非法字符 （非法字符以空格替代）
	 * 
	 * @param strXml
	 *            传入的xml字符
	 * @return 过滤了非法字符的xml字符
	 */
	public static String filterIllegalChar(String strXml) {

		if (strXml != null) {
			byte[] tmp = new String(strXml).getBytes();
			for (int i = tmp.length - 1; i >= 0; i--) {
				if (tmp[i] < 32 && tmp[i] >= 0)
					tmp[i] = ' ';
			}
			return new String(tmp);
		}
		return null;
	}

	/**
	 * 创建Document对象
	 * 
	 * @param rootName
	 *            根节点名称
	 * @return Document对象
	 */
	public static NL_Document creatNL_Document(String rootName) {

		if (rootName != null) {
			if (!"".equals(rootName)) {

				Document document = DocumentHelper.createDocument();
				// 创建根节点
				document.addElement(rootName);
				return (NL_Document) new NL_DocumentImpl(document);
			}
		}

		return null;

	}

	/**
	 * 读取xml文件 转换成Document
	 * 
	 * @param xmlFilePath
	 *            xml文件路径
	 * @return Document 对象
	 */
	public static NL_Document readXmlFile(String xmlFilePath) {

		Document document;

		if (xmlFilePath != null) {
			if (!"".equals(xmlFilePath.trim())) {
				SAXReader reader = new SAXReader();
				try {

					document = reader.read(new File(xmlFilePath));
					return (NL_Document) new NL_DocumentImpl(document);

				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * 解析XML形式的文本,得到Document对象
	 * 
	 * @param strDocument
	 *            XML形式的文本
	 * @return Document对象
	 * @throws IOException
	 */
	public static NL_Document strToDocument(String strDocument)
			throws IOException {

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
	 * 把树按xml文件方式输出
	 * 
	 * @param strFile
	 *            输出的文件名
	 * @throws IOException
	 */
	public static NL_Document outputXMLFile(String strFile, String encoding)
			throws Exception {
		Document xmlDocument = null;

		OutputFormat format = null;
		XMLWriter writer = null;
		try {
			if (!"".equals(encoding)) {
				format = OutputFormat.createPrettyPrint();
				/** 指定XML编码 */
				format.setEncoding(encoding);
				writer = new XMLWriter(new FileWriter(strFile), format);
			} else {
				writer = new XMLWriter(new FileWriter(strFile));
			}
			writer.write(xmlDocument);
			return (NL_Document) new NL_DocumentImpl(xmlDocument);
		} catch (Exception e) {

		} finally {
			if (null != writer)
				writer.close();
		}
		return null;
	}

	/**
	 * 改变编码方式（src->dist）
	 * 
	 * @param str
	 * @param src
	 *            源编码方式
	 * @param dist
	 *            目标编码方式
	 * @return
	 */
	public static String convertTo(String str, String src, String dist) {
		try {
			String temp_p = str;
			byte[] temp_t = temp_p.getBytes(src);
			String temp = new String(temp_t, dist);
			return temp;
		} catch (Exception e) {
			str = "";
		}
		return str;
	}

	/**
	 * 创建NL_Element
	 * 
	 * @param elementName
	 * @return NL_Element
	 */
	public static NL_Element creatNL_Element(String elementName) {

		if (elementName != null) {
			if (!"".equals(elementName.trim())) {
				Element element = DocumentHelper.createElement(elementName
						.trim());
				return (NL_Element) new NL_ElementImpl(element);
			}
		}

		return null;
	}

	/**
	 * org.dom4j.Document 互相 org.w3c.dom.Document
	 * 
	 * @param doc
	 *            Document(org.dom4j.Document)
	 * @throws Exception
	 * @return Document
	 */
	public static org.w3c.dom.Document parseDom4jToW3c(Document doc) throws Exception {
		if (doc == null) {
			return (null);
		}
		java.io.StringReader reader = new java.io.StringReader(doc.asXML());
		org.xml.sax.InputSource source = new org.xml.sax.InputSource(reader);
		javax.xml.parsers.DocumentBuilderFactory documentBuilderFactory = javax.xml.parsers.DocumentBuilderFactory
				.newInstance();
		javax.xml.parsers.DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		return (documentBuilder.parse(source));
	}

}
