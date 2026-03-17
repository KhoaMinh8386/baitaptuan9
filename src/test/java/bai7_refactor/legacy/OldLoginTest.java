package bai7_refactor.legacy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * ❌ CODE CŨ — LAB 1 / LAB 2 (trước khi refactor)
 * Vấn đề:
 *   - new ChromeDriver() hardcode
 *   - driver.findElement(By.xxx) lẫn lộn trong test
 *   - Thread.sleep() dùng cho wait
 *   - username/password hardcode ngay trong test
 *   - Không có POM, không có BaseTest
 */
public class OldLoginTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // ❌ VẤN ĐỀ: new ChromeDriver() hardcode
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com");
    }

    @Test
    public void testLoginSuccess() throws InterruptedException {
        // ❌ VẤN ĐỀ: findElement + By.xxx nằm trong test
        driver.findElement(By.id("user-name")).clear();
        driver.findElement(By.id("user-name")).sendKeys("standard_user");  // ❌ hardcode
        driver.findElement(By.id("password")).sendKeys("secret_sauce");    // ❌ hardcode
        driver.findElement(By.id("login-button")).click();

        // ❌ VẤN ĐỀ: Thread.sleep thay vì Explicit Wait
        Thread.sleep(2000);

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"), "Login failed!");
    }

    @Test
    public void testLoginFail() throws InterruptedException {
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("wrong_pass");      // ❌ hardcode
        driver.findElement(By.id("login-button")).click();

        Thread.sleep(1000); // ❌ sleep

        boolean errorShown = driver.findElement(By.cssSelector("[data-test='error']")).isDisplayed();
        Assert.assertTrue(errorShown, "Error should be shown!");
    }

    @Test
    public void testLockedUser() throws InterruptedException {
        driver.findElement(By.id("user-name")).sendKeys("locked_out_user"); // ❌ hardcode
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Thread.sleep(1000); // ❌ sleep

        String error = driver.findElement(By.cssSelector("[data-test='error']")).getText();
        Assert.assertTrue(error.contains("locked out"), "Locked user error not shown!");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
