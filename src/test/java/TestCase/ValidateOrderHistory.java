package TestCase;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import Utils.Excels;
import Utils.Excelutils;
import Utils.KeywordMapping;

public class ValidateOrderHistory extends Init{
	// Excelutils excel = new Excelutils();
	KeywordMapping mapping = new KeywordMapping();
	Excels excelop = new Excels();

	/*
	 * public void startCase() {
	 * 
	 * report = new
	 * ExtentReports("C:\\Workspace\\Automation_Hybrid\\Temp\\ExtentReport.html");
	 * test = report.startTest("TestCase Execution start");
	 * 
	 * }
	 */

	@Test(dataProvider = "inputSteps")
	public void testCase_ValidateOrderHistory(String testCaseName, String keyword, String locatorType, String locatorValue, String testData)
			throws Exception {
		//test.log(LogStatus.INFO, "executing validate order");
		mapping.operation(keyword, locatorType, locatorValue, testData);

	}

	@DataProvider
	public Object[][] inputSteps() throws Exception {
		Object[][] test = excelop.ReadVariant("C:\\Workspace\\Automation_Hybrid\\Files\\", "TestCase.xlsx", "Sheet1",
				"Validate Order history");
		return test;
	}
}
