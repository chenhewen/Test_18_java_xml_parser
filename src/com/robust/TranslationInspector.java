package com.robust;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ��鷭����ļ��Ƿ�������
 * @author Administrator
 *
 */
public class TranslationInspector {

	public static class Level {
		
		public static final int WRONG = 0;
		
		public static final int WARNING = 1;
		
		public static final int PASSED = 2;
		
		public static String getLevelStr(int level){
			if (level == WRONG) {
				return "WRONG";
			} else if (level == WARNING) {
				return "WARNING";
			} else if (level == PASSED) {
				return "PASSED";
			}
			
			return "UNKNOWN LEVEL";
		}
	} 
	
	/**
	 * ��鷭���ļ���<string name="xxx">yyy</string>
	 * @param modle ģ���ļ�
	 * @return true���ļ�û�����⣬ ���������xml�ļ��е�key��Ҳ����name, ��Ӧxxx��û�б��޸Ĺ��� �������
	 */
	public boolean inspectName(File modle, File translationFile) {
		XmlParser mParser = new XmlParser();
		Map<String, String> parsedFileMap = mParser.parseFile(modle, XmlParser.GET_TEXT_CONTENT);
		Set<String> parsedFilekeySet = parsedFileMap.keySet();
		
		Map<String, String> translationFileMap = mParser.parseFile(translationFile, XmlParser.GET_TEXT_CONTENT);
		Set<String> translationkeySet = translationFileMap.keySet();
		Iterator<String> translationIterator = translationkeySet.iterator();
		
		while (translationIterator.hasNext()) {
			String name = translationIterator.next();
			if (!parsedFilekeySet.contains(name)) {
				return false;
			}
		}
		
		return true;
	}
}
