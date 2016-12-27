package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class ApplitoolsFeaturesPage {

    public WebDriver driver;
    public static String url = "https://applitools.com/features/";

    public static ApplitoolsFeaturesPage visitPage(WebDriver driver) {
    	ApplitoolsFeaturesPage page = new ApplitoolsFeaturesPage(driver);
        page.visitPage();
        return page;
    }
    
    public ApplitoolsFeaturesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void visitPage() {
        this.driver.get(url);
    }

}
