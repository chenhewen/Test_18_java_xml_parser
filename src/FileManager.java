import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
			String relativePath = srcPath.split(srcFile.getAbsolutePath())[1];
			String desPath = destFile + relativePath;
			/*try {
				new File(desPath).createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}*/
			if (pattern.matcher(new File(desPath).getName()).matches()) {
				Utils.copyFile(srcPath, desPath);
			}
		}
	}
	
	public void copyDir(File srcFile, File destFile) {
		copyDir(srcFile, destFile, ".*");
	}
	
}
