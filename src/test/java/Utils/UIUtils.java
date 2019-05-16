package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import TestCase.Init;
import io.github.bonigarcia.wdm.WebDriverManager;

public class UIUtils extends Init {

	public WebDriver driver;
	public WebDriverWait wait;
	Logger logs;
	private Properties properties;

	public UIUtils() {
		setProperties(new Properties());
		try {
			//
			// getProperties().load(UIUtils.class.getClassLoader().getResourceAsStream(System.getProperty("user.dir")
			// + "\\sources\\files\\OR.properties"));
			properties = new Properties();
			FileInputStream fIn = new FileInputStream(
					System.getProperty("user.dir") + "\\src\\test\\java\\properties\\OR.properties");
			properties.load(fIn);
			Logger logs = Logger.getLogger("devpinoyLogger");
			System.out.println("Properties loaded");
		} catch (IOException e) {
			System.out.println("Properties not loaded");
			e.printStackTrace();
		}
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void openApp() throws IOException {

		String bname = getProperties().getProperty("Browser").trim();
		System.out.println("BrName::: " + bname);
		if (bname.equalsIgnoreCase("firefox") || bname.equalsIgnoreCase("mozilla")) {
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			test.log(LogStatus.PASS, "Firefox browser opened successfully");
		} else if (bname.equalsIgnoreCase("chrome")) {

			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			test.log(LogStatus.PASS, "Firefox browser opened successfully");
		}

		driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
		driver.manage().window().maximize();
		driver.get(getProperties().getProperty("URL"));
	}

	public void validateURL(String expectedURL) throws Exception {
		if (driver.getCurrentUrl().equalsIgnoreCase(expectedURL)) {
			// System.out.println("Correct URL opened");
			test.log(LogStatus.PASS, "Correct URL opened");
		} else {
			test.log(LogStatus.FAIL, "Incorrect URL opened");
			String screenshotPath = UIUtils.getScreenshot(driver, "ValidateURL");
			// To add it in the extent report
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
		}

	}

	public void validateText(String locatortype, String locatorValue, String expectedText) throws Exception {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		if (element.isDisplayed()) {
			if (element.getText().equalsIgnoreCase(expectedText)) {
				// System.out.println("Text Matched");
				test.log(LogStatus.PASS, "Text Validated successfully");
			} else {
				// System.out.println("Text mismatch");
				test.log(LogStatus.FAIL, "Text Validation failed, Expected :" + expectedText + ",Actual text present : "
						+ element.getText());
				String screenshotPath = UIUtils.getScreenshot(driver, "ValidateText:" + locatorValue);
				// To add it in the extent report
				test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
			}
		} else {
			test.log(LogStatus.FAIL, "Element not visible to be validated");
			String screenshotPath = UIUtils.getScreenshot(driver, "ValidateText:" + locatorValue);
			// To add it in the extent report
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
		}

	}

	public By getWebElement(String locatortype, String locatorValue) {

		try {
			switch (locatortype.toUpperCase()) {
			case "XPATH":
				return By.xpath(locatorValue);
			case "CSS":
				return By.cssSelector(locatorValue);
			case "TAGNAME":
				return By.tagName(locatorValue);
			case "PARTIALTEXT":
				return By.partialLinkText(locatorValue);
			case "LINKTEXT":
				return By.linkText(locatorValue);
			case "ID":
				return By.id(locatorValue);
			default:
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public WebElement objElement(By locatorTypes) {
		return driver.findElement(locatorTypes);

	}

	public String getValue(String key) throws IOException {
		/*
		 * properties = new Properties(); FileInputStream fIn = new
		 * FileInputStream(System.getProperty("user.dir") +
		 * "\\sources\\files\\OR.properties"); properties.load(fIn);
		 */
		return (getProperties().getProperty(key));

	}

	public void enterText(String locatortype, String locatorValue, String text) throws Exception {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		if (element.isDisplayed()) {
			element.clear();
			element.sendKeys(text);
			// System.out.println(text + "entered for" + locatortype);
			test.log(LogStatus.PASS, "Text :" + text + " entered successfully");

		} else {
			// System.out.println("Webement" + locatortype + "is not displayed");
			test.log(LogStatus.FAIL, "Element not present," + text + "not entered");
			String screenshotPath = UIUtils.getScreenshot(driver, "Entertext:" + locatorValue);
			// To add it in the extent report
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));
		}
	}

	public void clickOn(String locatortype, String locatorValue) throws Exception {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		if (element.isDisplayed()) {
			element.click();
			// System.out.println("Clicked on : " + locatortype);
			test.log(LogStatus.PASS, "Clicked on : " + locatortype);
		} else {
			// System.out.println("Webement" + locatortype + "is not displayed");
			test.log(LogStatus.PASS, "Clicked on : " + locatortype);
			String screenshotPath = UIUtils.getScreenshot(driver, "clickOn:" + locatorValue);
			// To add it in the extent report
			test.log(LogStatus.FAIL, test.addScreenCapture(screenshotPath));

		}
	}

	public void selectDropDownByText(String locatortype, String locatorValue, String textToSelect) {
		WebElement element = driver.findElement(getWebElement(locatortype, locatorValue));
		Select select = new Select(element);
		select.selectByVisibleText(textToSelect);
		System.out.println("Selected :" + locatortype + " from dropdown");
	}

	public void staticWait(String testData) {
		try {
			long time = Long.parseLong(testData);
			Thread.sleep(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeBrowser() {
		wait = new WebDriverWait(driver, 2);
		driver.close();
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public void clearTemp() {
		File file = new File("C:\\Workspace\\Automation_Hybrid\\Files");
		file.delete();

	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		// below line is just to append the date format with the screenshot name to
		// avoid duplicate names
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		// after execution, you could see a folder "FailedTestsScreenshots" under src
		// folder
		//String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + dateName+ ".png";
		String destination ="C:\\Workspace\\Automation_Hybrid\\Temp" + screenshotName + dateName+ ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		// Returns the captured file path
		return destination;
	}

}
