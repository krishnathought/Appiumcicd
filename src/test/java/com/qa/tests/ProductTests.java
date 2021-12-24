package com.qa.tests;


import com.qa.BaseTest;
import com.qa.pages.LoginPage;
import com.qa.pages.ProductsDetailsPage;
import com.qa.pages.ProductsPage;
import com.qa.pages.SettingsPage;
import com.qa.utils.TestUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;


public class ProductTests extends BaseTest{
    TestUtils utils = new TestUtils();
    LoginPage loginPage;
    ProductsPage productsPage;
    ProductsDetailsPage productsDetailsPage;
    SettingsPage settingsPage;
    JSONObject loginUsers;

    @BeforeClass
    public void beforeClass() throws IOException {
        // move this to local for threadsafe parellel
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

    }
        //closeApp();
        launchApp();

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
    }
    @AfterMethod
    public void afterMethod(){

    }
@Test
public void validateProductOnProductsPage(){
    SoftAssert sa =new SoftAssert();
        productsPage = loginPage.Login(loginUsers.getJSONObject("validUser").getString("userName"),loginUsers.getJSONObject("validUser").getString("password"));

       String SLBTitle= productsPage.getSLBTitle();
      String SLBPrice= productsPage.getSLBPrice();

      sa.assertEquals(SLBTitle,getStrings().get("products_page_slb_title"));
      sa.assertEquals(SLBPrice,getStrings().get("products_page_slb_price"));

      // logout
    settingsPage=productsPage.pressSettingsBtn();
    loginPage= settingsPage.pressloguOutBtn();
        sa.assertAll();



        }
    @Test(dependsOnMethods = {"validateProductOnProductsPage"})
    public void validateProductOnProductDetailsPage() throws InterruptedException {
        SoftAssert sa =new SoftAssert();
        productsPage = loginPage.Login(loginUsers.getJSONObject("validUser").getString("userName"),loginUsers.getJSONObject("validUser").getString("password"));

        productsDetailsPage =productsPage.pressSLBTitle();
        String SLBTitle= productsDetailsPage.getSLBTitle();
        String SLBTxt= productsDetailsPage.getSLBTxt();

        sa.assertEquals(SLBTitle,getStrings().get("products_details_page_slb_title"));
        sa.assertEquals(SLBTxt,getStrings().get("products_details_page_slb_Txt"));

        productsDetailsPage.scrollToSLBPrice();
        String SLBPrice= productsDetailsPage.getSLBPrice();
        sa.assertEquals(SLBPrice,getStrings().get("products_details_page_slb_price"));


        productsPage =productsDetailsPage.pressBackToProducts();
        Thread.sleep(5000);
        //wait(5000);
        // logout
       settingsPage=productsPage.pressSettingsBtn();
        loginPage= settingsPage.pressloguOutBtn();

        sa.assertAll();



    }



}

