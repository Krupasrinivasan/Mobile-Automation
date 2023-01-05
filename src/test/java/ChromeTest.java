import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;

public class ChromeTest {
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
    public void ex1_login()
    {
        driver.findElement(AppiumBy.accessibilityId("Chrome")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        try {
            driver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc=\"More options\"]")).click();
            driver.findElement(AppiumBy.accessibilityId("New tab")).click();
        }catch(Exception e){}
//        driver.findElement(AppiumBy.id("com.android.chrome:id/terms_accept")).click();
//        driver.findElement(AppiumBy.id("com.android.chrome:id/negative_button")).click();
        driver.findElement(AppiumBy.id("com.android.chrome:id/search_box_text")).sendKeys("appium");
        String selector = "new UiSelector().textStartsWith(\"appium.io download\")";
        driver.findElement(AppiumBy.androidUIAutomator(selector)).click();

        WebElement textLink = driver.findElement(AppiumBy.xpath("//android.view.View[@content-desc=\"Appium https://appium.io Appium\"]/android.view.View[1]/android.view.View/android.widget.TextView[2]"));

        Assert.assertEquals(textLink.getText().trim(),"https://appium.io");
    }
}
