package com.robust;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;

/**
 * 工具类
 */
public class Utils {
	
	public static int PATH_AHEAD_SECTION = 1;
	public static int PATH_LAST_SECTION = 2;
	
	public static String getLine(Element element) {
		NamedNodeMap attrMap = element.getAttributes();
		StringBuffer line = new StringBuffer();
		
		line.append("<");
		line.append(element.getNodeName());
		for (int i=0; i<attrMap.getLength(); i++) {
			Attr attr = (Attr) attrMap.item(i);
			line.append(" ")
				.append(attr.getNodeName())
				.append("=\"")
				.append(attr.getNodeValue())
				.append("\"");
				
		}
		line.append(">");
		
		line.append(element.getTextContent());
		
		line.append("</")
			.append(element.getNodeName())
			.append(">");
		
		
		return line.toString();
	}
	
	public static void printMap(Map<String, String> map, File f) {
		int count = 0;
		Set<Entry<String, String>> keySet = map.entrySet();
		Iterator<Entry<String, String>> iterator = keySet.iterator();
		System.out.println("<resources name=\"" + f.getParentFile().getName() + "\" count=\"" + keySet.size() +"\">");
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			System.out.println(entry.getValue());
			count++;
		}
		System.out.println("</resources>\n\n");
	}

	
	public static void printFile(File[] fs) {
		for (File f : fs) {
			if (f != null) {
				System.out.println(f.getAbsolutePath());
			}
		}
	}
	
	public static boolean copyFile(String srcStr, String decStr) {
		// 前提
		File srcFile = new File(srcStr);
		if (!srcFile.exists()) {
			return false;
		}
		File decFile = new File(decStr);
		if (!decFile.exists()) {
			File parent = decFile.getParentFile();
			boolean b = parent.mkdirs();

			try {
				decFile.createNewFile();

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(srcFile);
			output = new FileOutputStream(decFile);
			byte[] data = new byte[4 * 1024]; // 4k
			while (true) {
				int len = input.read(data);
				if (len <= 0) {
					break;
				}
				output.write(data, 0, len);

				// just test how it will perform when a exception occure on
				// backing up
				// throw new IOException();
			}
		} catch (Exception e) {
			return false;
		} finally {
			if (null != input) {
				try {
					input.close();
				} catch (Exception e2) {
				}
			}
			if (null != output) {
				try {
					output.flush();
					output.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}

		return true;
	}
	
	public static String getPath(String str, int preOrPost, int count) {
		
		StringBuffer regex = new StringBuffer("(.*)(/.*/");
		for (int i=0; i<count; i++) {
			regex.append("\\w+");
		}
		regex.append("\\.\\w*)");
		
		Pattern pattern = Pattern.compile(regex.toString());
		
		Matcher matcher = pattern.matcher(str);
		while (matcher.find()) {
			if (preOrPost == PATH_AHEAD_SECTION) {
				String pre = matcher.group(1);
				return pre;
			} else if (preOrPost == PATH_LAST_SECTION) {
				String post = matcher.group(2);
				return post;
			}
		}
		
		return "";
	}

	public static String getLanguageDirName(String absolutePath) {
		Pattern pattern = Pattern.compile(".*/(.*)/\\w+\\.\\w*");
		Matcher matcher = pattern.matcher(absolutePath);
		while (matcher.find()) {
			String name = matcher.group(1);
			return name;
		}
		
		return "";
	}
	
	/**
	 * 获取value-ar之类的东西， 不捕获可能的异常
	 * @param file
	 * @return
	 */
	public static String getValueLaunguageStr(File file) {
		String name = file.getName();
		String[] split = name.split("-");
		return split[0] +"-" + split[1];
		
	}
}
