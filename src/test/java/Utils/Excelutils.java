package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excelutils {

	public static FileInputStream fis;
	public static Sheet sheet;
	public static XSSFWorkbook wb;
	public static Row row;
	static int rInit;
	int rowCount = 0;

	public static Workbook loadexcel(String fileName) throws IOException {
		System.out.println("Loading excel data.....");
		File src = new File(fileName);
		fis = new FileInputStream(src);
		wb = new XSSFWorkbook(fis);
		if(wb !=null) {
			wb.close();
		}
		return wb;
		// sheet = wb.getSheet(sheetName);
		// fis.close();
	}
	
/*	public Workbook loadexcel(String path, String name) throws Exception {
		Workbook wb = null;
		File file = new File(path + "\\" + name);
		FileInputStream inputStream = new FileInputStream(file);
		String fileExtensionName = name.substring(name.indexOf("."));
		try {
			if (fileExtensionName.equals(".xlsx")) {
				wb = new XSSFWorkbook(inputStream);
			} else if (fileExtensionName.equals(".xls")) {
				wb = new HSSFWorkbook(inputStream);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return wb;
	}
*/
	public static Map<String, Map<String, String>> getdata(String fileName, String sheetName) throws IOException {
		
		sheet = loadexcel(fileName).getSheet(sheetName);

		Map<String, Map<String, String>> supermap = new HashMap<String, Map<String, String>>();
		Map<String, String> mymap = new HashMap<String, String>();

		for (int i = 0; i < sheet.getLastRowNum() + 1; i++) {
			row = sheet.getRow(i);
			String keycell = row.getCell(0).getStringCellValue();
			int colnum = row.getLastCellNum();
			for (int j = 1; j < colnum; j++) {
				String value = row.getCell(j).getStringCellValue();
				mymap.put(keycell, value);
			}
			supermap.put("MASTERDATA", mymap);
			System.out.println(supermap);

		}

		return supermap;

	}

	public static String getExecutionStatus(String fileName, String sheetName,String key) throws IOException {
		Map<String, String> myval = getdata(fileName,sheetName).get("MASTERDATA");
		String value = myval.get(key);
		return value;

	}

	public static int rowCalculator(String fileName,String sheetName,String tcName) throws IOException {
		sheet = loadexcel(fileName).getSheet(sheetName);
		
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int s = 1; s < rowCount + 1; s++) {
			String a = sheet.getRow(s).getCell(0).getStringCellValue();
			if (a.equalsIgnoreCase("tcName") || a.equalsIgnoreCase(tcName + "_TCEND")) {

				if (a.equalsIgnoreCase("tcName")) {
					rInit = s;
				}
				if (a.equalsIgnoreCase(tcName + "_TCEND")) {
					int rFinal = s;
					rowCount = rFinal - rInit;
					System.out.println("---ROW---" + rowCount);
				}
			}
		}
		return rowCount;
	}

	public Object[][] ReadVariant(String fileName,String sheetName,String tcName) throws IOException {

		sheet = loadexcel(fileName).getSheet(sheetName);
		int presentRows = rowCalculator(fileName,sheetName,tcName);
		int col = sheet.getRow(0).getLastCellNum();
		Object Data[][] = new Object[presentRows - 2][col];
		for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
			String val = sheet.getRow(i).getCell(0).getStringCellValue();

			if (val.equalsIgnoreCase(tcName) && !(val.equalsIgnoreCase(tcName + "_TCEND"))) {
				for (int a = i + 1; a < sheet.getLastRowNum(); a++) {
					Row row = sheet.getRow(a);
					if (!(row.getCell(0).getStringCellValue().equalsIgnoreCase(tcName + "_TCEND"))) {
						for (int b = 0; b < row.getLastCellNum(); b++) {
							if ((row.getCell(0).getStringCellValue().equalsIgnoreCase(tcName + "_TCEND"))) {
								break;
							} else {
								String value = sheet.getRow(a).getCell(b).getStringCellValue();
								Data[rowCount][b] = value;
							}
						}
					} else {
						break;
					}
					rowCount++;
				}
			} else if (val.equalsIgnoreCase(tcName + "_TCEND")) {
				break;
			}
		}
		return Data;

	}

}
