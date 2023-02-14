package util;

import static junit.framework.TestCase.assertTrue;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.rules.Timeout;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import util.SeleniumDrivers;

public class Helper {

	private static WebDriver driver;

//	public Helper(WebDriver driver) {
//		super(driver);
//		// TODO Auto-generated constructor stub
//	}
//	
	public Helper(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// private final Duration defaultWaitTime;

//	public void extendedHelper(WebDriver driver) {
//		this.driver = driver;
//		PageFactory.initElements(driver, this);
//	}
	/*
	 * Spinner Element
	 */
	@FindBy(xpath = "//div[@class='spinner-container']")
	private static WebElement spinner;

	public static void spinnerElement() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.invisibilityOf(spinner));
	}

	/***
	 * Login
	 */
	public static void loginToHodgeSite(String hodgeEnv) {
		ConfigReader configReader = new ConfigReader();
		Properties prop = configReader.init_prop();
		if (hodgeEnv.equalsIgnoreCase("QA")) {
			String hodgeQAUrl = prop.getProperty("hodgeQAUrl");
			driver.get(hodgeQAUrl);
		} else if (hodgeEnv.equalsIgnoreCase("dev")) {
			String hodgeDevUrl = prop.getProperty("hodgeDevUrl");
			driver.get(hodgeDevUrl);
		} else if (hodgeEnv.equalsIgnoreCase("UAT")) {
			String hodgeUATUrl = prop.getProperty("hodgeUATUrl");
			driver.get(hodgeUATUrl);
		} else {
			System.out.println("incorrect environment");
		}
	}

	public static void loginToMambu(String hodgeEnv) {
		ConfigReader configReader = new ConfigReader();
		Properties prop = configReader.init_prop();
		if (hodgeEnv.equalsIgnoreCase("Mambu")) {
			String mambuUrl = prop.getProperty("MambuUrl");
			driver.get(mambuUrl);
		} else {
			System.out.println("incorrect environment");
		}
	}

	/**
	 * CSV file path
	 **/

	public static String generateFilePath(String fileName) {
		return ".\\src\\test\\resources\\Data\\" + fileName + ".csv";

	}

	/****
	 * Handles clear field Handles sendkeys
	 * 
	 */

	public static void clearAndSendKeys(WebElement element, String text) {
		element.clear();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		element.sendKeys(text);
	}

	public static void clickAndSendKeys(WebElement element, String text) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		element.click();
		element.sendKeys(text);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
	}

	public static void clickElement(WebElement element) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		element.click();
	}

	public static void clickElementwithExecutor(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);

	}

	public static void clearField(WebElement element) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		element.clear();
	}

	public static void sendKeys(WebElement element, String text) {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		element.sendKeys(text);
	}

	/***
	 * Different type of waits in the page
	 * 
	 * @param driver
	 * @param condition
	 * @param timeOutInSeconds
	 * @param timeOutMessage
	 * @return
	 */

	// Implicit Wait

	@SuppressWarnings("deprecation")
	public static void setimplicitWait(long timeout, TimeUnit unit) {
		driver.manage().timeouts()
				// .implicitlyWait(Duration.ofSeconds(20));
				.implicitlyWait(timeout, unit.SECONDS);
	}

	@SuppressWarnings("deprecation")
	public static void pageLoadTimeout(int timeout, TimeUnit unit) {
		// TODO Auto-generated method stub
		driver.manage().timeouts().pageLoadTimeout(timeout, unit.SECONDS);

	}

	// Explicit Wait

	public static void setexplicitWait(Duration i) {
		WebDriverWait wait = new WebDriverWait(driver, i);
		// to be implemented in
		// -->wait.until(ExpectedConditions.elementToBeClickable(WebElement));
	}

	public static boolean waitForElementToBeClickable(WebElement element, int timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.elementToBeClickable(element));
			return true;

		} catch (TimeoutException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static boolean waitForElement(WebElement element, int timeOutInSeconds) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSeconds));
			wait.until(ExpectedConditions.visibilityOf(element));
			return true;

		} catch (TimeoutException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/****
	 * Is Enabled Is disabled condition check
	 * 
	 * @throws InterruptedException
	 */

	public static void elementIsEnabled(WebElement element) throws InterruptedException {
		if (element.isEnabled()) {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
			wait.until(ExpectedConditions.visibilityOf(element));
			Thread.sleep(2000);
			element.click();
		}
	}

	/***
	 * Validates current page of the User
	 */

	public static void isUserOnPage(String actualUrl) {
		String[] page = actualUrl.split("/");
		String userOn = driver.getCurrentUrl();

		if (actualUrl.equals(userOn)) {
			System.out.println("User on " + page[3] + " Page");
		} else {
			System.out.println("User is not on the requested page " + userOn);
		}
	}

	/*
	 * Switching between windows
	 * 
	 */

	public static void switchToWindow(String windowName) {
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		String parentWindow = i1.next();
		String childWindow = i1.next();
		if (windowName.equalsIgnoreCase("main")) {
			driver.switchTo().window(parentWindow);

		} else if (windowName.equalsIgnoreCase("child")) {
			driver.switchTo().window(childWindow);
		} else {
			System.out.println("Incorrect window");
		}
	}

	public static void switchToWindowByIndex(int index) {
		ArrayList<String> hnds = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(hnds.get(index));
	}

	/**
	 * Handles different type of scrolling
	 *
	 * @param driver WebDriver
	 * @throws InterruptedException
	 */

	public static void scrollToElement(WebElement element) throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true);", element);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(ExpectedConditions.visibilityOf(element));
		Thread.sleep(5000);
	}

