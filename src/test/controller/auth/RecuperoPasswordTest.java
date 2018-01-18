package test.controller.auth;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import util.IO;

public class RecuperoPasswordTest {
	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("webdriver.chrome.driver", ".\\WebDriver\\chromedriver.exe");
		
		//driver = new ChromeDriver();
		driver = new HtmlUnitDriver(true); //con true si attiva il supporto a  javascript
		
		baseUrl = "https://www.katalon.com/";
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
	}
	
	/** contiene del codice che va ripetuto tra i test cases */
	public void ripetitiveCode(String email) throws Exception {
		
		driver.get("http://localhost:8080/W3Play/homepage");
		
		findElement(By.id("login-link")).click();
		findElement(By.id("recovery")).click();
		
		WebElement element = findElement(By.id("email"));
		element.sendKeys(email);
		element.submit();
		
		IO.println(driver.getCurrentUrl());
		
		//manca il resto!...
	}
	
	@Test
	public void testUntitledTestCase() throws Exception {
		
		ripetitiveCode("email");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	//shortcut per trovare un elemento con il driver
	private WebElement findElement(By arg) {
		
		return driver.findElement(arg);
	}
}