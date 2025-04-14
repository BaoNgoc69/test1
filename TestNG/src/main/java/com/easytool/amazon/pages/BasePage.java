package com.easytool.amazon.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected BaseTestHelper baseTestHelper;

    // Iframe dùng chung
    protected By iframeLocator = By.xpath("//iframe[@title='[DEV] Amazon Easy Tool']");

    public BasePage(WebDriver driver, BaseTestHelper baseTestHelper) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.baseTestHelper = baseTestHelper;
    }

    public boolean switchToIframe() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);
            System.out.println("✅ Đã chuyển vào iframe!");
            return true;
        } catch (NoSuchFrameException | TimeoutException e) {
            System.out.println("⚠ Không tìm thấy iframe: " + e.getMessage());
            return false;
        }
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }
}

