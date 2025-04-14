package com.easytool.amazon.tests;

import com.aventstack.extentreports.ExtentTest;
import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.ConnectPage;
import com.easytool.amazon.utils.ExtentTestManager;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConnectTest extends BaseTest {
    ConnectPage connectPage;
    boolean hasConnection = false;

    @BeforeClass
    public void initPages() {
        BaseTestHelper baseTest = new BaseTestHelper(driver);
        connectPage = new ConnectPage(driver, baseTest);
    }

    @Test(description = "Open the Connect tab.", dependsOnMethods = {"testLogin"})
    public void testOpenConnectTab() {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("Open the Connect tab.");
        connectPage.openConnectTab();
    }

    @Test(description = "Check existing connections.", dependsOnMethods = {"testOpenConnectTab"})
    public void testCheckConnection() throws InterruptedException {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("Check if a connection exists. Skip test if none found.");

        if (connectPage.verifyNoConnectionUI()) {
            test.skip("⚠️ No connection found. Skipping test.");
            hasConnection = false;
            throw new SkipException("No connection found.");
        } else {
            hasConnection = true;
            connectPage.checkAllConnections();
            test.pass("Connections verified successfully.");
        }
    }

    @Test(description = "Save selected marketplaces.", dependsOnMethods = {"testOpenConnectTab"})
    public void testSaveMarketplaces() throws InterruptedException {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("Attempting to save selected marketplaces.");
        Assert.assertTrue(connectPage.saveMarketplaces());
        test.pass("Marketplaces saved successfully.");
    }

    @Test(description = "Verify UI when no connection exists.", dependsOnMethods = {"testOpenConnectTab"})
    public void testVerifyNoConnection() throws InterruptedException {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("Verify the UI when no connection exists.");

        if (hasConnection) {
            test.skip("✅ Connection exists. Skipping UI check.");
            throw new SkipException("Connection exists.");
        } else {
            boolean result = connectPage.verifyNoConnectionUI();
            Assert.assertTrue(result, "❌ UI not shown correctly!");
            test.pass("No-connection UI displayed as expected.");
        }
    }

    @Test(description = "Add and authorize new connection.", dependsOnMethods = {"testCheckConnection"})
    public void testAddAndAuthorizeConnection() throws InterruptedException {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("Add and authorize a new connection if none exist.");

        if (!hasConnection) {
            test.info("🔧 Adding new connection...");
            connectPage.addConnect();
            connectPage.checkAllConnections();
            test.pass("New connection added and verified.");
        } else {
            test.skip("✅ Connection already exists. No action needed.");
        }
    }
}
