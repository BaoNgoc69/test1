package com.easytool.amazon.tests;

import com.aventstack.extentreports.ExtentTest;
import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.InventoryPage;
import com.easytool.amazon.utils.ExtentTestManager;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {
    InventoryPage inventoryPage;

    @BeforeClass
    public void initPages() {
        BaseTestHelper baseTest = new BaseTestHelper(driver);
        inventoryPage = new InventoryPage(driver, baseTest);
    }

    @Test(description = "Open the Inventory tab.", dependsOnMethods = {"testLogin"})
    public void testOpenInventoryTab() throws InterruptedException {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("🔍 Opening Inventory tab...");

        boolean result = inventoryPage.openInventoryTab();
        Assert.assertTrue(result, "❌ Không mở được tab Inventory!");

        test.pass("✅ Inventory tab opened successfully.");
    }

    @Test(description = "Click 'Send All to Amazon' button.")
    public void clickSendAllToAmazon() {
        if (!inventoryPage.isProductAvailable()) {
            throw new SkipException("❌ Không có sản phẩm để gửi Amazon. Bỏ qua test.");
        }

        ExtentTest test = ExtentTestManager.getTest();
        test.info("📦 Clicking 'Send All to Amazon' button...");
        inventoryPage.clickSendAllToAmazon();
        test.pass("✅ Sent all to Amazon successfully.");
    }


    @Test(description = "Verify UI when no products are found in the Inventory tab. Should display 'Products not found' and hide action buttons.")
    public void verifyUIOnNoProductsFound() throws InterruptedException {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("🧪 Kiểm tra khi không có sản phẩm hiển thị...");

        inventoryPage.verifyUIOnNoProductsFound();

        test.pass("✅ Giao diện đúng: Hiển thị 'Products not found' và không hiển thị các nút hành động.");
    }


//    @Test(description = "Click 'Disable All Products' button.", dependsOnMethods = {"testOpenInventoryTab"})
//    public void clickDisableAllProducts() throws InterruptedException {
//        ExtentTest test = ExtentTestManager.getTest();
//        test.info("🛑 Clicking 'Disable All Products' button...");
//        inventoryPage.clickDisableAllProducts();
//        test.pass("✅ All products disabled successfully.");
//    }
}
