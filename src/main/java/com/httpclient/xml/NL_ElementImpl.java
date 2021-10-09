package com.httpclient.xml;

import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * Copyright(c)2009 江苏新大陆科技有限公司 All rights reversed 文件名称：NL_ElementImpl.java
 * 文件描述：org.dom4j.Element封装
 *
 */
public class NL_ElementImpl implements NL_Element {
	private Element element;

	public NL_ElementImpl(Element element) {
		this.element = element;
	}

	/**
	 * 取得某节点的单个子节点
	 *
	 * @param childElementName
	 *            需要取得的子节点名称
	 * @return childElement 子节点
	 */
	public NL_Element getChildElement(String childElementName) {

		Element childElement = null;

		if (element != null && childElementName != null) {
			if (!"".equals(childElementName.trim())) {
				childElement = element.element(childElementName);
				return (NL_Element) new NL_ElementImpl(childElement);
			}

		}

		return null;
	}

	/**
	 * 取得节点值(取值节点为叶子节点)
	 *
	 * @return ElementValue 节点值
	 */
	public String getElementValue() {
		String ElementValue = null;

		if (element != null) {
			ElementValue = element.getText();
		}
		return ElementValue;
	}

	/**
	 * 取得某子节点值(取值节点为叶子节点)
	 *
	 * @param childElementName
	 *            子节点名称
	 * @return childElementValue 子节点值
	 */
	public String getChildElementValue(String childElementName) {
		String childElementValue = null;

		if (element != null && childElementName != null) {
			childElementValue = element.elementText(childElementName);
		}

		return childElementValue;
	}

	/**
	 * 取得某节点下所有子节点
	 *
	 * @return List<Element> 子节点列
	 */
	public List<NL_Element> getElementList() {
		List<NL_Element> nodes = new ArrayList<NL_Element>();

		if (element != null) {
			List<Element> ls = element.elements();

			for (int i = 0; i < ls.size(); i++) {
				nodes.add((NL_Element) new NL_ElementImpl(ls.get(i)));
			}
		}

		return nodes;
	}

	/**
	 * 取得某节点下名为childElementName的所有子节点
	 *
	 * @param childElementName
	 *            所取节点名
	 * @return
	 */
	public List<NL_Element> getElementList(String childElementName) {
		List<NL_Element> nodes = new ArrayList<NL_Element>();

		if (element != null && childElementName != null) {
			List<Element> ls = element.elements(childElementName);

			for (int i = 0; i < ls.size(); i++) {
				nodes.add((NL_Element) new NL_ElementImpl(ls.get(i)));
			}
		}

		return nodes;
	}

	/**
	 * 在某节点下添加子节点
	 *
	 * @param childElementName
	 *            子节点名
	 * @return childElement 子节点
	 */
	public NL_Element addChildElement(String childElementName) {
		Element childElement = null;

		if (element != null && childElementName != null) {
			if (!"".equals(childElementName.trim())) {
				childElement = element.addElement(childElementName.trim());
				return (NL_Element) new NL_ElementImpl(childElement);
			}
		}

		return null;
	}

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
			String childElementValue) {
		Element childElement = null;

		if (element != null && childElementName != null) {
			if (!"".equals(childElementName.trim())) {
				childElement = element.addElement(childElementName.trim());
				childElement.setText(childElementValue);
				return (NL_Element) new NL_ElementImpl(childElement);
			}
		}

		return null;
	}

	/**
	 * 设置节点值
	 *
	 * @param elementValue
	 *            节点值
	 * @return element 节点
	 */
	public NL_Element setElementValue(String elementValue) {
		if (element != null && elementValue != null) {
			if (!"".equals(elementValue.trim())) {
				element.setText(elementValue);

			}
		}

		return (NL_Element) new NL_ElementImpl(element);
	}

	/**
	 * 取得节点的属性值
	 *
	 * @param attributeName
	 *            属性名称
	 * @return attributeValue 属性值
	 */
	public String getAttributeValue(String attributeName) {
		String attributeValue = null;

		if (element != null && attributeName != null) {
			attributeValue = element.attributeValue(attributeName);
		}

		return attributeValue;
	}

	/**
	 * 遍历节点的所有属性
	 *
	 * @return attributeMap<属性名，属性值>
	 */
	public HashMap<String, String> getAllAttributeValue() {
		HashMap<String, String> attributeMap = new HashMap<String, String>();

		if (element != null) {
			for (Iterator it = element.attributeIterator(); it.hasNext();) {
				Attribute attribute = (Attribute) it.next();
				String attributeValue = attribute.getText();
				String attributeName = attribute.getName();
				attributeMap.put(attributeName, attributeValue);
			}
		}

		return attributeMap;
	}

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
			String attributeValue) {
		boolean succ = false;

		if (element != null && attributeName != null && attributeValue != null) {
			if (!"".equals(attributeName.trim())) {
				element.addAttribute(attributeName.trim(), attributeValue);

				succ = true;
			}
		}

		return succ;
	}

	/**
	 * 设置属性的值
	 *
	 * @param attributeName
	 *            属性名
	 * @param attributeValue
	 *            属性值
	 * @return 是否成功
	 */
	public boolean setAttributeValue(String attributeName, String attributeValue) {
		boolean succ = false;

		if (element != null && attributeName != null && attributeValue != null) {
			if (!"".equals(attributeName.trim())) {

				Attribute attribute = element.attribute(attributeName.trim());

				if (attribute != null) {
					attribute.setText(attributeValue);

					succ = true;
				}
			}
		}

		return succ;
	}

	/**
	 * 删除属性
	 *
	 * @param attributeName
	 *            属性名
	 * @return 是否成功
	 */
	public boolean delAttribute(String attributeName) {
		boolean succ = false;

		if (element != null && attributeName != null) {
			Attribute attribute = element.attribute(attributeName);

			if (attribute != null) {
				element.remove(attribute);
			}

			succ = true;
		}

		return succ;
	}

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
			HashMap<String, NL_Element> childMap) {

		// HashMap<String, Element> childMap = new HashMap<String, Element>();

		for (Iterator i = element.elementIterator(); i.hasNext();) {
			Element childElement = (Element) i.next();
			String childElementName = childElement.getName();

			childMap.put(childElementName, (NL_Element) new NL_ElementImpl(
					childElement));

			getElementMap(childElementName, childMap);
		}

		return childMap;
	}

	/**
	 * 删除子节点
	 *
	 * @return parentElement 父节点（删除后）
	 */
	public NL_Element delChildElement(String childElementName) {
		if (element != null && childElementName != null) {
			List<Element> ls = element.elements(childElementName);
			// List<NL_Element> ls = getElementList(childElementName);

			for (int i = 0; i < ls.size(); i++) {
				element.remove(ls.get(i));
			}

		}

		return (NL_Element) new NL_ElementImpl(element);
	}

	/**
	 * 取得节点名称
	 *
	 * @return ElementName 节点名
	 */
	public String getElementName() {
		String ElementName = null;

		if (element != null) {
			ElementName = element.getName();
		}
		return ElementName;
	}

	/**
	 * element转字符串
	 * 
	 * @return element字符串
	 */
	public String elementToStr() {
		String strElement = "";

		if (element != null) {
			strElement = element.asXML();
		}
		return strElement;
	}

	/**
	 * 增加子节点
	 * 
	 * @param child
	 * @return
	 */
	public boolean addChildElement(NL_Element child) {
		boolean succ = false;
		if (element != null && child != null) {

			String strChild = child.elementToStr();

			try {
				element
						.add(DocumentHelper.parseText(strChild)
								.getRootElement());
				succ = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
		}
		return succ;

	}

}
