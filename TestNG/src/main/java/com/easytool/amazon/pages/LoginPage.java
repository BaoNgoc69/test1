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

    public void login(String email, String password) {
        waitAndType(inputEmail, email + Keys.ENTER);
        waitAndClick(clickBtnContinue);
        waitAndType(inputPassword, password);
        waitAndClick(clickBtnLogin);
    }

    // Helper: chờ phần tử hiển thị rồi nhập text
    private void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element)).clear();
        element.sendKeys(text);
    }

    // Helper: chờ phần tử có thể click rồi click
    private void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
}
