package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ReserveAFlightPage {
	WebDriver driver;
	private Logger logger = Logger.getLogger("flightReservation");
	
	@FindBy(name="reserveFlights")
	private WebElement continue2;
	
	public ReserveAFlightPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void clickOnbReserveFlight(){
	try{
	logger.info("Clicking on 'Reserve Flights'...");
	highlightElement(continue2);
	continue2.click();
	logger.info("Clicked on 'Reserve Flights'...");
	}catch(Exception ex){
		logger.error("Exception occurred while clicking on 'Reserve Flights': "+ex.getMessage());
	}
}
	
	
	private void highlightElement(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('Style','background: yellow; border: 2px solid red;');", element);
	}
	
}	
	
