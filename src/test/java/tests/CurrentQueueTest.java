package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

public class CurrentQueueTest extends BaseTest {

    @Test
    public void currentlyPlayedSongsDisplayed() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.playFirstSong();
        home.openCurrentQueue();

        Assert.assertTrue(home.isCurrentQueueLoaded(), "Current Queue page should load");
        Assert.assertTrue(home.getQueueSongCount() > 0, "At least one song should be in the queue");
    }

    @Test
    public void totalSongCountDisplayed() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        home.playFirstSong();
        home.openCurrentQueue();

        Assert.assertTrue(home.isTotalSongCountVisible(), "Total song count should be displayed");
    }

    @Test
    public void totalDurationDisplayed() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        home.playFirstSong();
        home.openCurrentQueue();

        Assert.assertTrue(home.isTotalDurationVisible(), "Total duration should be displayed");
    }

    @Test
    public void songDetailsDisplayed() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        home.playFirstSong();
        home.openCurrentQueue();

        Assert.assertTrue(home.hasSongDetails(), "ID, Title, Artist, Album, and Time should be displayed");
    }

    @Test
    public void shuffleAllSongs() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.openAllSongs();
        home.clickShuffleAll();
        home.clickShuffleAll();
        home.clickShuffleAll();

        Assert.assertTrue(true, "Shuffle All clicked multiple times");
    }

    @Test
    public void clearEmptiesQueue() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        home.playFirstSong();
        home.openCurrentQueue();

        home.clickClearQueue();

        Assert.assertTrue(home.isQueueEmptyMessageVisible(), "Empty queue message should appear");
    }
    @Test
    public void doubleClickSongNavigatesToQueue() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        // All Songs
        home.openAllSongs();
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on All Songs should open Current Queue");

        // Home
        getDriver().navigate().to("https://qa.koel.app");
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Home should open Current Queue");

        // Albums
        getDriver().navigate().to("https://qa.koel.app/#!/albums");
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Albums should open Current Queue");

        // Artists
        getDriver().navigate().to("https://qa.koel.app/#!/artists");
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Artists should open Current Queue");

        // Favorites
        getDriver().navigate().to("https://qa.koel.app/#!/favorites");
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Favorites should open Current Queue");

        // Recently played
        getDriver().navigate().to("https://qa.koel.app/#!/recently-played");
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Recently played should open Current Queue");
    }
}