package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excels {

	public Workbook getWorkbook(String path, String name) throws Exception {
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

	public List<String> testCaseList(String path, String name, String sheetname) throws Exception {
		Sheet sheet = null;
		Row row = null;
		int rowCount = 0;
		List<String> Finaldatalist = new LinkedList<String>();
		try {
			sheet = getWorkbook(path, name).getSheet(sheetname);
			rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			row = sheet.getRow(0);
			for (int i = 1; i < rowCount + 1; i++) {
				Row row1 = sheet.getRow(i);

				if (sheet.getRow(i).getCell(1).getStringCellValue().equalsIgnoreCase("Y")) {
					Finaldatalist.add(sheet.getRow(i).getCell(0).getStringCellValue().trim());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Finaldatalist;
	}

	public Map<String, String> testCaseCellDetails(String path, String name, String sheetname) throws Exception {
		Sheet sheet = null;
		Row row = null;
		int rowCount = 0;
		List<String> tcList = testCaseList(path, "TestCase.xlsx", "MasterSheet");
		System.out.println("TESTS " + tcList);
		Map<String, String> Finaldatalist = new LinkedHashMap<String, String>();
		try {
			sheet = getWorkbook(path, name).getSheet(sheetname);
			rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			System.out.println("rowCount " + rowCount);
			row = sheet.getRow(0);
			for (int tclist = 0; tclist < tcList.size(); tclist++) {
				String startIndex = "", endIndex = "", tcnameTemp = "", tcnamelist = "";
				for (int i = 1; i < rowCount + 1; i++) {
					Row row1 = sheet.getRow(0);
					tcnameTemp = sheet.getRow(i).getCell(0).getStringCellValue().trim();

					tcnamelist = tcList.get(tclist).trim();
					if (tcnamelist.equalsIgnoreCase(tcnameTemp) || tcnameTemp.contains(tcnamelist)) {
						if (!tcnameTemp.endsWith("_TCEND")) {
							startIndex = "si" + String.valueOf(i);
						} else {
							endIndex = "ei" + String.valueOf(i);
						}
					}
				}
				Finaldatalist.put(tcnamelist, startIndex + " " + endIndex);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Finaldatalist;
	}

	public Object[][] ReadVariant(String path, String name, String sheetname, String TestCase) throws Exception {
		Sheet sheet = null;
		Object[][] cellData = null;
		try {
			Map<String, String> tcDetails = testCaseCellDetails(path, name, sheetname);
			String testCaseName = tcDetails.get(TestCase.trim());
			System.out.println("testCaseName :" + testCaseName);
			int si = Integer.parseInt(testCaseName.substring(0, testCaseName.indexOf(' ')).substring(2).trim());
			int ei = Integer.parseInt(testCaseName.substring(testCaseName.indexOf(' ')).trim().substring(2).trim());
			sheet = getWorkbook(path, name).getSheet(sheetname);
			int col = sheet.getRow(0).getLastCellNum();
			int row = ei - si - 1;
			System.out.println("row : " + row + " Col " + col);
			cellData = new Object[row][col];
			for (int i = 0; i < row; i++) {
				// Row row1 = sheet.getRow(i);
				for (int j = 0; j < col; j++) {
					cellData[i][j] = sheet.getRow(si + 1 + i).getCell(j).getStringCellValue();
					System.out.print(sheet.getRow(si + 1 + i).getCell(j).getStringCellValue() + " ");
				}
				System.out.println();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return cellData;
	}
}
