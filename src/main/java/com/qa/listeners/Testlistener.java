package com.qa.listeners;

import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import org.openqa.selenium.OutputType;
import org.testng.Assert;
import org.testng.ITestListener;
import org.apache.commons.io.FileUtils;
import org.testng.ITestResult;
import org.testng.Reporter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class Testlistener implements ITestListener {
    TestUtils utils = new TestUtils();
    @Override
    public void onTestFailure(ITestResult result) {
        if(result.getThrowable()!=null){
            StringWriter sw=new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            result.getThrowable().printStackTrace(pw);
            utils.log(sw.toString());
            //System.out.println(sw.toString());
            // to fail the test ng
        }

        BaseTest base =new BaseTest();

        File file=base.getDriver().getScreenshotAs(OutputType.FILE);
        // Store the screen shot under specific folder
 Map<String,String> params = new HashMap<String,String>();
      params=   result.getTestContext().getCurrentXmlTest().getAllParameters();
        String imagePath= "Screenshots" + File.separator + params.get("platformName") + "_" +params.get("deviceName")+ "_"
                +  File.separator +base.getDateTime() +File.separator + result.getTestClass().getRealClass().getSimpleName()
                +File.separator +result.getName()+".png";
        try {
           // FileUtils.copyFile(file,new File("Samplescr.png"));
            FileUtils.copyFile(file,new File(imagePath));
            Reporter.log("This is the sample screenshot");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
