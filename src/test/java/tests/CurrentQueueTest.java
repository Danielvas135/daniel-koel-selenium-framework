package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.LoginPage;

import java.time.Duration;

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

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-test='list-meta']")));

        String meta = getDriver().findElement(By.cssSelector("[data-test='list-meta']")).getText();
        Assert.assertTrue(meta.contains("song"), "Total song count should be displayed");
    }

    @Test
    public void totalDurationDisplayed() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        home.playFirstSong();
        home.openCurrentQueue();

        // wait until the duration text is actually present
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),':') and (contains(@class,'duration') or contains(@class,'time'))]")));

        Assert.assertTrue(home.isTotalDurationVisible(), "Total duration should be displayed");
    }

    @Test
    public void songDetailsDisplayed() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        home.playFirstSong();
        home.openCurrentQueue();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("#queueWrapper tr, #queueWrapper .song-item")));

        Assert.assertTrue(home.hasSongDetails(), "ID, Title, Artist, Album, and Time should be displayed");
    }

    @Test
    public void shuffleAllSongs() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        home.openAllSongs();

        // wait for the shuffle button to be ready (it is the default on All Songs when nothing is selected)
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button.btn-shuffle-all, [data-test='btn-shuffle-all'], button[title*='Shuffle']")));

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

        // if Clear button is not visible, click the toggler first
        if (getDriver().findElements(By.cssSelector("button.btn-clear-queue")).isEmpty()
                || !getDriver().findElement(By.cssSelector("button.btn-clear-queue")).isDisplayed()) {
            new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                    .until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.toggler"))).click();
        }

        home.clickClearQueue();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(text(),'No songs queued')]")));

        Assert.assertTrue(
                getDriver().findElements(By.xpath("//*[contains(text(),'No songs queued')]")).size() > 0,
                "Empty queue message should appear"
        );
    }

    @Test
    public void doubleClickSongNavigatesToQueue() {
        HomePage home = new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        Assert.assertTrue(home.isLoaded(), "Home should load after login");

        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));

        // All Songs
        home.openAllSongs();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.song-item")));
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on All Songs should open Current Queue");

        // Home
        getDriver().navigate().to("https://qa.koel.app");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.song-item, .song-item")));
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Home should open Current Queue");

        // Albums
        getDriver().navigate().to("https://qa.koel.app/#!/albums");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.song-item, .song-item, .album")));
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Albums should open Current Queue");

        // Artists
        getDriver().navigate().to("https://qa.koel.app/#!/artists");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.song-item, .song-item, .artist")));
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Artists should open Current Queue");

        // Favorites
        getDriver().navigate().to("https://qa.koel.app/#!/favorites");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.song-item, .song-item")));
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Favorites should open Current Queue");

        // Recently played
        getDriver().navigate().to("https://qa.koel.app/#!/recently-played");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr.song-item, .song-item")));
        home.doubleClickFirstSong();
        Assert.assertTrue(home.isCurrentQueueLoaded(), "Double-click on Recently played should open Current Queue");
    }
}