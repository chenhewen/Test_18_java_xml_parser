package com.robust;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 文件处理类
 * @author chenhewen
 *
 */
public class FileManager {
	
	/**
	 * 返回所有包含strings.xml文件数组
	 * @param f
	 * @return
	 */
	public File[] getFiles(File f, String regex) {
		List<File> fileList = new ArrayList<File>();
		getFile(f, fileList, regex);
		return fileList.toArray(new File[0]);
	}
	
	public File[] getFiles(File f) {
		return getFiles(f, ".*");
	}
	
	/**
	 * 递归遍历, 寻找strings.xml
	 * @param f 遍历开始的根目录
	 * @param list 用于保存strings.xml的集合
	 */
	private void getFile(File f, List<File> list, String regex) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				getFile(file, list, regex);
			}
		} else {
			Pattern pattern = Pattern.compile(regex);
			if (pattern.matcher(f.getName()).matches()) {
				list.add(f);
			}
		}
	}
	
	public void createDirs(File[] fs) {
		for (File f: fs) {
			f.mkdirs();
		}
	}
	
	public void copyDir(File srcFile, File destFile, String regex) {
		Pattern pattern = Pattern.compile(regex);
		File[] files = getFiles(srcFile);
		for (File file : files) {
			String srcPath = file.getAbsolutePath();
			
			//FIXME this will crash when in windows platform
			//String relativePath = srcPath.split(srcFile.getAbsolutePath())[1];
			
			//instead, we change a way to do same thing as above
			String relativePath = srcPath.substring(srcFile.getAbsolutePath().length());
			String desPath = destFile + relativePath;
			
			if (pattern.matcher(new File(desPath).getName()).matches()) {
				Utils.copyFile(srcPath, desPath);
			}
		}
	}
	
	public void copyDir(File srcFile, File destFile) {
		copyDir(srcFile, destFile, ".*");
	}
	
	/**
	 * 获取满足正则表达式regex文件的父目录， 以map形式保存
	 * @param dir
	 * @param regex
	 * @return
	 */
	public Map<String, File> getParentFileMap(File dir, String regex) {
		Map<String, File> map = new HashMap<String, File>();
		for (File df: getFiles(dir, regex)) {
			map.put(df.getParentFile().getName(), df);
		}
		
		return map;
	}
	
}
