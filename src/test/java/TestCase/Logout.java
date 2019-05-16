package TestCase;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Utils.Excels;
import Utils.Excelutils;
import Utils.KeywordMapping;
import Utils.UIUtils;

public class Logout extends Init {
	KeywordMapping mapping = new KeywordMapping();
	Excels excelop = new Excels();

	@Test(dataProvider = "inputSteps")
	public void testCase_Logout(String testCaseName, String keyword, String locatorType, String locatorValue,
			String testData) throws Exception {
		mapping.operation(keyword, locatorType, locatorValue, testData);
	}

	@DataProvider
	public Object[][] inputSteps() throws Exception {
		Object[][] test = excelop.ReadVariant("C:\\Workspace\\Automation_Hybrid\\Files\\", "TestCase.xlsx", "Sheet1",
				"Logout");

		return test;
	}
}
