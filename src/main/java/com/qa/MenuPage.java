package com.qa;

import com.qa.pages.SettingsPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MenuPage  extends  BaseTest{
TestUtils utils =new TestUtils();
    @AndroidFindBy(xpath="//android.view.ViewGroup[@content-desc='test-Menu']/android.view.ViewGroup/android.widget.ImageView")
    private MobileElement settingsBtn;

    public SettingsPage pressSettingsBtn(){
        //System.out.println("Press Settings Button");
        utils.log("Press Settings Button");

        click(settingsBtn);
        return new SettingsPage();
    }

}
