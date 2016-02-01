package com.robust;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.robust.values.ResourceBundleManager;

/**
 * ��鷭����ļ��Ƿ�������
 * @author Administrator
 *
 */
public class TranslationInspector {

	XmlParser mParser = new XmlParser();
	FileManager mFileManager = new FileManager();
	
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
	 * ��鷭���ļ���<string name="xxx">yyy</string>�Ƿ���ڱ�ģ������name��
	 * @param comparedFile ģ���ļ�
	 * @return true�����ڶ���ġ�false��һ������
	 */
	public boolean checkRedundantNames(File translationFile, File comparedFile) {
		
		Map<String, String> comparedFileMap = mParser.parseFile(comparedFile, XmlParser.GET_TEXT_CONTENT);
		Set<String> comparedFilekeySet = comparedFileMap.keySet();
		
		Map<String, String> translationFileMap = mParser.parseFile(translationFile, XmlParser.GET_TEXT_CONTENT);
		Set<String> translationkeySet = translationFileMap.keySet();
		Iterator<String> translationIterator = translationkeySet.iterator();
		
		boolean exist = false;
		
		while (translationIterator.hasNext()) {
			String name = translationIterator.next();
			if (!comparedFilekeySet.contains(name)) {
				System.out.println(ResourceBundleManager.getInstance().getResourceBundle().getString("inspector.discover.name.not.exits"));
				System.out.println("\t" + name);
				//����Ϊ�˽����еĴ��󶼷��ֲ���ӡ�� ����û����ǰreturn false
				exist = true;
			}
		}
		 
		return exist;
	}
	
	/**
	 * ����Ƿ���ڸ��ǵķ��롣
	 * @param translationFile
	 * @param comparedFile
	 * @return
	 */
	public boolean checkOverwriteNames(File translationFile, File comparedFile) {
		Map<String, String> comparedFileMap = mParser.parseFile(comparedFile, XmlParser.GET_TEXT_CONTENT);
		Set<String> comparedFilekeySet = comparedFileMap.keySet();
		
		Map<String, String> translationFileMap = mParser.parseFile(translationFile, XmlParser.GET_TEXT_CONTENT);
		Set<String> translationkeySet = translationFileMap.keySet();
		Iterator<String> translationIterator = translationkeySet.iterator();
		
		boolean exist = false;
		while (translationIterator.hasNext()) {
			String name = translationIterator.next();
			if (comparedFilekeySet.contains(name)) {
				System.out.println(ResourceBundleManager.getInstance().getResourceBundle().getString("inspector.discover.name.already.exits"));
				System.out.println("\t" + name);
				//����Ϊ�˽����еĴ��󶼷��ֲ���ӡ�� ����û����ǰreturn false
				exist = true;
			}
		}
		 
		return exist;
		
	}
	
	
	/**
	 *  �ж�����Ŀ¼�µķ����ļ���Key��ģ���ļ��ж��Ǵ��ڵģ� ��ֹ������Ա���޸�Key
	 * @param translationDir
	 * @param comparedDir
	 * @param modle
	 * @return true�����⣬ falseû������
	 */
	public boolean checkProblems(File translationDir, File comparedDir, File modle) {
		boolean problemExist = false;
		int problemCount = 0;
		
		File[] translationFiles = mFileManager.getFiles(translationDir, Main.FILE_TRANSLATE_REGEX);
		Map<String, File> comparedFileMap = mFileManager.getParentFileMap(comparedDir, Main.FILE_REGEX);
		
		for (File f : translationFiles) {
			Utils.printlnDividerLine();
			System.out.println(ResourceBundleManager.getInstance().getResourceBundle().getString("inspector.discover.check") + ":" + f.getAbsolutePath());
			
			if (checkRedundantNames(f, modle)) {
				problemExist = true;
				problemCount++;
			}
			
			String valueLaunguageStr = Utils.getValueLaunguageStr(f);
			File comparedFile = comparedFileMap.get(valueLaunguageStr);
			if (checkOverwriteNames(f, comparedFile)) {
				problemExist = true;
				problemCount++;
			}
		}
		
		Utils.printlnDoubleDividerLine();
		System.out.println(ResourceBundleManager.getInstance().getResourceBundle().getString("inspector.discover.problem") + ":" + problemCount);
		return problemExist;
	}
}
