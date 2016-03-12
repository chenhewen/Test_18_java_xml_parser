package com.robust;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import com.robust.output.IOutput;
import com.robust.values.ResourceBundleManager;

/**
 * 检查翻译的文件是否有问题
 * @author Administrator
 *
 */
public class TranslationInspector {

	IOutput mOutput;
	
	public void setOutput(IOutput output) {
		mOutput = output;
	}
	
	public void output(CharSequence charSequence) {
		if (mOutput != null) {
			mOutput.output(charSequence);
		}
	}
	
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
	 * 检查翻译文件的<string name="xxx">yyy</string>是否存在比模板多余的name，
	 * @param comparedFile 模板文件
	 * @return true：存在多余的。false：一切正常
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
				output(ResourceBundleManager.getInstance().getResourceBundle().getString("inspector.discover.name.not.exits"));
				output("\t" + name);
				//这里为了将所有的错误都发现并打印， 所以没有提前return false
				exist = true;
			}
		}
		 
		return exist;
	}
	
	/**
	 * 检查是否存在覆盖的翻译。
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
				output(ResourceBundleManager.getInstance().getResourceBundle().getString("inspector.discover.name.already.exits"));
				output("\t" + name);
				//这里为了将所有的错误都发现并打印， 所以没有提前return false
				exist = true;
			}
		}
		 
		return exist;
		
	}
	
	
	/**
	 *  判断所有目录下的翻译文件的Key在模板文件中都是存在的， 防止翻译人员误修改Key
	 * @param translationDir
	 * @param comparedDir
	 * @param modle
	 * @return true有问题， false没有问题
	 */
	public boolean checkProblems(File translationDir, File comparedDir, File modle) {
		boolean problemExist = false;
		int problemCount = 0;
		
		File[] translationFiles = mFileManager.getFiles(translationDir, Main.FILE_TRANSLATE_REGEX);
		Map<String, File> comparedFileMap = mFileManager.getParentFileMap(comparedDir, Main.FILE_REGEX);
		
		for (File f : translationFiles) {
			output(Utils.getDividerLine());
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
		
		output(Utils.getDoubleDividerLine());
		output(ResourceBundleManager.getInstance().getResourceBundle().getString("inspector.discover.problem") + ":" + problemCount);
		return problemExist;
	}
}