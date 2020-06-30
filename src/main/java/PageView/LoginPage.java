package PageView;

import Support.SetupServer;
import org.openqa.selenium.By;

public class LoginPage {
    SetupServer driver;
    By emailField = By.id("formHorizontalEmail");
    By passField = By.id("formHorizontalPassword");
    By btnLogin = By.xpath("//a[@class='col-login__btn']");
    By label = By.xpath("//a[@class='navbar-brand']");

    public LoginPage(SetupServer driver) {
        this.driver = driver;
    }

    /**
     * @param email
     * @param password
     */
    public void Login(String email, String password) {
        inputEmail(email);
        inputPassword(password);
        clickBtnLogin();
        verifyLoginSuccess();
    }

    public void verifyLoginSuccess() {
        try {
            driver.isElementExist(label);
            System.out.println("Login Successfully");
        } catch (Exception e) {
            System.out.println("Login Failed");
        }
    }

    // input email address
    public void inputEmail(String email) {
        System.out.println("Input email address: " + email);
        driver.findElement(emailField).sendKeys(email);
    }

    // input password
    public void inputPassword(String password) {
        System.out.println("Input password: " + password);
        driver.findElement(passField).sendKeys(password);
    }

    // click Login button
    public void clickBtnLogin() {
        System.out.println("Click 'Login' button");
        driver.findElement(btnLogin).click();

    }
}
