import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class BasicTest_android {
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
    public void ex1_login()
    {
        driver.findElement(AppiumBy.accessibilityId("username")).sendKeys("admin");
        driver.findElement(AppiumBy.accessibilityId("password")).sendKeys("admin");
        driver.findElement(AppiumBy.className("android.widget.TextView")).click();

        String selector = "new UiSelector().textStartsWith(\"Native View\")";
        driver.findElement(AppiumBy.androidUIAutomator(selector)).click();

        WebElement textView1 = driver.findElement(AppiumBy.xpath("(//android.widget.TextView[@content-desc=\"textView\"])[1]"));

        Assert.assertEquals(textView1.getText().trim(),"Hello World, I'm View one");
    }

}
