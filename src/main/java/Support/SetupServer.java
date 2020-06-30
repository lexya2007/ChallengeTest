package Support;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SetupServer {
    public static WebDriver driver;

    public SetupServer() {
    }

    public static void SetUp(String browser, String url) {
        try {
            System.out.println("Started SetUp keyword");
            System.out.println("Initialized Selenium Web driver > " + browser);

            DesiredCapabilities capability = new DesiredCapabilities();
            capability.setPlatform(Platform.MAC);

            switch (browser.toLowerCase()) {
                case "firefox":
                    System.setProperty("webdriver.gecko.driver", "src/main/resources/drivers/geckodriver");
                    driver = new FirefoxDriver();
                    break;
                case "safari":
                    driver = new SafariDriver();
                    break;
                case "chrome":
                    System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
                    driver = new ChromeDriver();
                    break;
            }

            driver.get(url);
            driver.getWindowHandle();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println("Can't setup server" + e);
        }
    }

    public WebElement findElement(By by) {
        WebElement element = null;
        try {
            element = driver.findElement(by);
        } catch (Exception e) {
            captureScreenShot("Could not found element");
            System.out.println("Could not found element with locator: " + e);
        }
        return element;
    }

    public List<WebElement> findElements(By by) {
        List<WebElement> element = null;
        try {
            element = driver.findElements(by);
        } catch (Exception e) {
            captureScreenShot("Could not found element");
            System.out.println("Could not found element with locator: " + e);
        }
        return element;
    }

    /**
     * Implicit Wait
     *
     * @param second
     */
    public void Implicit_Wait(int second) {
        driver.manage().timeouts().implicitlyWait(second, TimeUnit.SECONDS);
    }

    /**
     * Explicit Wait
     *
     * @param second
     * @param by
     * @return
     */
    public WebElement Explicit_Wait(int second, By by) {
        WebDriverWait wait = new WebDriverWait(driver, second);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
        return element;
    }

    /**
     * Check element is existing
     *
     * @param by
     */
    public void isElementExist(By by) {
        driver.findElement(by);
    }

    /**
     * Wait for in second
     *
     * @param second
     */
    public void Wait(int second) {
        try {
            Thread.sleep(second * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void captureScreenShot(String fileName) {
        System.out.println("Started Captured the screenshot keyword");
        String destDir = "src/main/resources/screenshot";

        // To capture screenshot.
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss aa");

        // To create folder to store screenshots
        new File(destDir).mkdirs();
        // Set file name with combination of test class name + date time.
        fileName = fileName.replace(": ","_").replace("//","_").replace("/","_");
        String destFile = dateFormat.format(new Date()) + "_" + fileName + ".png";

        // Store file at destination folder location
        try {
            FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            System.out.println("Can't store file at destination folder");
        }
    }
}
