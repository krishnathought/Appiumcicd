package com.qa.tests;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;

import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsPage;


public class LoginTests extends BaseTest{
    TestUtils utils =new TestUtils();
    LoginPage loginPage;
    ProductsPage productsPage;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws IOException {
        InputStream datais =null;

        try {
            String dataFileName="Data/loginUsers.json";
            datais = getClass().getClassLoader().getResourceAsStream(dataFileName);
            JSONTokener tokener= new JSONTokener(datais);
            loginUsers = new JSONObject(tokener);
        }catch(Exception e) {
            e.printStackTrace();
            throw e; 
    }
        finally{
            if(datais !=null){
                datais.close();
            }
           // closeApp();
            //launchApp();

    }



    }
    @AfterClass
    public void AfterClass()
    {
closeApp();
    }

    @BeforeMethod
    public void beforeMethod(Method m)
    {
        loginPage = new LoginPage();
        System.out.println("\n"+ "***** starting test*****   "+"\t"+ m.getName()+"****"+"\n");
        utils.log("\n"+ "***** starting test*****   "+"\t"+ m.getName()+"****"+"\n");

    }
    @AfterMethod
    public void afterMethod(){

    }
@Test
public void invalidUserName(){

            loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("userName"));
            loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
            loginPage.pressLoginButton();

            String errText=loginPage.getErrortext();
            String expectedErrtext=getStrings().get("err_invalid_user_name_or_password");
            utils.log("Actual errortext--->"+errText);;
            //System.out.println("Actual errortext--->"+errText);
   utils.log("Expected errortext--->"+expectedErrtext);
          //  System.out.println("Expected errortext--->"+expectedErrtext);
            Assert.assertEquals(errText,expectedErrtext);


        }



@Test
public void invalidPassword(){
    loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("userName"));
    loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));
    loginPage.pressLoginButton();

    String errText=loginPage.getErrortext();
    String expectedErrtext=getStrings().get("err_invalid_user_name_or_password");
    utils.log("Actual errortext for invalid password-->"+errText);

    //System.out.println("Actual errortext for invalid password-->"+errText);
    //System.out.println("Expected errortext for invalid password-->"+expectedErrtext);

    utils.log("Expected errortext for invalid password-->"+expectedErrtext);
    Assert.assertEquals(errText,expectedErrtext);
}
@Test
public void successfulLogin(){
    loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("userName"));
    loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
    // returnig to products page. so  return type is products page
    productsPage = loginPage.pressLoginButton();

    String actualProductTitle=productsPage.getTitle();
    String expectedProductsTitle=getStrings().get("product_title");
    //System.out.println("Actual products title---"+actualProductTitle + "\n"+"Expected Products title---"+expectedProductsTitle);
    utils.log("Actual products title---"+actualProductTitle + "\n"+"Expected Products title---"+expectedProductsTitle);

    Assert.assertEquals(actualProductTitle, expectedProductsTitle);


}

}

