package testcases;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.google.common.io.Files;

import excelInputAndOutput.ExcelInteraction;
import pageFactory.BookAFlightPage;
import pageFactory.FindAFlightPage;
import pageFactory.FlightConfirmationPage;
import pageFactory.LoginPage;
import pageFactory.ReserveAFlightPage;
import utility.Constant;


public class ExcuteTest {
	WebDriver driver;
	ExcelInteraction excel;
	Properties allObjects;
	LoginPage loginPage;
	FindAFlightPage findAFlightPage;
	ReserveAFlightPage reserveAFlightPage;
	BookAFlightPage bookAFlightPage;
	FlightConfirmationPage flightConfirmationPage;
	ExtentReports extent;
	ExtentTest logger;
	ExtentHtmlReporter htmlReporter;
	
	@Parameters({"browser"})
	@BeforeTest
	public void setUp(String browser) throws IOException{
		if(browser.equals("chrome")){
			System.setProperty("webdriver.chrome.driver",Constant.chromeDriverPath);
			driver = new ChromeDriver();
		}else if(browser.equals("firefox")){
			System.setProperty("webdriver.gecko.driver", Constant.geckoDriverPath);
			driver = new FirefoxDriver();
		}
		
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		
		excel = new ExcelInteraction();
		
		extent = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(new File(System.getProperty("user.dir")+"//Report//TestExecutionReport.html"));
		
		htmlReporter.config().setChartVisibilityOnOpen(true);
		htmlReporter.config().setDocumentTitle("Extent Report : Flight Reservation");
		htmlReporter.config().setReportName("E2E Testing Of Flight Reservation");
		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
		  // OR
		
		//htmlReporter.loadConfig(System.getProperty("user.dir")+"//extentreports-java-3.1.5//extent-config.xml");
		extent.attachReporter(htmlReporter);
		
		
		// Provide Some System Information
		extent.setSystemInfo("Project Name", "Flight Reservation(POM)");
		extent.setSystemInfo("Browser", browser);
		extent.setSystemInfo("OS", System.getProperty("os.name"));
		extent.setSystemInfo("Java Version",System.getProperty("java.version"));
		extent.setSystemInfo("User", System.getProperty("user.name"));
		InetAddress myHost = InetAddress.getLocalHost();
		extent.setSystemInfo("Host Name", myHost.getHostName());
		
		
		
}
	
	@BeforeMethod
	private void getTestMethodName(Method method){
		logger = extent.createTest(method.getName());
	}
	
