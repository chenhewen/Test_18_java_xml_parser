package com.robust;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 负责解析的逻辑处理
 * @author chenhewen
 *
 */
public class ParserManager {

	public int mCorrectType = 0;
	
	public static final int CORRECT_REPLACE = 1;
	
	public static final int CORRECT_ADD = 2;
	
	XmlParser mParser = new XmlParser();
	FileManager mFileManager = new FileManager();
	ExcelManager mExcelManager = new ExcelManager();
	
	private Map<String, String> mDistinctMap = new TreeMap<String, String>();

	/**
	 * 比较两个map中的值, 返回要比较的map在模板map中没有的数据
	 * @param modleMap
	 * @param comparedMap
	 * @return
	 */
	public Map<String, String> compare(Map<String, String> modleMap, Map<String, String> comparedMap) {
		Map<String, String> distinctMap = new TreeMap<String, String>();
		distinctMap.putAll(modleMap);
		Set<Entry<String, String>> keySet = distinctMap.entrySet();
		Iterator<Entry<String, String>> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
			if (comparedMap.containsKey(entry.getKey())) {
				iterator.remove();
			}
		}

		return distinctMap;
	}
	
	/**
	 * 
	 * @param correctFile
	 * @param modleFile
	 * @param isCompleteCorrectFileWithModleFile
	 */
	public void formatPrivate(File correctFile, File modleFile, boolean isCompleteCorrectFileWithModleFile) {
		//1. 从将要格式化的文件中提取数据信息
		Map<String, String> stringMap = mParser.parseFile(correctFile, XmlParser.GET_TEXT_CONTENT);

		//2. 拷贝模板文件到目标文件夹, 并重命名
		String path = correctFile.getAbsolutePath();
		
		String copyPath = path + ".temp";
		Utils.copyFile(modleFile.getAbsolutePath(), copyPath);

		//3. 将步骤2中创建的文件中的数据替换未提取的数据, 注意提取数据是其子集
		File copyFile = new File(copyPath);
		mParser.replaceTextContent(copyFile, stringMap, !isCompleteCorrectFileWithModleFile);
		correctFile.delete();
		copyFile.renameTo(correctFile);
	}
	
	/**
	 * 按照模板的格式，格式化文件
	 * 举例： 模板文件中有，并且顺序依次为A = x， B = y， C = z的条目， 倘若待格式化的文件有， 并且顺序为C = n, B = m，
	 * 那么操作结果将是 B = m, C = n
	 * @param correctFile
	 * @param modleFile
	 */
	public void format(File correctFile, File modleFile) {
		formatPrivate(correctFile, modleFile, false);
	}
	
	public void formatRegexFile(File destDir, File modleFile, String regex) {
		for (File f : mFileManager.getFiles(destDir, regex)) {
			format(f, modleFile);
		}
	}
	
	public void formatFile(File destDir, File modleFile) {
		formatRegexFile(destDir, modleFile, "*");
	}
	
	/**
	 * 按照模板的格式，格式化文件, 并且用模板文件补全待格式化文件
	 * 举例： 模板文件中有，并且顺序依次为A = x， B = y， C = z的条目， 倘若待格式化的文件有， 并且顺序为C = n, B = m，
	 * 那么操作的结果将是 A = x, B = m, C = n,
	 * @param modleFile 模板文件
	 * @param correctFile 要更改的文件
	 */
	public void formatAndComplete(File correctFile, File modleFile) {
		formatPrivate(correctFile, modleFile, true);
	}
	
	/**
	 * 将要更改的文件夹所有替换为模板文件的格式, 同时替换要更改文件的
	 * @param destDir
	 * @param modleFile
	 */
	public void formatAndCompleteDir(File srcDir, File destDir, File modleFile) {
		mFileManager.copyDir(srcDir, destDir, Main.FILE_REGEX);
		for (File f : mFileManager.getFiles(destDir)) {
			formatAndComplete(f, modleFile);
		}
	}
	
	/**
	 * 找出未翻译的, 生成目录, xml
	 * @param srcDir
	 * @param destDir
	 * @param modleFile
	 */
	public void createDistinctFile(File srcDir, File destDir, File modleFile) {
		mFileManager.copyDir(srcDir, destDir, Main.FILE_REGEX);
		for (File f: mFileManager.getFiles(destDir)) {
			Map<String, String> stringMap = mParser.parseFile(f, XmlParser.GET_LINE);
			//直接调用该方法, 有损效率, 因为没有必要替换其中的内容			
			formatAndComplete(f, modleFile);
			mParser.remove(f, stringMap);
		}
	}
	
	/**
	 * 将src xml文件加入到dest xml文件尾部， 所谓的的尾部， 不是流的尾部， 因为要保证dest文件仍是格式良好的xml
	 * @param srcDir
	 * @param destDir
	 */
	@Deprecated
	public void appendFile(File srcDir, File destDir) {
		
		Map<String, File> map = mFileManager.getParentFileMap(destDir, Main.FILE_REGEX);
		
		for (File sf: mFileManager.getFiles(srcDir, Main.FILE_TRANSLATE_REGEX)) {
			String valueLaunguageStr = Utils.getValueLaunguageStr(sf);
			File destF = map.get(valueLaunguageStr);
			//下面两句是合并代码的本质， 是操作单一文件的
			Map<String, String> parsedFileMap = mParser.parseFile(sf, XmlParser.GET_TEXT_CONTENT);
			mParser.append(destF, parsedFileMap);
		}
	}
}
