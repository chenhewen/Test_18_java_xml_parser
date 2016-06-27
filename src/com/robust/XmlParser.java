package com.robust;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * 负责解析工作
 * @author chenhewen
 *
 */
public class XmlParser {

	public int mGetType = 0;

	public static final int GET_LINE = 1;

	public static final int GET_TEXT_CONTENT = 2;

	Map<String, String> mStringNameMap;

	/**
	 * 获取xml文件中所有的string tab的name作为key, 整行信息作为value
	 * @param file xml文件
	 * @return tab的name作为key, 整行信息作为value 的map
	 */
	public Map<String, String> parseFile(File file, int getType) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder buidler = null;
		Document document = null;
		NodeList resourcesList = null;
		Element root = null;
		mStringNameMap = new TreeMap<String, String>();
		try {
			buidler = factory.newDocumentBuilder();
			document = buidler.parse(file);
			root = document.getDocumentElement();
			resourcesList = root.getElementsByTagName("string");
			for (int i = 0; i < resourcesList.getLength(); i++) {
				Element element = (Element) resourcesList.item(i);
				String nameValue = element.getAttribute("name");
				String mapValue = "";
				if (getType == GET_LINE) {
					mapValue = Utils.getLine(element);
				} else if (getType == GET_TEXT_CONTENT) {
					mapValue = element.getTextContent();
				}
				mStringNameMap.put(nameValue, mapValue);
			}
		} catch (ParserConfigurationException e1) {
			e1.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mStringNameMap;
	}

	/**
	 * 替换制定xml文件中的指定的值
	 * @param replacedFile 要替换的xml文件
	 * @param stringMap key:name属性的值, value:textContent 也即尖括号之间的文本
	 * @param deleteLineNotFoundInMap 是否从replacedFile中删除在stringMap中没有的行
	 */
	public void replaceTextContent(File replacedFile, Map<String, String> stringMap, boolean deleteLineNotFoundInMap) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder buidler = null;
		Document document = null;
		NodeList resourcesList = null;
		Element root = null;
		try {
			buidler = factory.newDocumentBuilder();
			document = buidler.parse(replacedFile);
			root = document.getDocumentElement();
			resourcesList = root.getElementsByTagName("string");
//			for (int i = 0; i < resourcesList.getLength(); i++) {
			for (int i = resourcesList.getLength() - 1; i >= 0; i--) {
				Element element = (Element) resourcesList.item(i);
				String key = element.getAttribute("name");
				String textContent = stringMap.get(key);
				if (textContent != null) {
					element.setTextContent(textContent);
				} else if (deleteLineNotFoundInMap) {
					element.getParentNode().removeChild(element);
				}
			}
			//修改后提交
			TransformerFactory factory2 = TransformerFactory.newInstance();
			Transformer former = factory2.newTransformer();
			former.transform(new DOMSource(document), new StreamResult(replacedFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从制定的string.xml文件中删除map中的值
	 * @param operateFile
	 * @param stringMap
	 */
	public void remove(File operateFile, Map<String, String> stringMap) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder buidler = null;
		Document document = null;
		NodeList resourcesList = null;
		Element root = null;
		try {
			buidler = factory.newDocumentBuilder();
			document = buidler.parse(operateFile);
			root = document.getDocumentElement();
			resourcesList = root.getElementsByTagName("string");
			//一定要从后向前遍历
			for (int i = resourcesList.getLength() - 1; i >= 0; i--) {
				Element element = (Element) resourcesList.item(i);
				String key = element.getAttribute("name");
				if (stringMap.containsKey(key)) {
					
					// 删除空行
					Node nextSibling = element.getNextSibling();
					if (nextSibling != null && nextSibling.getNodeType() == Node.TEXT_NODE
							&& nextSibling.getNodeValue().trim().length() == 0) {
						element.getParentNode().removeChild(nextSibling);
					}
					
					element.getParentNode().removeChild(element);
				}
			}
			//修改后提交
			TransformerFactory factory2 = TransformerFactory.newInstance();
			Transformer former = factory2.newTransformer();
			former.transform(new DOMSource(document), new StreamResult(operateFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 在文件尾加入Map对应的字符串
	 * @param operateFile
	 * @param stringMap
	 */
	public void append(File operateFile, Map<String, String> stringMap) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder buidler = null;
		Document document = null;
		Element root = null;
		try {
			buidler = factory.newDocumentBuilder();
			document = buidler.parse(operateFile);
			root = document.getDocumentElement();

			Iterator<Entry<String, String>> iterator = stringMap.entrySet().iterator();
			while (iterator.hasNext()) {
				Entry<String, String> next = iterator.next();
				String name = next.getKey();
				String value = next.getValue();
				Element newChild = document.createElement("string");
				newChild.setAttribute("name", name);
				newChild.setTextContent(value);
				root.appendChild(newChild);
			}
			
			//修改后提交, 留4个空格
			TransformerFactory factory2 = TransformerFactory.newInstance();
			Transformer former = factory2.newTransformer();
			former.setOutputProperty(OutputKeys.INDENT, "yes");
			former.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			former.transform(new DOMSource(document), new StreamResult(operateFile));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}