package com.qa.pages;

import com.qa.MenuPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsDetailsPage extends MenuPage {
    
TestUtils utils = new TestUtils();

@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Description']/android.widget.TextView[1]") private MobileElement SLBTitle;
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Description']/android.widget.TextView[2]") private MobileElement SLBTxt;
    @AndroidFindBy(accessibility ="test-Price") private MobileElement SLBPrice;
@AndroidFindBy(accessibility = "test-BACK TO PRODUCTS") private MobileElement backToProducts;

// return type string not the class object
    public String getSLBTitle()
    {
        String Title= getAttribute(SLBTitle, "text");
      //  System.out.println("Title is"+ Title );
        utils.log("Title is"+ Title );

        return Title;
    }

    public String getSLBTxt()
    {
        String SLBTextValue =getAttribute(SLBTxt, "text");
      //  System.out.println("Text value SLB"+SLBTextValue);
        utils.log("Text value SLB"+SLBTextValue);

        return SLBTextValue;
    }
    public String getSLBPrice()
    {
        String price =getAttribute(SLBPrice, "text");
       // System.out.println("price value SLB"+price);
        utils.log("price value SLB"+price);

        return price;
    }

    public ProductsDetailsPage scrollToSLBPrice(){
        scrollToElement();
        return this;
    }
    public ProductsPage pressBackToProducts(){
       // System.out.println("Navigate back to Products page");
        utils.log("Navigate back to Products page");

        click(backToProducts);
    return new ProductsPage();
    }
}