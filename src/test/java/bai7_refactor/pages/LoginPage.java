package bai7_refactor.pages;

import bai1_base.base.BasePage;
import bai7_refactor.pages.InventoryPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * ✅ REFACTORED LoginPage — Bài 7
 * - Tất cả locator nằm trong @FindBy (KHÔNG còn trong test)
 * - Fluent Interface: login() trả về InventoryPage
 * - KHÔNG có Assert, KHÔNG có sleep
 */
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public InventoryPage login(String username, String password) {
        waitAndType(usernameInput, username);
        waitAndType(passwordInput, password);
        waitAndClick(loginButton);
        return new InventoryPage(driver);
    }

    public LoginPage loginExpectingFailure(String username, String password) {
        waitAndType(usernameInput, username);
        waitAndType(passwordInput, password);
        waitAndClick(loginButton);
        return this;
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(By.cssSelector("[data-test='error']"));
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }
}
