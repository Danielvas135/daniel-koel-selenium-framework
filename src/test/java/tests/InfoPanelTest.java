package tests;

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
        Assert.assertTrue(home.isInfoPanelVisible(), "Info panel should open after clicking INFO");

        home.closeInfoPanel();
        Assert.assertTrue(home.isInfoPanelHidden(), "Info panel should hide after clicking INFO again");
    }

    @Test
    public void artistAndAlbumTabsShowContent() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        home.playFirstSong();
        home.openInfoPanel();

        home.openArtistTab();
        String artist = home.getArtistPanelText();
        Assert.assertFalse(artist.isEmpty(), "Artist panel should contain text. Got: " + artist);

        home.openAlbumTab();
        String album = home.getAlbumPanelText();
        Assert.assertFalse(album.isEmpty(), "Album panel should contain text. Got: " + album);
    }

    @Test
    public void lyricsTabOpens() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        home.playFirstSong();
        home.openInfoPanel();
        home.openLyricsTab();

        String lyrics = home.getLyricsPanelText();
        // Lyrics may be empty on some songs — just confirm the tab opens
        Assert.assertNotNull(lyrics, "Lyrics tab should open");
    }
}