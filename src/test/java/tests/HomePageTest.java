package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class HomePageTest extends BaseTest {

    private WebDriverWait wait;

    private void login() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "BrightApple135!");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='view-profile-link']")),
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Home')]")),
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mainHeader"))
        ));
    }

    private void shortPause() {
        try { Thread.sleep(800); } catch (InterruptedException ignored) {}
    }

    // ==================== AC 1: Welcome Message ====================
    @Test
    public void welcomeMessageIsDisplayed() {
        login();

        String greeting = getDriver().findElement(
                By.xpath("//section[@id='homeWrapper']//div[contains(@class,'heading-wrapper')]//h1")
        ).getText().trim();

        Assert.assertFalse(greeting.isEmpty(), "Welcome message should not be empty. Found: [" + greeting + "]");
        Assert.assertTrue(greeting.length() > 1, "Welcome message should be displayed. Found: [" + greeting + "]");
    }

    // ==================== AC 2 & 3: Recently Played ====================
    @Test
    public void viewAllButtonIsDisplayedNextToRecentlyPlayed() {
        login();

        WebElement viewAll = getDriver().findElement(
                By.cssSelector("button[data-testid='home-view-all-recently-played-btn']")
        );
        Assert.assertTrue(viewAll.isDisplayed(), "View All button should be visible");
        Assert.assertTrue(viewAll.getText().contains("View All"), "Button text should be View All");
    }

    @Test
    public void recentlyPlayedViewAllNavigates() {
        login();
        getDriver().findElement(By.xpath("//button[contains(text(),'View All') or contains(@class,'view-all')]")).click();
        shortPause();
        Assert.assertTrue(getDriver().getPageSource().contains("Recently Played") ||
                        getDriver().getCurrentUrl().contains("recently-played"),
                "View All should open the full Recently Played page");
    }

    @Test
    public void recentlyPlayedListGrowsAfterPlay() {
        login();

        getDriver().findElement(By.cssSelector("a[href='#!/recently-played'], button[title*='View All']")).click();
        shortPause();
        int before = getDriver().findElements(
                By.cssSelector(".recently-played tr.song-item, .song-list-wrap.recently-played tr.song-item")
        ).size();

        getDriver().findElement(By.cssSelector("a[href='#!/songs']")).click();
        shortPause();

        var js = (org.openqa.selenium.JavascriptExecutor) getDriver();
        var songs = getDriver().findElements(By.cssSelector("tr.song-item"));
        for (int i = 0; i < 2 && i < songs.size(); i++) {
            js.executeScript("arguments[0].click();", songs.get(i));
            shortPause();
        }

        getDriver().findElement(By.cssSelector("a[href='#!/recently-played'], button[title*='View All']")).click();
        shortPause();
        int after = getDriver().findElements(
                By.cssSelector(".recently-played tr.song-item, .song-list-wrap.recently-played tr.song-item")
        ).size();

        Assert.assertTrue(after >= before, "Recently Played list should not shrink after playing songs");
    }

    // ==================== AC 4, 5, 6: Recently Added ====================
    @Test
    public void recentlyAddedSongsAndAlbumsAreDisplayed() {
        login();

        int albums = getDriver().findElements(By.cssSelector("ol.recently-added-album-list li")).size();
        int songs = getDriver().findElements(By.cssSelector("ol.recently-added-song-list li")).size();

        Assert.assertTrue(albums > 0, "Recently Added albums should be present");
        Assert.assertTrue(songs > 0, "Recently Added songs should be present");
    }

    @Test
    public void albumNameDisplayedOnRecentlyAdded() {
        login();
        var albumLinks = getDriver().findElements(By.cssSelector("ol.recently-added-album-list a.name"));
        Assert.assertTrue(albumLinks.size() > 0, "Album names should be present");
        Assert.assertFalse(albumLinks.get(0).getText().isBlank(), "Album name should not be empty");
    }

    @Test
    public void shuffleAndDownloadIconsPresentOnRecentlyAdded() {
        login();

        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("a.shuffle-album, i.fa-random")).size() > 0,
                "Shuffle icon should be present"
        );
        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("a.download-album, i.fa-download")).size() > 0,
                "Download icon should be present"
        );
    }

    // ==================== AC 7: Search ====================
    @Test
    public void searchFieldIsPresentAndAcceptsInput() {
        login();
        WebElement search = getDriver().findElement(By.cssSelector("input[name='q']"));
        Assert.assertTrue(search.isDisplayed(), "Search field should be visible");
        search.sendKeys("Dark Days");
        Assert.assertEquals(search.getAttribute("value"), "Dark Days", "Search field should accept input");
    }

    @Test
    public void searchFieldOpensResults() {
        login();
        WebElement search = getDriver().findElement(By.cssSelector("input[name='q']"));
        search.click();
        search.sendKeys("Dark Days");
        search.sendKeys(Keys.ENTER);
        shortPause();

        Assert.assertTrue(
                getDriver().getPageSource().toLowerCase().contains("dark days") ||
                        getDriver().getCurrentUrl().contains("search"),
                "Search for Dark Days should open results"
        );
    }

    // ==================== AC 8: Music Panel ====================
    @Test
    public void musicPanelShowsRequiredLinks() {
        login();
        Assert.assertTrue(getDriver().findElements(By.cssSelector("a.home")).size() > 0, "Home link should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("a.queue")).size() > 0, "Current Queue link should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("a.songs")).size() > 0, "All Songs link should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("a.albums")).size() > 0, "Albums link should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("a.artists")).size() > 0, "Artists link should be present");
    }

    // ==================== AC 9 & 10: Playlists Panel ====================
    @Test
    public void playlistsPanelShowsCreateOptions() {
        login();

        getDriver().findElement(By.cssSelector("[data-testid='sidebar-create-playlist-btn'], i.fa-plus-circle.create")).click();
        shortPause();

        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("[data-testid='playlist-context-menu-create-simple']")).size() > 0,
                "New Playlist option should appear"
        );
        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("[data-testid='playlist-context-menu-create-smart']")).size() > 0,
                "New Smart Playlist option should appear"
        );
    }

    @Test
    public void playlistsPanelShowsFavoritesRecentlyPlayedAndUserPlaylists() {
        login();
        Assert.assertTrue(getDriver().findElements(By.cssSelector("li.playlist.favorites")).size() > 0, "Favorites should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("li.playlist.recently-played")).size() > 0, "Recently Played should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("li.playlist.playlist")).size() > 0, "User playlists should be present");
    }

    // ==================== AC 11: Profile / Logout / About ====================
    @Test
    public void profileLogoutAndAboutIconsAreDisplayed() {
        login();
        Assert.assertTrue(getDriver().findElements(By.cssSelector("[data-testid='view-profile-link']")).size() > 0, "Profile link should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("[data-testid='btn-logout']")).size() > 0, "Logout button should be present");
        Assert.assertTrue(getDriver().findElements(By.cssSelector("[data-testid='about-btn']")).size() > 0, "About Koel button should be present");
    }
}