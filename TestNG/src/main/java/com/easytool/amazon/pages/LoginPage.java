package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;
    BaseTestHelper baseTestHelper;

    @FindBy(id = "account_email")
    WebElement inputEmail;  

    @FindBy(xpath = "//button[@type='submit']")
    WebElement clickBtnContinue;

    @FindBy(id = "account_password")
    WebElement inputPassword;

    @FindBy(name = "commit")
    WebElement clickBtnLogin;

    public LoginPage(WebDriver driver, BaseTestHelper baseTestHelper) {

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
     this.baseTestHelper = baseTestHelper;
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) throws InterruptedException {
        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email, Keys.ENTER);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(clickBtnContinue)).click();
        Thread.sleep(5000);
        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys(password);
        Thread.sleep(5000);
        wait.until(ExpectedConditions.elementToBeClickable(clickBtnLogin)).click();
    }
}
