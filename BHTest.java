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
	private WebDriverWait wait;

	@BeforeTest
	public void setUp() throws InterruptedException {
		
		System.setProperty("webdriver.chrome.driver",
				"D:\\Integration_automation\\Automation\\drivers\\geckodriver.exe");

		FirefoxOptions options = new FirefoxOptions();
		options.addPreference("geo.prompt.testing", true);
		options.addPreference("geo.prompt.testing.allow", true);
		options.addPreference("network.cookie.cookieBehavior", 0);
		driver = new FirefoxDriver(options);
		
		// Navigate to the URL
				driver.get("https://www.brighthorizons.com");
				
				// Explicit Wait
				wait = new WebDriverWait(driver, Duration.ofSeconds(70));

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

	}

	@Test(enabled = true)
	public void test1() throws InterruptedException
	{
        // Locate the "Find a Center" option in the top header
		Thread.sleep(5000);
        WebElement findACenter = driver.findElement(By.cssSelector("nav.nav-shared:nth-child(5) > div:nth-child(3) > ul:nth-child(2) > li:nth-child(7) > a"));
        wait.until(ExpectedConditions.visibilityOf(findACenter));
		wait.until(ExpectedConditions.elementToBeClickable(findACenter));
		
        // Click on the "Find a Center" option
        findACenter.click();
		
        //Switch to the new page
        for (String handle : driver.getWindowHandles()) {
            driver.switchTo().window(handle);
        }

        // Verify that the new page's URL contains the expected locator
        String currentURL = driver.getCurrentUrl();
        String expectedLocator = "/child-care-locator";
		Assert.assertTrue(currentURL.contains(expectedLocator));
		
		 // Locate the search box element
        WebElement searchBox = driver.findElement(By.xpath("//input[@id='addressInput']"));

        // Type "new york" into the search box and press Enter
        searchBox.sendKeys("new york");
        searchBox.sendKeys(Keys.ENTER);

		// Locate the element that displays the number of found centers
        WebElement foundCentersElement = driver.findElement(By.xpath("//span[@class='resultsNumber']"));
        
        int foundCentersCount = Integer.parseInt(foundCentersElement.getText());
        System.out.println("Element that displays the number of found centers"+foundCentersCount);
        
        // Locate the list of displayed centers
        List<WebElement> displayedCenters = driver.findElements(By.xpath("//*[@class='buttonContainer']"));
        int displayedCentersCount = displayedCenters.size();

        // Compare the two values
        Assert.assertEquals(foundCentersCount, displayedCentersCount);
		
		// Locate the list of elements
        List<WebElement> elements = driver.findElements(By.xpath("/html/body/main/section/div"));

        // Click on the first center on the list
        if (!elements.isEmpty()) {
            elements.get(1).click();
        }
		
        //Get center name from list
        String listCenterName = driver.findElement(By.xpath("//html/body/main/section/div[3]/div[2]/h3")).getText();        
        // Get the center name and address from the popup
        String popupCenterName = driver.findElement(By.xpath("//span[@class='mapTooltip__headline']")).getText();
    
        System.out.println("center name on pop up"+ popupCenterName);
        
        // Verify that the center name and address match
        Assert.assertEquals(popupCenterName, listCenterName);
			
        }

	
	@Test(enabled = true)
	public void test2() throws InterruptedException {

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