//	public static void scrollBetween( ) {
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//		js.executeScript("window.scrollBy(0,600)");
//		
//	}

	public static void scrollAndClick(WebElement element) {
		// WebElement element = driver.findElement(by);
		int elementPosition = element.getLocation().getY();
		String js = String.format("window.scroll(0, %s)", elementPosition);
		((JavascriptExecutor) driver).executeScript(js);
		element.click();
	}

	public static void scroll(int percent) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0," + percent + ")", "");
		Thread.sleep(2000);
	}

	public static void scrollToTopOfPage() throws InterruptedException {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		js.executeScript("window.scrollTo(document.body.scrollHeight, 0)");
		Thread.sleep(2000);
	}

	public static void scrollToBottomOfPage() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		Thread.sleep(2000);
	}

	public static void scrollElementToMiddleOfThePage(WebElement element) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
				+ "var elementTop = arguments[0].getBoundingClientRect().top;"
				+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
		js.executeScript(scrollElementIntoMiddle, element);
		Thread.sleep(3000);

	}

	public static void scrollToElementUntilClickable(WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].click();", element);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5000));
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	/***
	 * Handling Alerts Present in the page
	 * 
	 * @param driver
	 * @return
	 */
	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert().accept();
			return true;
		} // try
		catch (NoAlertPresentException exception) {
			return false;
		} // catch
	} // isAlertPresent()

	public static boolean acceptAlert() {

		long waitForAlert = System.currentTimeMillis() + 2;
		boolean boolFound = false;

		do {
			try {
				Alert alert = driver.switchTo().alert();
				if (alert != null) {
					alert.accept();
					boolFound = true;
				}
			} catch (Exception ignored) {
			}
		} while ((System.currentTimeMillis() < waitForAlert) && (!boolFound));

		return boolFound;
	}

	/***
	 * Handling Page Actions
	 * 
	 * @param driver
	 * @param by
	 */

	public static void mouseHover(By by) {
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(by);
		action.moveToElement(we).build().perform();
	}

	public static void doubleClick(By by) {
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(by);
		action.doubleClick(we).build().perform();
	}

	public static void keyboardAction_Up() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_UP);
		action.sendKeys(Keys.ENTER);
		action.perform();
	}

	public static void keyboardAction_Down() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ARROW_DOWN);
		action.sendKeys(Keys.ENTER);
		action.perform();
	}

	public static void keyboardAction_Enter() {
		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER);
		action.perform();
	}

	public static void moveToElement(WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element).click().perform();
		action.perform();
	}

//	public static void scrollAndClick( , By by) {
//		WebElement element = driver.findElement(by);
//		int elementPosition = element.getLocation().getY();
//		String js = String.format("window.scroll(0, %s)", elementPosition);
//		((JavascriptExecutor) driver).executeScript(js);
//		element.click();
//	}

	/**
	 * Take Screen Shot for Pre condition
	 **/

	public static void takeScreenShot() throws IOException {
		String title = driver.getCurrentUrl();
		String[] screenShotName = title.split("/");
		Date dt = new Date();
		DateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(
				".\\src\\test\\resources\\screenShot\\" + screenShotName[2] + "" + df.format(dt) + ".png");
		FileUtils.copyFile(SrcFile, DestFile);
	}

	public static void waitForElementToBeDisplayed(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public static void waitForElementToBeInvisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public static void waitForElementPresent(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static void waitForOneOfTheElement(WebElement firstElement, WebElement secondElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOf(firstElement),
				ExpectedConditions.invisibilityOf(secondElement)));
	}

	/***
	 * Radio buttons/Checkboxes validation
	 * 
	 * @param radioButton
	 * @return
	 */
	public static boolean getradioButtonState(WebElement radioButton) {

		return radioButton.isSelected();
	}

	public static boolean getCheckboxState(WebElement checkbox) {

		return checkbox.isSelected();
	}

	/**
	 * Handling select class with different methods
	 *
	 * @param driver  WebDriver
	 * @param element dropdown element
	 * @param text    the string/text of the option in the list
	 */
	public static void selectFromDropDownByText(WebElement element, String text) {
		try {
			Select dropdown = new Select(element);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			dropdown.selectByVisibleText(text);
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
		}
	}

	public static void selectFromDropDownByValue(WebElement element, String text) {
		try {
			Select dropdown = new Select(element);
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			dropdown.selectByValue(text);
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
		}
	}

	public static void selectFromDropDownByPartialText(WebElement element, String partialText) {

		try {
			Select dropdown = new Select(element);

			List<WebElement> dropDownList = dropdown.getOptions();

			for (WebElement option : dropDownList) {
				String currentOption = option.getText();
				if (currentOption.contains(partialText))
					dropdown.selectByVisibleText(currentOption);
			}
		} catch (StaleElementReferenceException e) {
			e.printStackTrace();
		}
	}

	public static void zoomOut(double value) throws Exception {
		Thread.sleep(1000);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.body.style.zoom = '" + value + "'");
		Thread.sleep(2000);
	}

	public static void zoomIn() {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("document.body.style.zoom = '0'");
	}

}
