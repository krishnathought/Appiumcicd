package com.qa.pages;
import com.qa.BaseTest;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class LoginPage extends BaseTest

{
    TestUtils utils =new TestUtils();
    @AndroidFindBy(accessibility = "test-Username") private MobileElement userNameTxtField;
    @AndroidFindBy(accessibility = "test-Password") private MobileElement passwordTxtField;
    @AndroidFindBy(accessibility = "test-LOGIN") private MobileElement loginButton;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Error message']/android.widget.TextView") private MobileElement errText;
 


    // we are entring the value and staying in the  same page; so return type is same page(Loginpage)
public LoginPage enterUserName(String userName)
{
    //System.out.println("Login with "+userName);
utils.log("Login with "+userName);
    sendKeys(userNameTxtField, userName);
    return this;
}
public LoginPage enterPassword(String password)
{
    utils.log("Password is "+password);

  // System.out.println("Password is "+password);
    sendKeys(passwordTxtField, password);
    return this;
}
public ProductsPage pressLoginButton()
{
   utils.log("Press login Button");

    //System.out.println("Press login Button");
click(loginButton);
    return new ProductsPage();
}


public ProductsPage Login(String userName,String password){

    enterUserName(userName);
    enterPassword(password);
    return pressLoginButton();
}
public String getErrortext()
{
   utils.log("err Text method "+getAttribute(errText, "text"));

   // System.out.println("err Text method "+getAttribute(errText, "text"));
    return getAttribute(errText, "text");

}
}