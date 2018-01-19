package test.controller.auth;

import java.util.concurrent.TimeUnit;

import org.junit.*;
import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class RecuperoPasswordTest {
	
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		
		System.setProperty("webdriver.chrome.driver", ".\\WebDriver\\chromedriver.exe");
		
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
	}
	
	//--------------------------------------------------------------------------
	//-- TEST CASES
	//--------------------------------------------------------------------------
	
	@Test
	public void TC_1_3_1() throws Exception {
		
		ripetitiveCode("email%2@prova.com");
		assertNotNull(findElement(By.id("alert-error")));
	}
	
	@Test
	public void TC_1_3_2() throws Exception {
		
		ripetitiveCode("a@prova.com");
		assertNotNull(findElement(By.id("alert-error")));
	}
	
	@Test
	public void TC_1_3_3() throws Exception {
		
		ripetitiveCode("email@prova.dominio");
		assertNotNull(findElement(By.id("alert-error")));
	}
	
	@Test
	public void TC_1_3_4() throws Exception {
		
		ripetitiveCode("");
		assertNotNull(findElement(By.id("alert-error")));
	}
	
	@Test
	public void TC_1_3_5() throws Exception {
		
		ripetitiveCode("antonio@w3play.it");
		assertNotNull(findElement(By.id("alert-error")));
	}
	
	@Test
	public void TC_1_3_6() throws Exception {
		
		ripetitiveCode("ndonio96@gmail.com");
		assertNotNull(findElement(By.id("alert-success")));
	}
	
	//-- END OF TEST CASES
	//--------------------------------------------------------------------------
	
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