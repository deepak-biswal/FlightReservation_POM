package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class FlightConfirmationPage {
	WebDriver driver;
	boolean isDisplayed = false;
	private Logger logger = Logger.getLogger("flightReservation");
	
	
	@FindBy(xpath="//font[contains(text(),'itinerary has been booked!')]")
	private WebElement confirmationText;
	
	public FlightConfirmationPage(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean verifyBookingConfirmationText(){
	
		try{
			logger.info("Verifying presenrce of booking confirmation text...");
			if(confirmationText.isDisplayed()){
				highlightElement(confirmationText);
				isDisplayed = true;
			}else{
				isDisplayed = false;
		}
			return isDisplayed;
	}catch(Exception ex){
		logger.error("Exception occurred while verifying presence of booking confirmation text : "+ex.getMessage());
		return isDisplayed;
	}
}
	
	private void highlightElement(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('Style','background: yellow; border: 2px solid red;');", element);
	}
	
}
