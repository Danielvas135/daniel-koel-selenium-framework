package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class LogoutTest extends BaseTest {

    @Test
    public void logoutReturnsToLoginPage() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.logout();

        LoginPage login = new LoginPage(getDriver());
        Assert.assertTrue(login.isLoaded(), "Login page should appear after logout");
    }
}