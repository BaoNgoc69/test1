package com.easytool.amazon.tests;

import com.easytool.amazon.pages.BaseTestPage;
import com.easytool.amazon.pages.ConnectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.WebElement;
import java.util.List;

import java.time.Duration;

public class ConnectTest extends BaseTest {
    ConnectPage connectPage;

    @BeforeClass
    public void initPages() {
        BaseTestPage baseTest = new BaseTestPage(driver);
        connectPage = new ConnectPage(driver, baseTest);
    }



    @Test(dependsOnMethods = {"testLogin"})
    public void testOpenConnectTab() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // Chờ iframe xuất hiện ít nhất một cái
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("iframe")));
            System.out.println("✅ Ít nhất một iframe đã xuất hiện.");

            // Debug: In ra số lượng iframe trên trang
            List<WebElement> iframes = driver.findElements(By.tagName("iframe"));
            System.out.println("🔍 Tổng số iframes trên trang: " + iframes.size());

            if (iframes.isEmpty()) {
                System.out.println("❌ Không tìm thấy iframe nào!");
                return;  // Dừng test case nếu không có iframe
            }

            for (WebElement iframe : iframes) {
                String title = iframe.getAttribute("title");
                System.out.println("📌 Iframe title: " + (title != null ? title : "Không có title"));

                // Kiểm tra iframe có hiển thị không
                if (iframe.isDisplayed()) {
                    System.out.println("✅ Iframe này đang hiển thị.");
                } else {
                    System.out.println("⚠️ Iframe này có thể bị ẩn.");
                }
            }

            // Tìm đúng iframe theo title (nếu có)
            WebElement targetIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//iframe[contains(@title, '[DEV] Amazon Easy Tool')]")
            ));

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", targetIframe);

            // Chuyển vào iframe nếu nó hiển thị
            if (targetIframe.isDisplayed()) {
                driver.switchTo().frame(targetIframe);
                System.out.println("✅ Đã chuyển vào iframe.");
            } else {
                System.out.println("❌ Iframe bị ẩn, không thể switch.");
                return;
            }

            // Chờ tab Connect hiển thị
            WebElement connectTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//ul[@class='tabs-container']//span[normalize-space()='Connect']")
            ));
            wait.until(ExpectedConditions.elementToBeClickable(connectTab)).click();
            System.out.println("✅ Đã mở tab Connect.");

            // Gọi hàm checkAllConnections()
            System.out.println("🔍 Đang chạy checkAllConnections()");
            connectPage.checkAllConnections();
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi mở tab Connect: " + e.getMessage());
            throw e;
        }
    }


}
