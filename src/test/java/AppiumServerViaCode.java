import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.List;

public class AppiumServerViaCode {
    AndroidDriver driver;
    AppiumDriverLocalService service;

    @BeforeSuite
    public void setup() {
        try {
            service = new AppiumServiceBuilder()
                    .usingPort(4723)
                    .withAppiumJS(new File("/opt/homebrew/lib/node_modules/appium/build/lib/main.js"))
                    .build();
            service.start();

            if (service == null || !service.isRunning()) {
                throw new RuntimeException("An appium server node is not started!");
            } else {
                UiAutomator2Options uiAutomator2Options = new UiAutomator2Options()
                        .autoGrantPermissions()
                        .setNewCommandTimeout(Duration.ofMillis(20000))
                        .setAdbExecTimeout(Duration.ofMillis(20000))
                        .setApp("/Users/krupa.srinivasan/Downloads/AppiumWorkshop/VodQA.apk");
                driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), uiAutomator2Options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void runTest() {
        try {
            driver.findElement(AppiumBy.className("android.widget.Button")).click();
            Thread.sleep(5000);
            driver.findElement(AppiumBy.androidUIAutomator("textStartsWith(\"Native View\")")).click();
            Thread.sleep(2000);
            List<WebElement> viewElements = driver.findElements(AppiumBy.androidUIAutomator("description(\"textView\")"));
            Assert.assertEquals(viewElements.get(0).getText(), "Hello World, I'm View one ");
            Assert.assertEquals(viewElements.get(1).getText(), "Hello World, I'm View two ");
            Assert.assertEquals(viewElements.get(2).getText(), "Hello World, I'm View three ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        Killing the driver and ending the session
     */

    @Test
    public void testLogin_ImplicitWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));


        driver.findElement(AppiumBy.accessibilityId("username")).sendKeys("admin");
        driver.findElement(AppiumBy.accessibilityId("password")).sendKeys("admin");
        driver.findElement(AppiumBy.className("android.widget.TextView")).click();

    }

    @Test
    public void testLogin_ExplicitWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("username"))).sendKeys("admin");
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("password"))).sendKeys("admin");
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.className("android.widget.TextView"))).click();

    }

    @Test
    public void testLogin_CustomWait() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));


        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("username"))).sendKeys("admin");
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("password"))).sendKeys("admin");
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.className("android.widget.TextView"))).click();
    }

    @Contract(pure = true)
    private @NotNull ExpectedCondition<Boolean> elementFoundAndClicked(By locator) {
        return driver -> {
            WebElement el = driver.findElement(locator);
            el.click();
            return true;
        };
    }
    @AfterSuite
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
            if (service != null) {
                service.stop();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

