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
        connectPage.checkAllConnections();
   }
}




