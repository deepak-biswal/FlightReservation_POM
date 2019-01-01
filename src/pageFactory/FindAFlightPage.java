package pageFactory;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class FindAFlightPage {
	
	WebDriver driver;
	private Logger logger = Logger.getLogger("flightReservation");
	
	@FindBy(css="input[name='tripType']:nth-child(2)")
	private WebElement oneWay;
	
	@FindBy(name="fromPort")
	private WebElement departureFrom;
	
	@FindBy(name="toPort")
	private WebElement arrivalTo;
	
	@FindBy(xpath="//input[@name='servClass'][@value='First']")
	private WebElement first;
	
	@FindBy(name="airline")
	private WebElement airline;
	
	@FindBy(name="findFlights")
	private WebElement continue1;
	
	
	public FindAFlightPage(WebDriver driver){
		
		this.driver = driver;
		PageFactory.initElements(driver, this);
		
	}
	
	
	
	public void selectTripType(){
		try{
		logger.info("Clicking on 'OneWay' trip type...");
		highlightElement(oneWay);
		oneWay.click();
		logger.info("Clickedon 'OneWay' trip type...");
		}catch(Exception ex){
			logger.error("Exception occurred while clicking on 'OneWay' trip type : "+ex.getMessage() );
		}
	}
	
	public void selectDepartureFrom(String flyFrom){
		try{
		logger.info("Selecting '"+flyFrom+"' from 'Departure From' web list");
		highlightElement(departureFrom);
		Select flyfrom = new Select(departureFrom);
		flyfrom.selectByVisibleText(flyFrom);
		logger.info("Selected '"+flyFrom+"' from 'Departure From' web list");
		}catch(Exception ex){
			logger.error("Exception occurred while selecting '"+flyFrom+"' from 'Departure From' web list : "+ex.getMessage());
		}
		
	}
	public void selectArrivalTo(String flyTo){
		try{
			logger.info("Selecting  '"+flyTo+"' from 'ArrivalTo' webList");
			highlightElement(arrivalTo);
			Select flyto = new Select(arrivalTo);
			flyto.selectByVisibleText(flyTo);
			logger.info("Selected  '"+flyTo+"' from 'ArrivalTo' webList");
		
		}catch (Exception ex){
			logger.error("Exception occurred while selecting  '"+flyTo+"' from 'ArrivalTo' webList: "+ex.getMessage());
			
		}
	}
	
	public void selectClassPreference(){
		try{
		logger.info("Clicking on 'First' class...");
		highlightElement(first);
		first.click();
		logger.info("Clicked on 'First' class...");
		}catch(Exception ex){
			logger.error("Exception occurred while clicking on 'First' class : "+ex.getMessage() );
		}
	}
	
	public void selectAirlinePreference(String airlinePreference){
		try{
		logger.info("Selecting '"+airline+"' from 'airline' web list");
		highlightElement(airline);
		Select airlinepreference = new Select(airline);
		airlinepreference.selectByVisibleText(airlinePreference);
		logger.info("Selected '"+airlinePreference+"' from 'airline' web list");
		}catch(Exception ex){
			logger.error("Exception occurred while selecting '"+airlinePreference+"' from 'airline' web list : "+ex.getMessage());
		}
		
	}
	public void clickOnFindFlights(){
		try{
			logger.info("Clicking on 'FindFlights'");
			highlightElement(continue1);
			continue1.click();
			logger.info("Clicked on 'FindFlights'");
			}catch(Exception ex){
				logger.error("Exception occurred while clicking on 'FindFlights' : "+ex.getMessage() );
	}
	}
	
	private void highlightElement(WebElement element){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("arguments[0].setAttribute('Style','background: yellow; border: 2px solid red;');", element);
	}

}
