package pageFactory;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	
	WebDriver driver;
	private Logger logger = Logger.getLogger("flightReservation");
	
	@FindBy(name="userName")
	private WebElement userName;
	
	@FindBy(name="password")
	private WebElement password;
	
	@FindBy(name="login")
	private WebElement signIn;
	
	@FindBy(xpath="//a")
	private List<WebElement> links;
	
	public LoginPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public int getNumberOfLinks(){
		try{
		return links.size();
		}catch(Exception ex){
			logger.error("Exception occurred while fetching number of links : "+ex.getMessage());
			return 0;
		}
	}
	
	
	public ArrayList<String> getWebTableData(){
		try{
		int rowCount = driver.findElements(By.xpath("(//table)[10]/tbody/tr")).size();
		int colCount = driver.findElements(By.xpath("(//table)[10]/tbody/tr[1]/td")).size();
		
		ArrayList<String> actValues = new ArrayList<String>();
		for(int i=1;i<=rowCount;i++){
			for(int j=1;j<=colCount;j++){
				highlightElement(driver.findElement(By.xpath("(//table)[10]/tbody/tr["+i+"]/td["+j+"]")));
				System.out.print(driver.findElement(By.xpath("(//table)[10]/tbody/tr["+i+"]/td["+j+"]")).getText()+" | ");
				actValues.add(driver.findElement(By.xpath("(//table)[10]/tbody/tr["+i+"]/td["+j+"]")).getText());
			}
			System.out.println();
		}
		
		return actValues;
		}catch(Exception ex){
			logger.error("Exception occurred while fetching data values from web  table : "+ex.getMessage());
			return null;
		}
	}
	
	
	
	public void enterUserName(String username){
		try{
		logger.info("Entering '"+username+"' in the 'UserName' edit field...");
		highlightElement(userName);
		userName.sendKeys(username);
		logger.info("Entered '"+username+"' in the 'UserName' edit field...");
		}catch(Exception ex){
			logger.error("Exception occurred while entering '"+username+"' in the 'UserName' edit field : "+ex.getMessage());
		}
	}
	
	
	public void enterPassword(String pwd){
		try{
		logger.info("Entering '"+pwd+"' in the 'Password' edit field...");
		highlightElement(password);
		password.sendKeys(pwd);
		logger.info("Entered '"+pwd+"' in the 'Password' edit field...");
		}catch(Exception ex){
			logger.error("Exception occurred while entering '"+pwd+"' in the 'Password' edit field : "+ex.getMessage());
		}
	}
	
	
	public void clickOnSignIn(){
		try{
		logger.info("Clicking on 'Sign-In'...");
		highlightElement(signIn);
		signIn.click();
		logger.info("Clicked on 'Sign-In'...");
		}catch(Exception ex){
			logger.error("Exception occurred while clicking on 'Sign-In': "+ex.getMessage());
		}
	}
	
	private void highlightElement(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('Style','background: yellow; border: 2px solid red;');", element);
	}
	

}
