package com.robust;

/**
 * 启动类Main
 * @author chenhewen
 *
 */
public class Main {
	
	public static final String RES_DIR = "F:/桌面/res";
	public static final String RES_DIR_DISTICT_EXCEL = "F:/桌面/res.distict.excel";
	public static final String RES_DIR_TRANSLATE = "F:/桌面/translate";
	public static final String RES_DIR_FILL = "F:/桌面/res_fill";
	public static final String RES_DIR_FINAL = "F:/桌面/res.final";
	//内置
	public static final String FILE_REGEX = "[sS]trings?\\.xml";
	public static final String FILE_TRANSLATE_REGEX = "[sS]trings?\\.xml";
	// 内置外置
//	public static final String FILE_REGEX = "[sS]trings?.*\\.xml";
	public static final String ENGLISH_FILEPATH = RES_DIR +"/values/strings.xml";
	
	public static void main(String[] args) {
		XmlParser parser = new XmlParser();
		ParserManager parserManager = new ParserManager();
		FileManager fileManager = new FileManager();
		ExcelManager excelManager = new ExcelManager();
		TranslationInspector translationInspector = new TranslationInspector();
		
		//parserManager.createDistinctFile(new File("F:/桌面/res/values-iw/strings.xml"), new File("F:/桌面/strings-iw.xml"), new File("F:/桌面/res/values/strings.xml"));
		//translationInspector.checkProblems(new File("F:/桌面/strings-iw.xml"), new File("F:/桌面/res/values-iw/strings.xml"), new File(ENGLISH_FILEPATH));
		//parserManager.appendFile(new File("F:/桌面/strings-iw.xml"), new File("F:/桌面/res/values-iw/strings.xml"));
		//parserManager.formatFile(new File("/home/chenhewen/桌面/backup/V1.0.1/strings 阿语.xml"), new File("/home/chenhewen/桌面/backup/V1.0.1/strings.xml"));

		/*String cmd = args[0];
		String[] methodArgs = new String[args.length - 1];
		Class<?>[] methodArgsTypeClazz = new Class<?>[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			methodArgs[i - 1] = args[i];
			methodArgsTypeClazz[i - 1] = String.class;
		}
		
		
		CmdLine cmdLine = new CmdLine();
		Class<? extends CmdLine> clazz = cmdLine.getClass();
		try {
			Method declaredMethod = clazz.getDeclaredMethod(cmd, methodArgsTypeClazz);
			try {
				declaredMethod.invoke(cmdLine, (Object[]) methodArgs);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		} catch (NoSuchMethodException e) {
			System.out.println(cmdLine.getSupportedCmd());
		} catch (SecurityException e) {
			//TODO
		}		*/
	}
}
