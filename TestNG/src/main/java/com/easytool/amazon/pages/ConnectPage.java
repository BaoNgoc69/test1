
package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.time.Duration;

public class ConnectPage {
    WebDriver driver;
    WebDriverWait wait;
    By iframeLocator = By.xpath("//iframe[@title='ToolE Amazon Easy Sync']" );
    By connectTabLocator = By.xpath("//ul[@class='tabs-container']//li[3]" );
    By configLinkLocator = By.xpath("//a[normalize-space()='How to configure your Connect?']" );
    By checkConnectButton = By.xpath("//button[.//span[text()='Check']]");
    By successMessage = By.xpath("//span[contains(@class, 'Polaris-Text--bodyMd Polaris-Text--success') and text()='Successfully connected to Amazon']" );

    public ConnectPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public boolean openConnectTab() {
        try {
            WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
            driver.switchTo().frame(iframeElement);

            WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(connectTabLocator));
            connectButton.click();
            System.out.println("✅ Đã click vào tab 'Connect'" );

            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(configLinkLocator));
            System.out.println("✅ Link 'How to configure your Connect?' đã xuất hiện!" );
            Thread.sleep(200);
            return configLink.isDisplayed();
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi mở tab Connect: " + e.getMessage());
            return false;
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    public void switchToIframe(By iframeLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement iframeElement = wait.until(ExpectedConditions.presenceOfElementLocated(iframeLocator));
        driver.switchTo().frame(iframeElement);
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    public void checkAllConnections() {
        try {
            // 1️⃣ Chuyển vào iframe (nếu có)
            switchToIframe(By.xpath("//iframe"));

            // 2️⃣ Lấy danh sách tất cả các nút "Check"
            List<WebElement> checkButtons = driver.findElements(By.xpath("//button[span[text()='Check']]"));

            // 3️⃣ Lặp qua từng nút và click
            for (WebElement checkButton : checkButtons) {
                checkButton.click();
                System.out.println("✅ Đã click vào nút Check");

                // 4️⃣ Chờ thông báo kết nối thành công
                WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@class, 'Polaris-Text--success')]")
                ));
                System.out.println("✅ Kết nối thành công: " + successMessage.getText());

                // 5️⃣ Chờ một chút trước khi tiếp tục (tránh lỗi)
                Thread.sleep(2000);
            }

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra kết nối: " + e.getMessage());
        } finally {
            // 6️⃣ Quay lại trang chính (nếu có dùng iframe)
            switchToDefaultContent();
        }
    }

}
