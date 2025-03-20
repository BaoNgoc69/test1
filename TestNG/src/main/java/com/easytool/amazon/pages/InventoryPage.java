package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.Thread.sleep;

public class InventoryPage {
    WebDriver driver;
    WebDriverWait wait;
    BaseTestHelper baseTestHelper;

    By iframeLocator = By.xpath("//iframe[@title='[DEV] Amazon Easy Tool']");
    By inventoryTabLocator = By.xpath("//ul[@class='tabs-container']//span[@class='Polaris-Text--root Polaris-Text--bodySm Polaris-Text--medium'][normalize-space()='Inventory11']");
    By configLinkLocator = By.xpath("//a[normalize-space()='How to use Inventory tab?']");


    public InventoryPage(WebDriver driver, BaseTestHelper baseTestHelper) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.baseTestHelper = baseTestHelper; // ✅ Nhận từ test, không tự tạo mới
        PageFactory.initElements(driver, this);
    }

    public boolean openInventoryTab() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);

            WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(inventoryTabLocator));
            connectButton.click();
            System.out.println("✅ Đã click vào tab 'Inventory'");

            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(configLinkLocator));
            System.out.println("✅ Link 'How to use Inventory tab?' đã xuất hiện!");
            baseTestHelper.takeScreenshot(driver,"Đã click vào tab Inventory");
            sleep(200);
            return configLink.isDisplayed();

        } catch (Exception e) {
            System.out.println("⚠️ How to use Inventory tab?: " + e.getMessage());
            return false;
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    // swith to iframe
    public boolean switchToIframe(By iframeLocator) {
        try {
            WebElement iframeElement = new WebDriverWait(driver, Duration.ofSeconds(1))
                    .until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
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
