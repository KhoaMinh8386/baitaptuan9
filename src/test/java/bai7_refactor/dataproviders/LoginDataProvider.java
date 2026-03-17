package bai7_refactor.dataproviders;

import org.testng.annotations.DataProvider;

/**
 * ✅ REFACTORED — DataProvider thay thế hardcode username/password
 * Bài 7: Di chuyển dữ liệu test ra @DataProvider
 *
 * Trước (❌ cũ):
 *   driver.findElement(By.id("user-name")).sendKeys("standard_user");
 *   driver.findElement(By.id("password")).sendKeys("secret_sauce");
 *
 * Sau (✅ mới):
 *   @Test(dataProvider = "loginData")
 *   public void test(String user, String pass, boolean expectSuccess, String desc)
 */
public class LoginDataProvider {

    @DataProvider(name = "loginData")
    public static Object[][] loginData() {
        return new Object[][]{
            // username,         password,        expectSuccess, description
            {"standard_user",   "secret_sauce",   true,  "✅ Đăng nhập thành công"},
            {"standard_user",   "wrong_password", false, "❌ Sai password"},
            {"locked_out_user", "secret_sauce",   false, "❌ Tài khoản bị khóa"},
            {"",                "secret_sauce",   false, "❌ Username rỗng"},
            {"standard_user",   "",               false, "❌ Password rỗng"},
        };
    }
}
