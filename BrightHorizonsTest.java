package tests;

import static org.testng.Assert.assertTrue;

import java.io.Console;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class BrightHorizonsTest {

	private WebDriver driver;

	@BeforeTest
	public void setUp() {
		
		System.setProperty("webdriver.chrome.driver",
				"D:\\Integration_automation\\Automation\\drivers\\geckodriver.exe");

		FirefoxOptions options = new FirefoxOptions();
		options.addPreference("geo.prompt.testing", true);
		options.addPreference("geo.prompt.testing.allow", true);
		options.addPreference("network.cookie.cookieBehavior", 0);
		driver = new FirefoxDriver(options);

	}

	@Test(enabled = true)
	public void test1()
	{
		
		
		
	}
	
	@Test(enabled = false)
	public void test2() throws InterruptedException {

		// Navigate to the URL
		driver.get("https://www.brighthorizons.com");
		
		// Explicit Wait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));

		// Accept site cookies
		Thread.sleep(5000);

		try {
			WebElement cookieAccept = driver.findElement(By.xpath("//*[@id='onetrust-accept-btn-handler']"));
			wait.until(ExpectedConditions.visibilityOf(cookieAccept));
			wait.until(ExpectedConditions.elementToBeClickable(cookieAccept));
			cookieAccept.click();
		} catch (Exception e) {
			System.out.println(e);
		}

		// Click on search/loop icon(top, right corner)
		WebElement searchIcon = driver.findElement(By
				.xpath("//a[@href='#subnav-search-desktop-top']//span[@class='icon-search bhc-icon-search-rounded']"));
		wait.until(ExpectedConditions.visibilityOf(searchIcon));
		wait.until(ExpectedConditions.elementToBeClickable(searchIcon));
		searchIcon.click();

		// Verify if search field is visible on page
		String searchInput = "Employee Education in 2018: Strategies to Watch";
		WebElement searchField = driver
				.findElement(By.xpath("/html/body/nav/div[3]/ul/li[10]/nav/div/div/div/div[3]/form/input"));
		if (searchField.isDisplayed()) {
			searchField.sendKeys(searchInput);
			searchField.sendKeys(Keys.ENTER);
		}

		// Verify if the search result is exact match to what you typed into search
		// locating first search result
		Thread.sleep(15000);
		WebElement firstWebElement = driver.findElement(By.xpath("/html/body/main/section[2]/div[2]/a[1]/div/h3"));
		wait.until(ExpectedConditions.visibilityOf(firstWebElement));
		String firstSearchResultTitle = firstWebElement.getText();
		System.out.println(firstSearchResultTitle);
		Assert.assertEquals(searchInput, firstSearchResultTitle);
	}

}
