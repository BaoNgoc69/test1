package com.easytool.amazon.tests;

import com.aventstack.extentreports.ExtentTest;
import com.easytool.amazon.pages.BaseTestHelper;
import com.easytool.amazon.pages.PlanPage;
import com.easytool.amazon.utils.ExtentTestManager;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PlanTest extends BaseTest {
    PlanPage planPage;

    @BeforeClass
    public void initPages() {
        BaseTestHelper baseTest = new BaseTestHelper(driver);
        planPage = new PlanPage(driver, baseTest);
    }

    @Test(description = "Open the Plan tab.", dependsOnMethods = {"testLogin"})
    public void testOpenPlanTab() {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("🔍 Opening Plan tab...");

        boolean result = planPage.openPlanTab();
        if (result) {
            test.pass("✅ Plan tab opened successfully.");
        } else {
            test.fail("❌ Không mở được tab Plan!");
        }
    }

    @Test(description = "Detect and print current plan", dependsOnMethods = {"testOpenPlanTab"})
    public void printCurrentPlan() {
        ExtentTest test = ExtentTestManager.getTest();
        test.info("🔍 Detecting current plan...");

        String planName = String.valueOf(planPage.detectCurrentPlan());
        System.out.println("🔍 Plan hiện tại là: " + planName);
        test.pass("✅ Plan hiện tại là: " + planName);
    }
}