	@Test(priority=0)
	private void launchFlightReservationApplication() throws IOException{
		// Invoke Application Under Test
		String url = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "InvokeApplication", 1, 0);
		driver.get(url);
		String expTitle = 	excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "InvokeApplication", 1, 1);	
		Assert.assertEquals(driver.getTitle(),expTitle);
		
	}
	
	// Verify that 16 number of navigation links are present on home page
	//@Test(priority=1)
	@Test(dependsOnMethods="launchFlightReservationApplication")
	private void verifyLinks(){
		loginPage = new LoginPage(driver);
		Assert.assertEquals(loginPage.getNumberOfLinks(), 16);
		
	}
	
	
	//@Test(priority=2)
	@Test(dependsOnMethods="verifyLinks")
	private void verifyWebTable(){
		loginPage = new LoginPage(driver);
		ArrayList<String> expValues = new ArrayList<String>();
		expValues.add("Atlanta to Las Vegas");
		expValues.add("$398");
		expValues.add("Boston to San Francisco");
		expValues.add("$513");
		expValues.add("Los Angeles to Chicago");
		expValues.add("$168");
		expValues.add("New York to Chicago");
		expValues.add("$198");
		expValues.add("Phoenix to San Francisco");
		expValues.add("$213");
		
		
		
		Assert.assertTrue(expValues.equals(loginPage.getWebTableData()));
		
		
	}
	
	// Verify that Login To Flight Reservation is successful after entering valid credentials
	//@Test(priority=3)
	@Test(dependsOnMethods="verifyWebTable")
	private void login() throws IOException{
		loginPage = new LoginPage(driver);
		// Enter UserName
		String  userName = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "Login", 1, 0);
		loginPage.enterUserName(userName);
	
		// Enter Password
		String password = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "Login", 1, 1);
		loginPage.enterPassword(password);
		
		// Click on 'Sign-In'
		loginPage.clickOnSignIn();
		String expTitle = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "Login", 1, 2);
		
		Assert.assertEquals(driver.getTitle(),expTitle);
	}
	
	// Verify that User should be able to reserve a flight after entering valid flight details
	//@Test(priority=4)
	@Test(dependsOnMethods="login")
	public void reserveAFlight() throws IOException{
		findAFlightPage = new FindAFlightPage(driver);
		// Select Trip Type
		findAFlightPage.selectTripType();
		
		
		// Select Departure From
		String departureFrom = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "FlightDetails", 1, 0);
		findAFlightPage.selectDepartureFrom(departureFrom);
				
		// Select Arrival To
		String arrivalTo = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "FlightDetails", 1, 1);
		findAFlightPage.selectArrivalTo(arrivalTo);
				
				
		// Select Class Preference
		
		findAFlightPage.selectClassPreference();
				
		// Select Airline Preference
		String airlinePreference = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "FlightDetails", 1, 2);
		findAFlightPage.selectAirlinePreference(airlinePreference);
				
		// Click on 'CONTINUE1'
				
		findAFlightPage.clickOnFindFlights();
		
		reserveAFlightPage = new ReserveAFlightPage(driver);		
		// Click on 'CONTINUE2'
		reserveAFlightPage.clickOnbReserveFlight();
		
		
	}
	
	// Verify that user should be able to book a ticket after entering valid passenger details
	//@Test(priority=5)
	@Test(dependsOnMethods="reserveAFlight")
	private void bookATicket() throws IOException{
		bookAFlightPage = new BookAFlightPage(driver);
		// Enter First Name
		String firstName = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "PersonalDetails", 1, 0);
		bookAFlightPage.enterFirstName(firstName);
		// Enter Last Name
		String lastName = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "PersonalDetails", 1, 1);
		bookAFlightPage.enterLastName(lastName);
		// Enter Credit Card Number
		String cardNumber = excel.getCellData(System.getProperty("user.dir")+"//TestData", "FR.xls", "PersonalDetails", 1, 2);
		bookAFlightPage.enterCreditCardNumber(cardNumber);
		// Click on 'SECURE PURCHASE'
		bookAFlightPage.clickOnBuyFlights();
		
		// Verify Booking Confirmation Text
		flightConfirmationPage = new FlightConfirmationPage(driver);
		Assert.assertTrue(flightConfirmationPage.verifyBookingConfirmationText());

		
	}
	
	@AfterMethod
	private void getTestStatus(ITestResult result) throws IOException{
		
		if(result.getStatus() == ITestResult.SUCCESS){
			logger.log(Status.PASS, MarkupHelper.createLabel(result.getName()+" got Passed", ExtentColor.GREEN));
			String screenShotPath = getScreenShot(result.getName());
			logger.addScreenCaptureFromPath(screenShotPath);
		}else if(result.getStatus() == ITestResult.FAILURE){
			logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName()+" got failed due to "+result.getThrowable(), ExtentColor.RED));
			String screenShotPath = getScreenShot(result.getName());
			logger.addScreenCaptureFromPath(screenShotPath);
		}else if(result.getStatus() == ITestResult.SKIP){
			logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+" got skipped", ExtentColor.CYAN));
		}
		
	}
	
	
private String getScreenShot(String screenShotName) throws IOException{
		
		TakesScreenshot srcShot = (TakesScreenshot)driver;
		
		File SrcFile = srcShot.getScreenshotAs(OutputType.FILE);
		String dateFormat = new SimpleDateFormat("dd_MM_YYYY_hh_mm_ss").format(new Date());
		String destination = System.getProperty("user.dir")+"//screenshots//"+screenShotName+"_"+dateFormat+".png";
		File destFile = new File(destination);
		Files.copy(SrcFile, destFile);
		return destination;
		
	}
	
	@AfterTest
	public void closeDriver(){
		extent.flush();
		driver.quit();
	}

}
