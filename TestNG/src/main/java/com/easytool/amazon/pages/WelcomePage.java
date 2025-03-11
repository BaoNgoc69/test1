package com.easytool.amazon.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WelcomePage {
    WebDriver driver;
    WebDriverWait wait;
    By iframeLocator = By.xpath("//iframe[@title='ToolE Amazon Easy Sync']");
    By successMessageLocator = By.xpath("//p[contains(text(),'Connect to Amazon easily in 3 simple clicks')]");

    public WelcomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public boolean isLoginSuccessful() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
            System.out.println("🔥 Nội dung hiển thị sau khi login: " + successMessage.getText());

            return successMessage.isDisplayed();
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra login: " + e.getMessage());
            return false;
        } finally {
            driver.switchTo().defaultContent(); // Luôn quay lại main page
        }
    }
}
