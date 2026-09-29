package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class PlaylistTests extends BaseTest {

    @Test
    public void createRenameDeletePlaylist() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "KoelTest123!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        String name = "Playlist_" + System.currentTimeMillis() / 1000;
        String renamed = name + "_Renamed";

        home.createPlaylist(name);
        Assert.assertTrue(home.playlistExists(name), "Playlist should exist after create");

        home.renamePlaylist(name, renamed);
        Assert.assertTrue(home.playlistExists(renamed), "Renamed playlist should exist");
        Assert.assertFalse(home.playlistExists(name), "Old name should be gone after rename");

        home.deletePlaylist(renamed);

        Assert.assertFalse(
                home.playlistExists(renamed),
                "Renamed playlist should be gone after delete"
        );
    }
}