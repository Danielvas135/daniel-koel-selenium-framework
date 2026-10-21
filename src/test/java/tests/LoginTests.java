package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;
import org.openqa.selenium.TimeoutException;

public class LoginTests extends BaseTest {

    @Test
    public void validLogin() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");
        Assert.assertTrue(home.isLoaded(), "Home should load after valid login");
    }

    @Test
    public void invalidPasswordDoesNotOpenHome() {
        LoginPage login = new LoginPage(getDriver());
        login.provideEmail("daniel.vasquez@testpro.io");
        login.providePassword("wrongpassword");
        login.clickSubmit();

        Assert.assertFalse(
                new HomePage(getDriver()).isLoaded(),
                "Home should not load with a bad password"
        );
    }

    @Test
    public void invalidEmailFormatShowsError() {
        LoginPage login = new LoginPage(getDriver());
        login.provideEmail("bademail");
        login.providePassword("anypassword");
        login.clickSubmit();

        String message = login.getEmailValidationMessage();
        Assert.assertTrue(
                message.contains("Please include an '@'"),
                "Should show browser email format message. Got: " + message
        );
    }

    @Test
    public void emptyFieldsShowError() {
        LoginPage login = new LoginPage(getDriver());
        login.clickSubmit();

        String message = login.getEmailValidationMessage();
        Assert.assertTrue(
                message.toLowerCase().contains("fill") || message.toLowerCase().contains("required"),
                "Empty email should show required message. Got: " + message
        );
    }

    @Test
    public void loginRedirectsToSavedPage() {
        getDriver().get("https://qa.koel.app/#!/albums");

        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");
        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("albums"),
                "User should stay on the albums page after login"
        );
    }

}