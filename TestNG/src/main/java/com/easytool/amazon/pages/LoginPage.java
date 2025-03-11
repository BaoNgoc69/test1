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

    @FindBy(id = "account_email")
    WebElement inputEmail;

    @FindBy(xpath = "//button[@type='submit']")
    WebElement clickBtnContinue;

    @FindBy(id = "account_password")
    WebElement inputPassword;

    @FindBy(name = "commit")
    WebElement clickBtnLogin;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys(email, Keys.ENTER);
        wait.until(ExpectedConditions.elementToBeClickable(clickBtnContinue)).click();
        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(clickBtnLogin)).click();
    }
}
