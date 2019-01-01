package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookAFlightPage {
	
	WebDriver driver;
	private Logger logger = Logger.getLogger("flightReservation");
	
	@FindBy(name="passFirst0")
	private WebElement firstName;
	
	@FindBy(name="passLast0")
	private WebElement lastName;
	
	@FindBy(name="creditnumber")
	private WebElement creditNumber;
	
	@FindBy(name="buyFlights")
	private WebElement securePurchase;
	
	
	public BookAFlightPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	public void enterFirstName(String firstname){
		try{
		logger.info("Entering '"+firstname+"' in the 'First Name' edit field...");
		highlightElement(firstName);
		firstName.sendKeys(firstname);
		logger.info("Entered '"+firstname+"' in the 'First Name' edit field...");
		}catch(Exception ex){
			logger.error("Exception occurred while entering '"+firstname+"' in the 'First Name' edit field : "+ex.getMessage());
		}
	}
	
	public void enterLastName(String lastname){
		try{
		logger.info("Entering '"+lastname+"' in the 'Last Name' edit field...");
		highlightElement(lastName);
		lastName.sendKeys(lastname);
		logger.info("Entered '"+lastname+"' in the 'Last Name' edit field...");
		}catch(Exception ex){
			logger.error("Exception occurred while entering '"+lastname+"' in the 'Last Name' edit field : "+ex.getMessage());
		}
	}
	
	public void enterCreditCardNumber(String creditcardNumber){
		try{
		logger.info("Entering '"+creditcardNumber+"' in the 'Credit Card Number' edit field...");
		highlightElement(creditNumber);
		creditNumber.sendKeys(creditcardNumber);
		logger.info("Entered '"+creditcardNumber+"' in the 'Credit Card Number' edit field...");
		}catch(Exception ex){
			logger.error("Exception occurred while entering '"+creditcardNumber+"' in the 'Credit Card Number' edit field : "+ex.getMessage());
		}
	}
	
	
	public void clickOnBuyFlights(){
		try{
		logger.info("Clicking on 'SECURE PURCHASE'...");
		highlightElement(securePurchase);
		securePurchase.click();
		logger.info("Clicked on 'SECURE PURCHASE'...");
		}catch(Exception ex){
			logger.error("Exception occurred while clicking on 'SECURE PURCHASE' : "+ex.getMessage());
		}
		
	}
	
	private void highlightElement(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('Style','background: yellow; border: 2px solid red;');", element);
	}

}
