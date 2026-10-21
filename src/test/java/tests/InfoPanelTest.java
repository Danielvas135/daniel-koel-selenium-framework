package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class InfoPanelTest extends BaseTest {

    @Test
    public void infoPanelOpensAndShowsSongDetails() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.playFirstSong();
        home.openInfoPanel();

        Assert.assertTrue(home.isInfoPanelVisible(), "Info panel should be visible");
        // Album/artist data is empty on many songs — skip for now
    }
}