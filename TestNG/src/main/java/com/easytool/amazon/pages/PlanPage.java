package com.easytool.amazon.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static java.lang.Thread.sleep;

public class PlanPage extends BasePage {

    By planTabLocator = By.xpath("//ul[@class='tabs-container']//span[@class='Polaris-Text--root Polaris-Text--bodySm Polaris-Text--medium'][normalize-space()='Plans']");
    By planHeaderTitle = By.xpath("//h2[@class='header-title' and (normalize-space(text())='Free' or normalize-space(text())='Starter' or normalize-space(text())='Pro' or normalize-space(text())='Enterprise')]");

    public PlanPage(WebDriver driver, BaseTestHelper baseTestHelper) {
        super(driver, baseTestHelper);
    }

    public boolean openPlanTab() {
        try {
            switchToIframe();
            WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(planTabLocator));
            connectButton.click();
            System.out.println("✅ Đã click vào tab 'Plan'");

            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(planHeaderTitle));
            System.out.println("✅ Các Plan đã hiển thị!");
            sleep(200);
            return configLink.isDisplayed();
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi mở tab Plan: " + e.getMessage());
            return false;
        } finally {
            switchToDefaultContent();
        }
    }

    public String detectCurrentPlan() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            switchToIframe();

            WebElement disabledButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[@class='plan-button']//button[contains(@class, 'Polaris-Button--disabled')]")
            ));

            WebElement planNameElement = disabledButton.findElement(By.xpath(
                    "./ancestor::div[@class='plan-content']//h2[@class='header-title']"
            ));

            String planName = planNameElement.getText();
            System.out.println("✅ Plan hiện tại đang sử dụng: " + planName);

            return planName;
        } catch (Exception e) {
            System.out.println("⚠ Lỗi khi detect current plan: " + e.getMessage());
            return "Unknown";
        } finally {
            switchToDefaultContent();
        }
    }
}
