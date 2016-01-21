import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;

/**
 * Excel逻辑处理类 */
public class ExcelManager {
	
	/**
	 * 将Map中的值放进Excel文件中
	 * @param map
	 * @param file
	 */
	public void putMap(Map<String, String> map, File file){
		try{
			FileOutputStream fos = new FileOutputStream(file/*"f:/test.xls"*/);
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet s = wb.createSheet();
			wb.setSheetName(0, "first sheet");
			int colCount = 0;
			Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) iterator.next();
				String key = entry.getKey();
				String textContent = entry.getValue();
				
				HSSFRow row = s.createRow(colCount++);
				for(int j=0; j<2; j++){
					HSSFCell cell = row.createCell(j);
					if(j==0){
						cell.setCellValue(key);
					}else{
						cell.setCellValue(textContent);
					}					
				}
			}
			
			wb.write(fos);
			fos.close();
		}catch(Exception e){
			System.out.println("excel error!!!!!!!!!!!!!!!!!!");
		}
	}
	
	/**
	 * 从
	 * @param file
	 * @return
	 */
	public Map<String, String> getMap(File file) {
		POIFSFileSystem fs = null; 
		HSSFWorkbook wb = null; 
		
		HSSFRow row = null; 
		HSSFCell cell = null;
		
		String cellValue;
		String key = "";
		String value = "";
		
		FileInputStream fileInputStream = null;
		
		Map<String, String> returnMap = new TreeMap<String, String>();
		
		try { 
			
			fileInputStream = new FileInputStream(file);
			fs = new POIFSFileSystem(fileInputStream); 
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0); 
			for(int i=0; i<=sheet.getLastRowNum(); i++){
				row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				for(int j=0; j<row.getLastCellNum(); j++){
					cell = row.getCell(j);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
					if(j==0){
						key = cellValue;			
					}else if (j == 1){
						value = cellValue;
					}
				}
				returnMap.put(key, value);
			}
		} catch (IOException e) { 
			e.printStackTrace(); 
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
					fileInputStream = null;
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				if (wb != null) {
					wb.close();
					wb.close();
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return returnMap;
	}
}
