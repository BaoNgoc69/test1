package com.easytool.amazon.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.List;
import java.time.Duration;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class ConnectPage extends BasePage {

    String originalURL;

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
    }
    public boolean openConnectTab() {
        try {
            switchToIframe();
            WebElement connectButton = wait.until(ExpectedConditions.elementToBeClickable(connectTabLocator));
            connectButton.click();
            System.out.println("✅ Đã click vào tab 'Connect'");

            WebElement configLink = wait.until(ExpectedConditions.visibilityOfElementLocated(configLinkLocator));
            System.out.println("✅ Link 'How to configure your Connect?' đã xuất hiện!");
            sleep(200);
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
        WebElement clickAddConnect = driver.findElement(addConnect);
        clickAddConnect.click();
        sleep(500);

        WebElement inputName = driver.findElement(inputNameMkp);
        inputName.sendKeys("Amazon USA");
        baseTestHelper.scrollDown(200);

        new Select(driver.findElement(selectRegion)).selectByIndex(1);
        new Select(driver.findElement(selectMKP)).selectByIndex(1);
        new Select(driver.findElement(selectOrder)).selectByIndex(1);

        sleep(200);
        baseTestHelper.takeScreenshot(driver, "add mkp");
        WebElement clickSave = driver.findElement(btnSaveAuthorize);
        this.originalURL = driver.getCurrentUrl();
        System.out.println("URL hiện tại của trang connect" + originalURL);
        baseTestHelper.scrollDown(200);
        sleep(1000);
        clickSave.click();
        System.out.println("Click Save Successfully");
        sleep(5000);
        authorizeConnection();
        sleep(1000);
    }

    public boolean verifyNoConnectionUI() {
        try {
            switchToIframe();
            WebElement noConnectionMessage = driver.findElement(emptyConnectMessage);
            Assert.assertTrue(noConnectionMessage.isDisplayed(), "❌ Không tìm thấy thông báo khi chưa có kết nối!");

            WebElement getStartedButton = driver.findElement(btnGetStarted);
            Assert.assertTrue(getStartedButton.isDisplayed(), "❌ Nút 'Get Started' không hiển thị!");
            Assert.assertTrue(getStartedButton.isEnabled(), "❌ Nút 'Get Started' không thể click!");

            WebElement helpLink = driver.findElement(By.xpath("//a[contains(text(), 'How to configure your Connect?')]"));
            Assert.assertTrue(helpLink.isDisplayed(), "❌ Link 'How to configure your Connect?' không hiển thị!");
            Assert.assertEquals(helpLink.getAttribute("href"), "EXPECTED_URL_HERE", "❌ Link không đúng!");

            System.out.println("✅ Giao diện khi chưa có kết nối hiển thị đúng!");
            return true;
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra giao diện không có kết nối: " + e.getMessage());
            return false;
        } finally {
            switchToDefaultContent();
        }
    }

    public boolean checkAllConnections() throws InterruptedException {
        boolean ischeck = false;
        try {
            switchToIframe();
            List<WebElement> checkButtons = driver.findElements(By.xpath("//button[span[text()='Check']]"));
            System.out.println("🔍 Tổng số button Check tìm thấy: " + checkButtons.size());
            for (WebElement checkButton : checkButtons) {
                ischeck = true;
                checkButton.click();
                System.out.println("✅ Đã click vào nút Check");
                baseTestHelper.scrollDown(200);
                sleep(3000);

                WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//span[contains(@class, 'Polaris-Text--success')]")));
                System.out.println("✅ Kết nối thành công: " + successMessage.getText());
                sleep(1000);
            }
            if (!ischeck) {
                this.addConnect();
                baseTestHelper.takeScreenshot(driver, "Add connect");
            }
        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi kiểm tra kết nối: " + e.getMessage());
        } finally {
            switchToDefaultContent();
        }
        return ischeck;
    }

    public void authorizeConnection() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String url = driver.getCurrentUrl();
        wait.until(ExpectedConditions.urlContains(url));
        System.out.println("URL hiện tại: " + url);
        System.out.println("Page changed, locating email input...");
        driver.switchTo().defaultContent();

        WebElement inputName = driver.findElement(inputEmail);
        inputName.sendKeys("nhu@inter-soft.com" + Keys.ENTER);
        WebElement inputPass = driver.findElement(inputPassword);
        inputPass.sendKeys("Fed@#SoftToole" + Keys.ENTER);
        System.out.println("entered password");

        WebElement signInButton = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(By.id("auth-signin-button")));
        System.out.println("Entered OTP");

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(this.originalURL)));
        System.out.println("✅ Trang đã thay đổi!");
        sleep(25000);

        WebElement checkButtonConfirm = wait.until(ExpectedConditions.elementToBeClickable(checkboxConfirmAuthorize));
        checkButtonConfirm.click();
        System.out.println("✅ Đã nhấn nút Confirm");

        WebElement clickConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(btnConfirmAuthorize));
        clickConfirmButton.click();
        System.out.println("✅ Đã nhấn nút Confirm");

        System.out.println("✅ Đã quay lại trang chính!");
        sleep(10000);
    }

    public int checkMarketPlace() throws InterruptedException {
        List<WebElement> marketplaceCheckboxs = driver.findElements(By.xpath("//input[@type='checkbox']"));
        // Bỏ phần tử đầu tiên vì nó sai
        marketplaceCheckboxs = marketplaceCheckboxs.subList(1, marketplaceCheckboxs.size());
        System.out.println("🔍 Tổng số button Checkboxs tìm thấy: " + marketplaceCheckboxs.size());

        int totalCheck = marketplaceCheckboxs.size();

        boolean isChange = false;
        for (WebElement checkbox : marketplaceCheckboxs) {
            if (!checkbox.isSelected()) {
                Thread.sleep(2000);
                checkbox.click();
                Thread.sleep(2000);
                isChange = true;
            }
        }
        if (!isChange) {
            marketplaceCheckboxs.getFirst().click();
            Thread.sleep(2000);
            totalCheck --;
            Thread.sleep(2000);
        }
        WebElement saveButton = driver.findElement(By.xpath("//button[span[text()='Save']]"));
        System.out.println("Đã thấy nút save");
        baseTestHelper.scrollDown(100);

        Thread.sleep(2000);
        saveButton.click();
        Thread.sleep(15000);
        System.out.println("Đã click nút save");

        return totalCheck;
    }

    public boolean saveMarketplaces() throws InterruptedException {
        try {
            switchToIframe();

            // Đếm số nút ban đầu
            List<WebElement> marketplaceButtons = driver.findElements(By.xpath("//button[span[text()='Marketplaces']]"));
            System.out.println("🔍 Tổng số nút Marketplaces tìm thấy: " + marketplaceButtons.size());

            if (marketplaceButtons.isEmpty()) {
                System.out.println("❌ Không tìm thấy nút Marketplaces.");
                return false;
            }

            int totalChecked = 0;

            for (int i = 0; i < marketplaceButtons.size(); i++) {
                // Re-locate mỗi lần để tránh stale element
                List<WebElement> currentButtons = driver.findElements(By.xpath("//button[span[text()='Marketplaces']]"));

                if (i >= currentButtons.size()) break;

                WebElement btn = currentButtons.get(i);

                if (btn.isDisplayed()) {
                    System.out.println("👉 Click Marketplaces button #" + (i + 1));

                    // Scroll để đảm bảo không bị che
                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
                    Thread.sleep(300);

                    btn.click();

                    baseTestHelper.scrollDown(150);
                    int count = this.checkMarketPlace();
                    totalChecked += count;
                }
            }

            // Chuyển sang tab Inventory
            By inventoryTabLocator = By.xpath("//ul[@class='tabs-container']//span[normalize-space()='Inventory']");
            WebElement inventoryButton = wait.until(ExpectedConditions.elementToBeClickable(inventoryTabLocator));
            inventoryButton.click();
            System.out.println("✅ Đã vào tab 'inventory'");

            Thread.sleep(5000);

            List<WebElement> bngOptions = driver.findElements(By.xpath("//optgroup[@label='BNG']/option"));
            List<WebElement> usaOptions = driver.findElements(By.xpath("//optgroup[@label='Amazon USA']/option"));

            int totalExpected = bngOptions.size() + usaOptions.size();
            System.out.println("🧾 Tổng mục BNG: " + bngOptions.size() + ", Amazon USA: " + usaOptions.size());
            System.out.println("✅ Tổng đã chọn: " + totalChecked);

            if (totalChecked == totalExpected) {
                System.out.println("🎉 PASSED - Đã chọn và lưu đúng số lượng marketplaces.");
                return true;
            } else {
                System.out.println("❌ FAILED - Số lượng không khớp.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("⚠️ Lỗi khi lưu Marketplaces: " + e.getMessage());
        } finally {
            switchToDefaultContent();
        }

        return false;
    }


} 
