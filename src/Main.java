import java.io.File;
/**
 * 启动类
 * @author chenhewen
 *
 */
public class Main {
	public static final String RES_DIR = "/home/chenhewen/桌面/res";
	public static final String RES_DIR_DISTICT_EXCEL = "/home/chenhewen/桌面/res.distict.excel";
	public static final String RES_DIR_FINAL = "/home/chenhewen/桌面/res.final";
	public static final String REGEX = "[sS]trings?\\.xml";
	public static final String ENGLISH_FILEPATH = RES_DIR + "/values/strings.xml";
	
	public static void main(String[] args) {
		XmlParser parser = new XmlParser();
		ParserManager parserManager = new ParserManager();
		FileManager fileManager = new FileManager();
		ExcelManager excelManager = new ExcelManager();
		
		/**
		 * 三步骤, 一行一行执行
		 * 
		 * 规范1: 翻译前, 与翻译后, 目录结构要保持一致
		 * 规范2: 程序开发人员需保证, 只在英文strings.xml写入新增字段
		 * 规范3: 将翻译好的文件通过程序整理到string.xml中时要注意, 不可将value/strings.xml进行拷贝替换(因为翻译往往需要一段时间, 这时候英文可能又有新增). 其他目录下文件可以
		 * 
		 * 1. 创建一个未翻译的资源文件夹, 里面的内容是Excel表格, 交给产品
		 * 2. 创建一个补齐文件夹, 里面所有的strings.xml文件中未翻译的, 都按照模板补齐了, 一般情况下, 模板是英文string.xml, 
		 *    也就是此时, 其他语言中会混杂有英语, 并且按照英文strings.xml进行了格式化.
		 * 3. 如果产品的翻译回来了, 他们需要提供一个与翻译之前同样的目录结构的文件夹. 这时, 上一步用英文补齐的每一项, 都会被相应的替换   
		 */
		parserManager.createDistinctExcel(new File(RES_DIR), new File(RES_DIR_DISTICT_EXCEL), new File(ENGLISH_FILEPATH));

		parserManager.formatAndCompleteDir(new File(RES_DIR), new File(RES_DIR_FINAL), new File(ENGLISH_FILEPATH));

		parserManager.translateExcel(new File(RES_DIR_DISTICT_EXCEL), new File(RES_DIR_FINAL));
		
		
		
		
		
		//测试...
		
		/*File[] stringFiles = fileManager.getStringFiles(new File(RES_DIR), REGEX);
		Map<String, String> modleMap = parser.parseFile(new File(ENGLISH_FILEPATH), MyParser.GET_LINE);
		
		for (File stringFile : stringFiles) {
			Map<String, String> comparedMap = parser.parseFile(stringFile, MyParser.GET_LINE);
			Map<String, String> dictingMap = parserManager.compare(modleMap, comparedMap);
			Utils.printMap(dictingMap, stringFile);
			Utils.saveInFile(dictingMap, stringFile, new File(RES_DIR+"2"));
		}*/
		
		/*File modleFile = new File("/home/chenhewen/桌面/res/values/strings.xml");
		File correctFile = new File("/home/chenhewen/桌面/res/values-ir/strings.xml");
		parserManager.formatAndCompleteAsModule(correctFile, modleFile);*/
		
		//parserManager.createDistinctFile(new File("/home/chenhewen/F/GoStore/ZBoost/res"), new File("/home/chenhewen/桌面/rescopy"), new File(ENGLISH_FILEPATH));
		
		/*File file = new File("/home/chenhewen/桌面/res/values/strings.xml");
		Map<String, String> map = parser.parseFile(file, MyParser.GET_LINE);
		parser.remove(file, map);*/
		
//		Map<String, String> map = parser.parseFile(new File(ENGLISH_FILEPATH), XmlParser.GET_TEXT_CONTENT);
//		excelManager.putMap(map, new File("/home/chenhewen/桌面/excel.xls"));
		
	}
}
