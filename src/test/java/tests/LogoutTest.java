package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LogoutTest extends BaseTest {

    @Test
    public void logoutReturnsToLoginPage() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.logout();

        LoginPage login = new LoginPage(getDriver());
        Assert.assertTrue(login.isLoaded(), "Login page should appear after logout");
    }
    @Test
    public void logoutButtonIsPresent() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");
        Assert.assertTrue(home.isLogoutButtonVisible(), "Logout button should be present next to Profile");
    }

    @Test
    public void logoutAfterProfileUpdate() {
        // For now this just logs out after a normal login.
        // If you later add a real profile-update step, call it here first.
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.logout();

        LoginPage login = new LoginPage(getDriver());
        Assert.assertTrue(login.isLoaded(), "Login page should appear after logout");
    }
}