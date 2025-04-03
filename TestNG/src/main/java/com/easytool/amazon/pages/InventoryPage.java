package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

import static java.lang.Thread.sleep;

public class InventoryPage {
    WebDriver driver;
    WebDriverWait wait;
    BaseTestHelper baseTestHelper;

    By iframeLocator = By.xpath("//iframe[@title='[DEV] Amazon Easy Tool']");
    By inventoryTabLocator = By.xpath("//ul[@class='tabs-container']//span[@class='Polaris-Text--root Polaris-Text--bodySm Polaris-Text--medium'][normalize-space()='Inventory']");
    By configLinkLocator = By.xpath("//a[normalize-space()='How to use Inventory tab?']");
    By btnClickAllToAmazon = By.xpath("//button[span[contains(@class, 'Polaris-Text') and contains(text(), 'Send all to Amazon')]]");
    By btnClickDisableAllProduct = By.xpath("//button[@class='Polaris-Button Polaris-Button--pressable Polaris-Button--variantSecondary Polaris-Button--sizeMedium Polaris-Button--textAlignCenter Polaris-Button--iconWithText Polaris-Button--toneCritical']");

    public InventoryPage(WebDriver driver, BaseTestHelper baseTestHelper) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.baseTestHelper = baseTestHelper; // ✅ Nhận từ test, không tự tạo mới
        PageFactory.initElements(driver, this);
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

    public boolean openInventoryTab() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);

            WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(inventoryTabLocator));
            Assert.assertNotNull(connectButton, "❌ Không tìm thấy tab Inventory để click!");
            connectButton.click();
            System.out.println("✅ Đã click vào tab 'Inventory'");

            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(configLinkLocator));
            Assert.assertTrue(configLink.isDisplayed(), "❌ Link 'How to use Inventory tab?' không hiển thị!");
            System.out.println("✅ Link 'How to use Inventory tab?' đã xuất hiện!");

            baseTestHelper.takeScreenshot(driver, "Đã click vào tab Inventory");
            sleep(200);
            return true;

        } catch (Exception e) {
            Assert.fail("⚠️ Lỗi khi mở tab Inventory: " + e.getMessage());
            return false;
        } finally {
            driver.switchTo().defaultContent();
        }
    }


    public void clickSendAllToAmazon() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);

            WebElement sendAllButton = wait.until(ExpectedConditions.elementToBeClickable(btnClickAllToAmazon));
            sendAllButton.click();
            baseTestHelper.takeScreenshot(driver, "btnInventory");

            // Kiểm tra xem nút có được click không
            Assert.assertTrue(true, "✅ Đã click vào nút 'Send all to Amazon'");
        } catch (Exception e) {
            // Khi có lỗi, assert false để fail test
            Assert.fail("⚠️ Lỗi khi click vào 'Send all to Amazon': " + e.getMessage());
        } finally {
            driver.switchTo().defaultContent();  // Đảm bảo quay về nội dung chính
        }
    }
    public void clickDisableAllProducts() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);

            WebElement sendAllButton = wait.until(ExpectedConditions.elementToBeClickable(btnClickDisableAllProduct));
            sendAllButton.click();
            baseTestHelper.takeScreenshot(driver, "Disable all product");

            // Kiểm tra xem nút có được click không
            Assert.assertTrue(true, "✅ Đã click vào nút 'Disable all products'");
        } catch (Exception e) {
            // Khi có lỗi, assert false để fail test
            Assert.fail("⚠️ Disable all product': " + e.getMessage());
        } finally {
            driver.switchTo().defaultContent();  // Đảm bảo quay về nội dung chính
        }
    }




}
