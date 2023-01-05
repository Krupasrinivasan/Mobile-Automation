import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.beans.PropertyVetoException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;

public class Gestures {
    AppiumDriver driver;
    @BeforeTest
    public void launch_app()
    {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", "Android");
            capabilities.setCapability("platformVersion", "13");
            capabilities.setCapability("automationName","UiAutomator2");
            capabilities.setCapability("deviceName","Pixel 3a API 33");
            //capabilities.setCapability("udid","335308570804");
            //String apkPath = getClass().getClassLoader().getResource("WhatsApp.apk").getPath(); //VodQA.apk
            //capabilities.setCapability("appium:noReset", false);
            capabilities.setCapability("appium:fullReset", true);
            String apkPath = "/Users/krupa.srinivasan/Downloads/AppiumWorkshop/VodQA.apk";
            capabilities.setCapability("app", apkPath);
            driver = new AndroidDriver(new URL("http://127.0.0.1:4723"),  capabilities);

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void clickGesture()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.className("android.widget.Button")));

            WebElement elementToClick = driver.findElement(AppiumBy.className("android.widget.Button"));

            HashMap<String,String> elementMap = new HashMap<String,String>();
            elementMap.put("elementId", ((RemoteWebElement) elementToClick).getId());

            ((JavascriptExecutor) driver).executeScript("mobile: clickGesture", elementMap);

            wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.androidUIAutomator("text(\"Samples List\")")));
            driver.findElement(AppiumBy.androidUIAutomator("text(\"Back\")")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void horizontalSwipe()
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.className("android.widget.Button"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("slider1"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(AppiumBy.accessibilityId("slider")));

        Point source = driver.findElement(AppiumBy.accessibilityId("slider")).getLocation();


        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence sequence = new Sequence(finger, 0);
        sequence.addAction(finger.createPointerMove(ofMillis(200),
                PointerInput.Origin.viewport(), source.x, source.y));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(600)));
        sequence.addAction(finger.createPointerMove(ofMillis(600),
                PointerInput.Origin.viewport(), source.x + 400, source.y));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(singletonList(sequence));
    }
}
