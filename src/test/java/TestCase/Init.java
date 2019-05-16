package TestCase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import Utils.UIUtils;

public class Init {
	public static ExtentReports extent;
	public static ExtentTest test;

	UIUtils utils = null;

	@BeforeSuite
	public void beforeSuite() {
		utils = new UIUtils();
		/*
		 * SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd_HH_mm_ss"); Date
		 * date = new Date(); extent = new
		 * ExtentReports("C:\\Workspace\\Automation_Hybrid\\Temp\\ExtentReports"+
		 * formatter.format(date).toString()+".html", true);
		 */
		extent = new ExtentReports("C:\\Workspace\\Automation_Hybrid\\Temp\\AutomationReport.html", true);

		extent.loadConfig(new File("C:\\Workspace\\Automation_Hybrid\\entent-config.xml"));

	}

	@BeforeClass
	public void beforeTest() {
		test = extent.startTest(this.getClass().getSimpleName());
		test.assignAuthor("Saurabh Singh");
		test.assignCategory("Automation: Extended Keyword Driven");

	}

	@AfterClass
	public void afterTest() {

		extent.endTest(test);
	}

	@AfterSuite
	public void afterSuite() {
		extent.flush();
		extent.close();
	}
}