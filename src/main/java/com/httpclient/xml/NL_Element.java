package com.httpclient.xml;

import java.util.HashMap;
import java.util.List;

/**
 * 
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed 文件名称：NL_Element.java
 * 文件描述：org.dom4j.Element封装
 *
 */
public interface NL_Element {
	/**
	 * 取得某节点的单个子节点
	 *
	 * @param childElementName
	 *            需要取得的子节点名称
	 * @return childElement 子节点
	 */
	public NL_Element getChildElement(String childElementName);

	/**
	 * 取得节点值(取值节点为叶子节点)
	 *
	 * @return ElementValue 节点值
	 */
	public String getElementValue();

	/**
	 * 取得某子节点值(取值节点为叶子节点)
	 *
	 * @param childElementName
	 *            子节点名称
	 * @return childElementValue 子节点值
	 */
	public String getChildElementValue(String childElementName);

	/**
	 * 取得某节点下所有子节点
	 *
	 * @return List<Element> 子节点列
	 */
	public List<NL_Element> getElementList();

	/**
	 * 取得某节点下名为childElementName的所有子节点
	 *
	 * @param childElementName
	 *            所取节点名
	 * @return
	 */
	public List<NL_Element> getElementList(String childElementName);

	/**
	 * 在某节点下添加子节点
	 *
	 * @param childElementName
	 *            子节点名
	 * @return childElement 子节点
	 */
	public NL_Element addChildElement(String childElementName);

	/**
	 * 在某节点下添加子节点
	 *
	 * @param childElementName
	 *            子节点名
	 * @param childElementValue
	 *            子节点值
	 * @return childElement 子节点
	 */
	public NL_Element addChildElement(String childElementName,
                                      String childElementValue);

	/**
	 * 设置节点值
	 *
	 * @param elementValue
	 *            节点值
	 * @return element 节点
	 */
	public NL_Element setElementValue(String elementValue);

	/**
	 * 删除子节点
	 *
	 * @return parentElement 父节点（删除后）
	 */
	public NL_Element delChildElement(String childElementName);

	/**
	 * 取得节点的属性值
	 *
	 * @param attributeName
	 *            属性名称
	 * @return attributeValue 属性值
	 */
	public String getAttributeValue(String attributeName);

	/**
	 * 遍历节点的所有属性
	 *
	 * @return attributeMap<属性名，属性值>
	 */
	public HashMap<String, String> getAllAttributeValue();

	/**
	 * 新增节点的属性和值
	 *
	 * @param attributeName
	 *            属性名
	 * @param attributeValue
	 *            属性值
	 * @return 是否成功
	 */
	public boolean addElementAttribute(String attributeName,
                                       String attributeValue);

	/**
	 * 设置属性的值
	 *
	 * @param attributeName
	 *            属性名
	 * @param attributeValue
	 *            属性值
	 * @return 是否成功
	 */
	public boolean setAttributeValue(String attributeName, String attributeValue);

	/**
	 * 删除属性
	 *
	 * @param attributeName
	 *            属性名
	 * @return 是否成功
	 */
	public boolean delAttribute(String attributeName);

	/**
	 * 取所有子节点map
	 *
	 * @param elementName
	 *            父节点名
	 * @param childMap
	 *            节点集
	 * @return childMap 节点集
	 */
	public HashMap<String, NL_Element> getElementMap(String elementName,
                                                     HashMap<String, NL_Element> childMap);

	/**
	 * 取得节点名称
	 *
	 * @return ElementName 节点名
	 */
	public String getElementName();

	/**
	 * element转字符串
	 * 
	 * @return element字符串
	 */
	public String elementToStr();

	/**
	 * 增加子节点
	 * 
	 * @param child
	 * @return
	 */
	public boolean addChildElement(NL_Element child);

}
