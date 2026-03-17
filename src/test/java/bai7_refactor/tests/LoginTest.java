package bai7_refactor.tests;

import bai1_base.base.BaseTest;
import bai7_refactor.pages.InventoryPage;
import bai7_refactor.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * ✅ REFACTORED LoginTest — Bài 7
 *
 * SO SÁNH BEFORE / AFTER:
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │  ❌ Code cũ (legacy/OldLoginTest.java)                             │
 * │  - new ChromeDriver() trong @BeforeMethod                          │
 * │  - driver.findElement(By.id("user-name")).sendKeys("standard_user")│
 * │  - Thread.sleep(2000) để chờ                                       │
 * │  - Credential hardcode thẳng trong test                            │
 * │                                                                     │
 * │  ✅ Code mới (bai7_refactor)                                       │
 * │  - BaseTest.getDriver() (ThreadLocal, tái sử dụng)                 │
 * │  - LoginPage.login(user, pass) — locator ẩn sau Page Object        │
 * │  - WebDriverWait + ExpectedConditions (KHÔNG sleep)                │
 * │  - @DataProvider — dữ liệu tách khỏi logic test                   │
 * └─────────────────────────────────────────────────────────────────────┘
 */
public class LoginTest extends BaseTest {

    @Test(dataProvider = "loginData",
          dataProviderClass = bai7_refactor.dataproviders.LoginDataProvider.class,
          description = "Bài 7 - DDT Login Test")
    public void testLogin(String username, String password, boolean expectSuccess, String description) {
        System.out.println("[BAI7] " + description);

        LoginPage loginPage = new LoginPage(getDriver());

        if (expectSuccess) {
            InventoryPage inventoryPage = loginPage.login(username, password);
            Assert.assertTrue(inventoryPage.isLoaded(),
                "[" + description + "] — Trang Inventory phải được load sau khi đăng nhập thành công");
        } else {
            loginPage.loginExpectingFailure(username, password);
            Assert.assertTrue(loginPage.isErrorDisplayed(),
                "[" + description + "] — Phải hiển thị thông báo lỗi");
        }
    }
}
