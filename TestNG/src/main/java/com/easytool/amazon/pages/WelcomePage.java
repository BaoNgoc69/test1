package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class WelcomePage extends BasePage {

    By successMessageLocator = By.xpath("//p[contains(text(),'Connect to Amazon easily in 3 simple clicks')]");

    public WelcomePage(WebDriver driver, BaseTestHelper baseTestHelper) {
        super(driver, baseTestHelper);
        PageFactory.initElements(driver, this);
    }

    public boolean isLoginSuccessful() {
        try {
            switchToIframe();

            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(successMessageLocator));
            System.out.println("🔥 Nội dung hiển thị sau khi login: " + successMessage.getText());
            baseTestHelper.takeScreenshot(driver, "login_success");
            return successMessage.isDisplayed();

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra login: " + e.getMessage());
            return false;
        } finally {
            switchToDefaultContent(); // Luôn quay lại main page
        }
    }
}
