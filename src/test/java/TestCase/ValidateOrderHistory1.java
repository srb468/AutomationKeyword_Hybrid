package TestCase;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Utils.Excels;
import Utils.Excelutils;
import Utils.KeywordMapping;
import Utils.UIUtils;

public class ValidateOrderHistory1 {
	Excelutils excel = new Excelutils();
	KeywordMapping mapping = new KeywordMapping();
	// Excels excelop = new Excels();

	@Test(dataProvider = "inputSteps")
	public void execute(String testCaseName, String keyword, String locatorType, String locatorValue, String testData)
			throws Exception {
		
		System.out.println(keyword+"|"+locatorType+"|"+locatorValue+"|"+ testData);
		mapping.operation(keyword, locatorType, locatorValue, testData);
		/*String a =excel.getExecutionStatus("C:\\Workspace\\Automation_Hybrid\\Files\\TestCase.xlsx","Sheet2","Validate Order history");
		if (a.equalsIgnoreCase("Y")) {
			mapping.operation(keyword, locatorType, locatorValue, testData);
		} else {
			throw new SkipException("Skipping TestCase : Execution status is not Y");
		}*/

	}

	@DataProvider
	public Object[][] inputSteps() throws Exception {
		Object[][] testSteps = excel.ReadVariant("C:\\Workspace\\Automation_Hybrid\\Files\\TestCase.xlsx","Sheet2","Validate Order history");

		return testSteps;
	}
}
