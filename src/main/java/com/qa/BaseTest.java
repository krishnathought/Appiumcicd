package com.qa;

import java.io.*;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;


import com.qa.utils.TestUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.FindsByAndroidUIAutomator;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.screenrecording.CanRecordScreen;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;


import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
	
	 protected static ThreadLocal<AppiumDriver>  driver = new ThreadLocal<AppiumDriver>() ;
	    protected static ThreadLocal <Properties>  props = new ThreadLocal<Properties>();
        protected  static ThreadLocal <HashMap<String,String>> strings= new ThreadLocal<HashMap<String,String>>();
        protected static ThreadLocal<String> platform =  new ThreadLocal<String>();
        protected static ThreadLocal<String> dateTime = new ThreadLocal<String>();
        protected static ThreadLocal<String> deviceName = new ThreadLocal<String>();
        private static AppiumDriverLocalService server;
    TestUtils utils;

    public AppiumDriver getDriver(){

        return driver.get();
    }
    public void setDriver(AppiumDriver driver2){
        driver.set(driver2);
    }

    public Properties getProps(){

        return props.get();
    }
    public void setProps(Properties prop2){
        props.set(prop2);
    }
    public HashMap<String ,String> getStrings(){

        return strings.get();
    }
    public void setStrings(HashMap<String ,String> strings2){
        strings.set(strings2);
    }

    public String getPlatform(){

        return platform.get();
    }
    public void setPlatform(String platform2){
        platform.set(platform2);    }

    public String getDateTime(){
        return dateTime.get();

    }

    public void setDateTime(String dateTime2){
        dateTime.set(dateTime2);
    }
    public String getDeviceName(){
        return deviceName.get();

    }

    public void setDeviceName(String deviceName1){
        deviceName.set(deviceName1);
    }
	// constructor for page factory

	public BaseTest(){

	    // Appium field decorater which will helps to initalize the UI elements for page factory
	    PageFactory.initElements(new AppiumFieldDecorator(getDriver(), Duration.ofSeconds(2)),this);
	}


    @BeforeSuite
    public void beforeSuite() throws Exception {
        server = getAppiumService();
        if (!checkIfAppiumServerIsRunnning(4723)) {
            server.start();
            // method to avoid printing appium server logs in console
            server.clearOutPutStreams();
            System.out.println("Appium server started");
        } else {
            System.out.println("Appium server already running");

        }
    }

    /// another method instad of isRunning()
        public boolean checkIfAppiumServerIsRunnning(int port) throws Exception {
            boolean isAppiumServerRunning = false;
            ServerSocket socket;
            try {
                socket = new ServerSocket(port);
                socket.close();
            } catch (IOException e) {
                System.out.println("1");
                isAppiumServerRunning = true;
            } finally {
                socket = null;
            }
            return isAppiumServerRunning;
        }



    @AfterSuite
    public void  afterSuite(){

        server.stop();
        System.out.println("Appium server stopped");
    }

    public AppiumDriverLocalService getAppiumServerDefault(){

        return AppiumDriverLocalService.buildDefaultService();
    }
    public AppiumDriverLocalService getAppiumService() {
        HashMap<String, String> environment = new HashMap<String, String>();

        environment.put("PATH", "/Users/krishnamurthy/.nvm/versions/node/v16.13.0/bin:/Users/krishnamurthy/Library/Android/sdk/tools:/Users/krishnamurthy/Library/Android/sdk/platform-tools:/Users/krishnamurthy/Library/Android/sdk/emulator:/Library/Java/JavaVirtualMachines/jdk-17.0.1.jdk/Contents/Home/bin:/opt/homebrew/bin:/opt/homebrew/sbin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin:/Users/krishnamurthy/Downloads/Softwares/Maven/bin" + System.getenv("PATH"));
        environment.put("ANDROID_HOME", "//Users/krishnamurthy/Library/Android/sdk");

        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()

                .usingDriverExecutable(new File("/Users/krishnamurthy/.nvm/versions/node/v16.13.0/bin/node"))
                .withAppiumJS(new File("/Users/krishnamurthy/.nvm/versions/node/v16.13.0/lib/node_modules/appium/build/lib/main.js"))
                .usingPort(4723)
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE)
                .withEnvironment(environment)
        );
    }

    // for Start recording logic
    @BeforeMethod
    public void beforeMethod(){
        ((CanRecordScreen) getDriver()).startRecordingScreen();
    }

    // Synchronized - at a time only oene thread can use this method. other threads need to wait
    @AfterMethod
    public synchronized void afterMethod(ITestResult result){
       String media= ((CanRecordScreen) getDriver()).stopRecordingScreen();

       //create the video incase of faliure
        if(result.getStatus() ==1) {
            Map<String, String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
            String dir = "Videos" + File.separator + params.get("platformName") + "_" + params.get("deviceName") + File.separator + getDateTime()
                    + File.separator + result.getTestClass().getRealClass().getSimpleName();

            File videoDir = new File(dir);
            synchronized (videoDir){
                if (!videoDir.exists()) {
                    videoDir.mkdirs();
                }
            }

            try {
                FileOutputStream fos = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
                fos.write(Base64.getDecoder().decode(media));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	 @Parameters({"platformName","deviceName","emulator","udid"})
    @BeforeTest
    public void beforeTest(String platformName,String deviceName,String emulator,String udid) throws IOException{
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability(MobileCapabilityType.PLATFORM_NAME,platformName);
        cap.setCapability("newCommandTimeout", 300);
         utils= new TestUtils();

         setDateTime(utils.dateTime());
        setPlatform(platformName);
         setDeviceName(deviceName);
         InputStream inputStream = null;
         InputStream stringsis = null;
         Properties props = new Properties();
         AppiumDriver driver;
         URL url =null;
         try {
            props= new Properties();
            String propFileName="config.properties";
            String xmlFileName="Strings/Strings.xml";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            props.load(inputStream);
            setProps(props);
            stringsis=getClass().getClassLoader().getResourceAsStream(xmlFileName);
            setStrings(utils.parseStringXML(stringsis));


            cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
 
            cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,props.getProperty("androidAutomationName"));
            if(emulator.equalsIgnoreCase("true")) {
                cap.setCapability("avd", deviceName);
                cap.setCapability("avdLaunchTimeout", "360000");
              cap.setCapability(MobileCapabilityType.UDID, "emulator-5554");
                 url =new URL(props.getProperty("appiumURL") + "4723/wd/hub");




            }
            else{

                cap.setCapability(MobileCapabilityType.UDID, udid);
                 url =new URL(props.getProperty("appiumURL") + "4723/wd/hub");
            }
           //URL appUrl =getClass().getClassLoader().getResource(props.getProperty("andriodAppLocation"));
            String appUrl =getClass().getResource(props.getProperty("andriodAppLocation")).getFile();
           //System.out.println("App url"+appUrl);
           utils.log(appUrl);
           cap.setCapability(MobileCapabilityType.APP,appUrl);
    
             cap.setCapability("appPackage",props.getProperty("androidAppPackage"));
            cap.setCapability("appActivity",props.getProperty("andriodAppActivity")); 



          //  URL url =new URL(props.getProperty("appiumURL"));
   
            driver = new AndroidDriver<MobileElement>(url, cap);
            String sessionid=driver.getSessionId().toString();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            setDriver(driver);
        }

         catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if(inputStream !=null){
                inputStream.close();
            }
            if(stringsis !=null){
                stringsis.close();
            }
        }
 
}
    public void waitForvisibility(MobileElement e){
        WebDriverWait wait = new WebDriverWait(getDriver(), TestUtils.WAIT);
        wait.until(ExpectedConditions.visibilityOf(e));
    }
    public void waitForvisibility(WebElement e){
        Wait<WebDriver> wait =new FluentWait<WebDriver>(getDriver())
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(e));
    }
public void click(MobileElement e){
    waitForvisibility(e);
    e.click();
}
public void sendKeys(MobileElement e,String txt){
    waitForvisibility(e);
    e.sendKeys(txt);
}

public String getAttribute(MobileElement e,String attribute)
{
    waitForvisibility(e);
   return e.getAttribute(attribute);

}

public void closeApp()
{
    ((InteractsWithApps)getDriver()).closeApp();
}

    public void launchApp()
    {
        ((InteractsWithApps)getDriver()).launchApp();
    }

    public MobileElement scrollToElement() {
        return (MobileElement) ((FindsByAndroidUIAutomator)getDriver()).findElementByAndroidUIAutomator(
                "new UiScrollable(new UiSelector()" + ".scrollable(true)).scrollIntoView("
                        + "new UiSelector().description(\"test-Price\"));");
    }
    @AfterTest
    public void afterTest(){
getDriver().quit();
    }
@Test
    public void baseTest(){

        System.out.println("TestNaG");
    }
}