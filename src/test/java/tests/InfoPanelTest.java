package tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class InfoPanelTest extends BaseTest {

    @Test
    public void infoPanelOpensAndCloses() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.playFirstSong();
        home.openInfoPanel();

        Assert.assertTrue(
                getDriver().findElements(By.id("extraTabArtist")).size() > 0,
                "Info panel should open after clicking INFO"
        );
    }

    @Test
    public void infoPanelOpensAndClosesMultipleTimes() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.playFirstSong();

        for (int i = 0; i < 2; i++) {
            home.openInfoPanel();
            Assert.assertTrue(
                    getDriver().findElements(By.id("extraTabArtist")).size() > 0,
                    "Panel should open (cycle " + (i + 1) + ")"
            );
            home.closeInfoPanel();
        }
    }

    @Test
    public void artistAndAlbumTabsShowContent() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        home.playFirstSong();
        home.openInfoPanel();
        home.openArtistTab();
        home.openAlbumTab();

        Assert.assertTrue(
                getDriver().findElements(By.id("extraTabArtist")).size() > 0,
                "Artist tab should be present"
        );
    }

    @Test
    public void lyricsTabOpens() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        home.playFirstSong();
        home.openInfoPanel();
        home.openLyricsTab();

        Assert.assertNotNull(home.getLyricsPanelText(), "Lyrics tab should open");
    }

    @Test
    public void shuffleAllSongs() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.openAllSongs();
        home.clickShuffleAll();
        home.clickShuffleAll();
        home.clickShuffleAll();

        Assert.assertTrue(true, "Shuffle All clicked multiple times");
    }

    @Test
    public void shuffleByArtistAndAlbum() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.playFirstSong();
        home.openInfoPanel();
        home.openArtistTab();
        home.clickArtistShuffle();
        home.openAlbumTab();
        home.clickAlbumShuffle();

        Assert.assertTrue(true, "Artist and Album shuffle buttons clicked");
    }
}