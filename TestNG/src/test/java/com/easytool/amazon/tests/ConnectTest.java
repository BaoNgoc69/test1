package com.easytool.amazon.tests;

import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.ConnectPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.time.Duration;

public class ConnectTest extends BaseTest {
    ConnectPage connectPage;
    boolean isChecked = false;

    @BeforeClass
    public void initPages() {
        BaseTestHelper baseTest = new BaseTestHelper(driver);
        connectPage = new ConnectPage(driver, baseTest);
    }

    @Test(dependsOnMethods = {"testLogin"})
    public void testOpenConnectTab() {
        connectPage.openConnectTab();

    }

    @Test(dependsOnMethods = {"testOpenConnectTab"})
    public void testCheckConnection() throws InterruptedException {
        connectPage.checkAllConnections();
    }


    @Test(dependsOnMethods = {"testCheckConnection"})
    public void testAddAndAuthorizeConnection() throws InterruptedException {
        if (isChecked) {
            System.out.println("⚠ Đã có connection, bỏ qua test này.");
            return;
        }
        System.out.println("🔄 Chưa có connection, tiến hành thêm mới.");

        // Chờ connection được cập nhật
       // connectPage.authorizeConnection();
        int retry = 0;
        boolean isConnected = false;
        while (retry < 5) { // Kiểm tra tối đa 5 lần
            Thread.sleep(3000); // Chờ 3 giây trước mỗi lần kiểm tra
            isConnected = connectPage.checkAllConnections();
            if (isConnected) {
                System.out.println("✅ Connection đã được thêm!");
                break;
            }
            retry++;
            System.out.println("⏳ Chưa thấy connection, thử lại lần " + retry);
        }

        if (!isConnected) {
            throw new RuntimeException("❌ Connection không được thêm sau khi authorize!");
        }
    }
}




