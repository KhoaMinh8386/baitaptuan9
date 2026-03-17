package bai2_pom.tests;

import bai1_base.base.BaseTest;
import bai2_pom.pages.CartPage;
import bai2_pom.pages.InventoryPage;
import bai2_pom.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class CartTest extends BaseTest {

    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";

    @Test
    public void testAddItemToCart() {
        LoginPage loginPage = new LoginPage(getDriver());
        int count = loginPage.login(VALID_USER, VALID_PASS)
                .addFirstItemToCart()
                .getCartItemCount();
        Assert.assertEquals(count, 1, "Số lượng trên Cart Badge phải là 1");
    }

    @Test
    public void testRemoveItemFromCart() {
        LoginPage loginPage = new LoginPage(getDriver());
        CartPage cartPage = loginPage.login(VALID_USER, VALID_PASS)
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();
        Assert.assertEquals(cartPage.getItemCount(), 0, "Cart phải rống sau khi xóa");
    }

    @Test
    public void testAddMultipleItemsAndVerify() {
        LoginPage loginPage = new LoginPage(getDriver());
        InventoryPage inventoryPage = loginPage.login(VALID_USER, VALID_PASS)
                .addItemByName("Sauce Labs Backpack")
                .addItemByName("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryPage.getCartItemCount(), 2, "Cart badge count phải bằng 2");
        List<String> cartItemNames = inventoryPage.goToCart().getItemNames();
        Assert.assertTrue(cartItemNames.contains("Sauce Labs Backpack"), "Không có balo");
        Assert.assertTrue(cartItemNames.contains("Sauce Labs Bike Light"), "Không có đèn xe");
    }
}
