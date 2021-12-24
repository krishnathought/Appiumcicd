package Framework;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DriverTest {
    AppiumDriver driver;

    @Test
    public void InvalidUserName(){
        System.out.println("inside invalid username test");
        MobileElement userNameTxtField=(MobileElement) driver.findElementByAccessibilityId("test-Username");
        MobileElement passwordTxtField = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-Password"));
        MobileElement loginButton = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-LOGIN"));

        userNameTxtField.sendKeys("invalidUserName");
        passwordTxtField.sendKeys("secret_sauce");
        loginButton.click();

        MobileElement errText=(MobileElement) driver.findElement(MobileBy.xpath("//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView"));

        String actualErrText=errText.getAttribute("text");
        String expectedErrtext="Username and password do not match any user in this service.";
System.out.println("actual error"+actualErrText);
System.out.println("expected error"+expectedErrtext);

Assert.assertEquals(actualErrText, expectedErrtext);
        //android.view.ViewGroup[@content-desc="test-Error message"]/android.widget.TextView


        //Username and password do not match any user in this service.

    }
    @Test
    public void InvalidPassword(){
        MobileElement userNameTxtField=(MobileElement) driver.findElementByAccessibilityId("test-Username");
        MobileElement passwordTxtField = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-Password"));
        MobileElement loginButton = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-LOGIN"));
        System.out.println("inside invalidpassword");
        userNameTxtField.sendKeys("standard_user");
        passwordTxtField.sendKeys("invalidPassword");
        loginButton.click();

        MobileElement errText=(MobileElement) driver.findElement(MobileBy.xpath("//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView"));

        String actualErrText=errText.getAttribute("text");
        String expectedErrtext="Username and password do not match any user in this service.";
        System.out.println(actualErrText);
        System.out.println(expectedErrtext);

        Assert.assertEquals(actualErrText, expectedErrtext);
    }

    @Test
    public void successful_login(){
        MobileElement userNameTxtField=(MobileElement) driver.findElementByAccessibilityId("test-Username");
        MobileElement passwordTxtField = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-Password"));
        MobileElement loginButton = (MobileElement) driver.findElement(MobileBy.AccessibilityId("test-LOGIN"));


        userNameTxtField.sendKeys("standard_user");
        passwordTxtField.sendKeys("secret_sauce");
        loginButton.click();
        MobileElement ProductTitleText=(MobileElement) driver.findElement(MobileBy.xpath("//android.view.ViewGroup[@content-desc='test-Cart drop zone']/android.view.ViewGroup/android.widget.TextView"));


        String actualText=ProductTitleText.getAttribute("text");
        String expectedText="PRODUCTS";

       System.out.println("Actual productsswewe title"+actualText);
        System.out.println("Expected Product title"+expectedText);
       Assert.assertEquals(actualText, expectedText);
    }
@BeforeTest
    public void BeforeClass() throws MalformedURLException{
        DesiredCapabilities cap = new DesiredCapabilities();
       cap.setCapability(MobileCapabilityType.PLATFORM_NAME,"Android");
       cap.setCapability("newCommandTimeout", 300);
       URL url =new URL("http://127.0.0.1:4723/wd/hub");

       cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel4");

        cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
       cap.setCapability(MobileCapabilityType.UDID, "emulator-5554");
       //  cap.setCapability(MobileCapabilityType.APP,"/Users/krishnamurthy/Documents/Appium/demo/src/main/resources/Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");

         cap.setCapability("appPackage","com.swaglabsmobileapp");
        cap.setCapability("appActivity","com.swaglabsmobileapp.SplashActivity");


       cap.setCapability("avd", "Pixel4");
        cap.setCapability("avdLaunchTimeout", "360000");

    /*    cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Galaxy");

         cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"UiAutomator2");
        cap.setCapability(MobileCapabilityType.UDID, "192.168.29.234:5555");
        cap.setCapability("PlatformName", "Android");
        cap.setCapability("PlatformVersion", "11");
        cap.setCapability("appPackage","com.swaglabsmobileapp");
        cap.setCapability("appActivity","com.swaglabsmobileapp.SplashActivity");
        */
         driver = new AndroidDriver<MobileElement>(url, cap);
        String sessionid=driver.getSessionId().toString();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public void AfterClass(){
       // driver.quit();
    }

}
