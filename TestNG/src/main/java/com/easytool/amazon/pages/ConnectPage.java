package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class ConnectPage extends BasePage {

    String originalURL;

    WebDriverWait wait;

    By connectTabLocator = By.xpath("//ul[@class='tabs-container']//span[@class='Polaris-Text--root Polaris-Text--bodySm Polaris-Text--medium'][normalize-space()='Connect']");
    By configLinkLocator = By.xpath("//a[normalize-space()='How to configure your Connect?']");
    By checkConnectButton = By.xpath("//button[.//span[text()='Check']]");
    By successMessage = By.xpath("//span[contains(@class, 'Polaris-Text--bodyMd Polaris-Text--success') and text()='Successfully connected to Amazon']");
    By addConnect = By.xpath("//span[normalize-space()='Get Started']");
    By textAddConnect = By.xpath("//p[contains(text(),'Grow your sales by expanding your business worldwi')]");
    By inputNameMkp = By.xpath("//div[@class='Polaris-TextField']//input");
    By selectRegion = By.xpath("//select[@id=':r6:']");
    By selectMKP = By.xpath("//select[@id=':rc:']");
    By selectOrder = By.xpath("//select[@id=':r8:']");
    By btnSaveAuthorize = By.xpath("//button[span[text()='Save & Authorize']]");
    By checkboxConfirmAuthorize = By.xpath("//div[@role='checkbox']");
    By btnConfirmAuthorize = By.xpath("//button[@class='primary' and text()='Confirm']");
    By inputEmail = By.xpath("//input[@type='email' and @id='ap_email' and @name='email']");
    By inputPassword = By.xpath("//input[@id='ap_password']");
    By btnGetStarted = By.xpath("//span[normalize-space()='Get Started']");
    By emptyConnectMessage = By.xpath("//div[@class='Polaris-CalloutCard__Title']/h2[contains(text(), \"You don't have any Amazon account connected yet\")]");

    public ConnectPage(WebDriver driver, BaseTestHelper baseTestHelper) {
        super(driver, baseTestHelper);
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean openConnectTab() {
        try {
            switchToIframe();
            WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(connectTabLocator));
            connectButton.click();

            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(configLinkLocator));
            return configLink.isDisplayed();
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi mở tab Connect: " + e.getMessage());
            return false;
        } finally {
            switchToDefaultContent();
        }
    }

    public void addConnect() throws InterruptedException {
        switchToIframe();

        wait.until(ExpectedConditions.elementToBeClickable(addConnect)).click();
        WebElement inputName = wait.until(ExpectedConditions.visibilityOfElementLocated(inputNameMkp));
        inputName.sendKeys("Amazon USA");

        baseTestHelper.scrollDown(200);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(selectRegion))).selectByIndex(1);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(selectMKP))).selectByIndex(1);
        new Select(wait.until(ExpectedConditions.elementToBeClickable(selectOrder))).selectByIndex(1);

        baseTestHelper.takeScreenshot(driver, "add mkp");
        WebElement clickSave = wait.until(ExpectedConditions.elementToBeClickable(btnSaveAuthorize));
        this.originalURL = driver.getCurrentUrl();
        baseTestHelper.scrollDown(200);
        clickSave.click();

        authorizeConnection();
    }

    public boolean verifyNoConnectionUI() {
        try {
            switchToIframe();

            Assert.assertTrue(wait.until(ExpectedConditions.visibilityOfElementLocated(emptyConnectMessage)).isDisplayed());
            Assert.assertTrue(wait.until(ExpectedConditions.elementToBeClickable(btnGetStarted)).isDisplayed());
            Assert.assertTrue(driver.findElement(btnGetStarted).isEnabled());

            WebElement helpLink = driver.findElement(By.xpath("//a[contains(text(), 'How to configure your Connect?')]"));
            Assert.assertTrue(helpLink.isDisplayed());
            Assert.assertEquals(helpLink.getAttribute("href"), "EXPECTED_URL_HERE");

            return true;
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra UI không có kết nối: " + e.getMessage());
            return false;
        } finally {
            switchToDefaultContent();
        }
    }

    public boolean checkAllConnections() {
        boolean ischeck = false;
        try {
            switchToIframe();
            List<WebElement> checkButtons = driver.findElements(By.xpath("//button[span[text()='Check']]"));
            for (WebElement checkButton : checkButtons) {
                ischeck = true;
                checkButton.click();
                baseTestHelper.scrollDown(200);

                wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage));
            }

            if (!ischeck) {
                addConnect();
                baseTestHelper.takeScreenshot(driver, "Add connect");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra kết nối: " + e.getMessage());
        } finally {
            switchToDefaultContent();
        }
        return ischeck;
    }

    public void authorizeConnection() {
        wait.until(ExpectedConditions.urlContains(originalURL));

        driver.switchTo().defaultContent();

        WebElement inputName = wait.until(ExpectedConditions.visibilityOfElementLocated(inputEmail));
        inputName.sendKeys("nhu@inter-soft.com" + Keys.ENTER);

        WebElement inputPass = wait.until(ExpectedConditions.visibilityOfElementLocated(inputPassword));
        inputPass.sendKeys("Fed@#SoftToole" + Keys.ENTER);

        wait.until(ExpectedConditions.elementToBeClickable(By.id("auth-signin-button")));
        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(this.originalURL)));

        WebElement checkButtonConfirm = wait.until(ExpectedConditions.elementToBeClickable(checkboxConfirmAuthorize));
        checkButtonConfirm.click();

        WebElement clickConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(btnConfirmAuthorize));
        clickConfirmButton.click();
    }

    public int checkMarketPlace() throws InterruptedException {
        List<WebElement> marketplaceCheckboxs = driver.findElements(By.xpath("//input[@type='checkbox']"));
        marketplaceCheckboxs = marketplaceCheckboxs.subList(1, marketplaceCheckboxs.size());

        int totalCheck = marketplaceCheckboxs.size();
        boolean isChange = false;

        for (WebElement checkbox : marketplaceCheckboxs) {
            if (!checkbox.isSelected()) {
                wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
                isChange = true;
            }
        }

        if (!isChange) {
            wait.until(ExpectedConditions.elementToBeClickable(marketplaceCheckboxs.getFirst())).click();
            totalCheck--;
        }

        WebElement saveButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[span[text()='Save']]")));
        baseTestHelper.scrollDown(100);
        saveButton.click();

        wait.withTimeout(Duration.ofSeconds(15))
                .until(ExpectedConditions.invisibilityOf(saveButton));

        return totalCheck;
    }

    public boolean saveMarketplaces() {
        try {
            switchToIframe();

            List<WebElement> marketplaceButtons = driver.findElements(By.xpath("//button[span[text()='Marketplaces']]"));

            if (marketplaceButtons.isEmpty()) {
                return false;
            }

            int totalChecked = 0;

            for (int i = 0; i < marketplaceButtons.size(); i++) {
                List<WebElement> currentButtons = driver.findElements(By.xpath("//button[span[text()='Marketplaces']]"));
                if (i >= currentButtons.size()) break;

                WebElement btn = currentButtons.get(i);
                if (btn.isDisplayed()) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
                    wait.until(ExpectedConditions.elementToBeClickable(btn)).click();

                    baseTestHelper.scrollDown(150);
                    int count = checkMarketPlace();
                    totalChecked += count;
                }
            }

            By inventoryTabLocator = By.xpath("//ul[@class='tabs-container']//span[normalize-space()='Inventory']");
            WebElement inventoryButton = wait.until(ExpectedConditions.elementToBeClickable(inventoryTabLocator));
            inventoryButton.click();

            wait.withTimeout(Duration.ofSeconds(5)).until(
                    ExpectedConditions.presenceOfElementLocated(By.xpath("//optgroup[@label='BNG']/option"))
            );

            List<WebElement> bngOptions = driver.findElements(By.xpath("//optgroup[@label='BNG']/option"));
            List<WebElement> usaOptions = driver.findElements(By.xpath("//optgroup[@label='Amazon USA']/option"));

            int totalExpected = bngOptions.size() + usaOptions.size();

            return totalChecked == totalExpected;

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi lưu Marketplaces: " + e.getMessage());
            return false;
        } finally {
            switchToDefaultContent();
        }
    }
}
