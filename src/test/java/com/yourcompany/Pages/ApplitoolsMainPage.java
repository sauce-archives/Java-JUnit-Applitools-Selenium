package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ApplitoolsMainPage {

    @FindBy(css = ".features>a")
    private WebElement featuresLink;

    public WebDriver driver;
    public static String url = "https://www.applitools.com";

    public static ApplitoolsMainPage visitPage(WebDriver driver) {
        ApplitoolsMainPage page = new ApplitoolsMainPage(driver);
        page.visitPage();
        return page;
    }

    public ApplitoolsMainPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void visitPage() {
        this.driver.get(url);
    }

    public ApplitoolsFeaturesPage goToFeaturesPage() {
    	featuresLink.click();
    	return new ApplitoolsFeaturesPage(this.driver);
    }

}
