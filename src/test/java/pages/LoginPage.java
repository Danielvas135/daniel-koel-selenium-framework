package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.TimeoutException;

public class LoginPage extends BasePage {

    @FindBy(css = "input[type='email']")
    private WebElement emailInput;

    @FindBy(css = "input[type='password']")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement submitBtn;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".error, .alert, [role='alert'], .login-error")
    private WebElement errorMessage;

    public boolean isLoaded() {
        try {
            waitVisible(emailInput);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            waitVisible(errorMessage);
            return errorMessage.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void provideEmail(String email) {
        type(emailInput, email);
    }

    public void providePassword(String password) {
        type(passwordInput, password);
    }

    public void clickSubmit() {
        safeClick(submitBtn);
    }

    public HomePage loginAs(String email, String password) {
        provideEmail(email);
        providePassword(password);
        clickSubmit();
        HomePage home = new HomePage(driver);
        home.isLoaded();
        return home;
    }
    public String getEmailValidationMessage() {
        return emailInput.getAttribute("validationMessage");
    }
}