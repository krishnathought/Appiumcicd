package com.qa.pages;

import com.qa.BaseTest;

import com.qa.MenuPage;
import com.qa.utils.TestUtils;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductsPage extends MenuPage {
    ;
    TestUtils utils =new TestUtils();
    @AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc='test-Cart drop zone']/android.view.ViewGroup/android.widget.TextView") private MobileElement productTitleTxt;


@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='test-Item title'])[1]") private MobileElement SLBTitle;
    @AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc='test-Price'])[1]") private MobileElement SLBPrice;

// return type string not the class object
public String getTitle()
{
    String ProductTitle =getAttribute(productTitleTxt, "text");
   // System.out.println("Producuts Page  Title "+ProductTitle);
    utils.log("Producuts Page  Title "+ProductTitle);

    return ProductTitle;
}
    public String getSLBTitle()
    {
        String title =getAttribute(SLBTitle, "text");
     //   System.out.println("Title is on products page  "+title);
        utils.log("Title is on products page  "+title);

        return title;
    }

    public String getSLBPrice()
    {
        String Price= getAttribute(SLBPrice, "text");
        //System.out.println("Prics is on product page "+Price);
        utils.log("Prics is on product page "+Price);

        return  Price;

    }
    public ProductsDetailsPage pressSLBTitle(){
      //  System.out.println("Press SLB Title link -");

        utils.log("Press SLB Title link -");
    click(SLBTitle);
    return new ProductsDetailsPage();
    }
}