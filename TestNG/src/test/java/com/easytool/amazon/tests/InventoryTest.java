package com.easytool.amazon.tests;

import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.InventoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class InventoryTest extends BaseTest {
    InventoryPage inventoryPage;


    @BeforeClass
    public void initPages() {
        BaseTestHelper baseTest = new BaseTestHelper(driver);
        inventoryPage = new InventoryPage(driver, baseTest); // Khởi tạo inventoryPage
    }

    @Test(dependsOnMethods = {"testLogin"})
    public void testOpenInventoryTab() throws InterruptedException {
        inventoryPage.openInventoryTab();
    }




}
