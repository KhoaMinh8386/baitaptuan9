package bai2_pom.tests;

import bai1_base.base.BaseTest;
import bai2_pom.pages.InventoryPage;
import bai2_pom.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";
    private static final String WRONG_PASS = "wrong_password";

    @Test
    public void testLoginSuccess() {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(VALID_USER, VALID_PASS);
        Assert.assertTrue(inventoryPage.isLoaded(), "Trang Inventory không được load sau khi đăng nhập!");
    }

    @Test
    public void testLoginFail() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure(VALID_USER, WRONG_PASS);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi không được hiển thị");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"), "Câu lỗi không đúng");
    }

    @Test
    public void testEmptyUsername() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginExpectingFailure("", VALID_PASS);
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Lỗi field trống không hiển thị");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"), "Câu lỗi sai hoặc mất tiêu");
    }
}
