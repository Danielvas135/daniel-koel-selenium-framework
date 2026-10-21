package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;



public class HomePage extends BasePage {

    // ---- PageFactory elements ----
    @FindBy(xpath = "//a[@href='#!/songs' and normalize-space(text())='All Songs']")
    private WebElement allSongsLink;

    @FindBy(css = "[data-testid='sidebar-create-playlist-btn']")
    private WebElement sidebarPlaylistBtn;

    @FindBy(css = "[data-testid='playlist-context-menu-create-simple']")
    private WebElement sidebarCreateBtn;

    @FindBy(css = "form[name='create-simple-playlist-form'] input[name='name']")
    private WebElement sidebarCreateInput;

    @FindBy(xpath = "//button[contains(@class, 'btn-delete-playlist')]")
    private WebElement deletePlaylistBtn;

    @FindBy(xpath = "//button[contains(@class, 'ok') or contains(text(), 'OK')]")
    private WebElement okBtn;

    @FindBy(css = "[data-testid='btn-logout']")
    private WebElement logoutBtn;

    @FindBy(css = "[data-testid='toggle-extra-panel-btn']")
    private WebElement infoBtn;

    @FindBy(css = "#extra, [data-testid='extra-panel']")
    private WebElement infoPanel;

    @FindBy(css = "#extraPanelAlbum, [id='extraPanelAlbum']")
    private WebElement albumName;

    @FindBy(css = "#extraPanelArtist, [id='extraPanelArtist']")
    private WebElement artistName;

    @FindBy(css = "tr.song-item")
    private List<WebElement> songRows;

    @FindBy(css = "[data-test='btn-play-all'], .btn-play-all")
    private WebElement playAllBtn;

    @FindBy(css = "[data-test='btn-shuffle-all'], .btn-shuffle-all")
    private WebElement shuffleAllBtn;

    // ---- dynamic locators ----
    private By playlistByName(String name) {
        return By.xpath("//section[@id='playlists']//a[normalize-space(text())='" + name + "']");
    }

    private static final By RENAME_INPUT = By.cssSelector("[data-testid='inline-playlist-name-input']");
    private static final By ALL_PLAYLIST_ITEMS = By.xpath("//section[@id='playlists']//a");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        try {
            waitVisible(allSongsLink);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void logout() {
        safeClick(logoutBtn);
    }

    public void playFirstSong() {
        safeClick(allSongsLink);
        shortPause();

        if (!songRows.isEmpty()) {
            WebElement first = songRows.get(0);
            // Double-click starts playback on most Koel builds
            new Actions(driver)
                    .moveToElement(first)
                    .doubleClick(first)
                    .perform();
            shortPause();
        }
    }
    public void openAllSongs() {
        safeClick(allSongsLink);
        shortPause();
    }

    public void clickShuffleAll() {
        By shuffle = By.cssSelector("button.btn-shuffle-all, [data-test='btn-shuffle-all']");
        WebElement btn = waitClickable(shuffle);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        shortPause();
    }

    public void openInfoPanel() {
        if (!isInfoPanelVisible()) {
            safeClick(infoBtn);
            shortPause();
        }
    }

    public boolean isInfoPanelVisible() {
        try {
            WebElement albumTab = driver.findElement(By.id("extraTabAlbum"));
            waitClickable(albumTab);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isShuffleButtonVisible() {
        try {
            waitClickable(shuffleAllBtn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean isLogoutButtonVisible() {
        try {
            waitClickable(logoutBtn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isInfoPanelHidden() {
        try {
            WebElement panel = driver.findElement(By.cssSelector("#extra, [data-testid='extra-panel']"));
            String cls = panel.getAttribute("class");
            return cls == null || !cls.contains("showing");
        } catch (Exception e) {
            return true;
        }
    }

    public void openArtistTab() {
        WebElement tab = driver.findElement(By.id("extraTabArtist"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        shortPause();
    }

    public void openAlbumTab() {
        WebElement tab = driver.findElement(By.id("extraTabAlbum"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        shortPause();
    }

    public void openLyricsTab() {
        WebElement tab = driver.findElement(By.id("extraTabLyrics"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        shortPause();
    }

    public void closeInfoPanel() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", infoBtn);
        shortPause();
    }

    public String getArtistPanelText() {
        try {
            WebElement panel = driver.findElement(By.cssSelector("#extraPanelArtist [data-test='artist-info'], #extraPanelArtist"));
            return panel.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public String getAlbumPanelText() {
        try {
            WebElement panel = driver.findElement(By.cssSelector("#extraPanelAlbum [data-test='album-info'], #extraPanelAlbum"));
            return panel.getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void clickArtistShuffle() {
        By shuffle = By.xpath(
                "//div[@id='extraPanelArtist']//button[contains(@title,'Shuffle') or contains(@class,'shuffle')]"
        );
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(shuffle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        shortPause();
    }

    public void clickAlbumShuffle() {
        By shuffle = By.xpath(
                "//div[@id='extraPanelAlbum']//button[contains(@title,'Shuffle') or contains(@class,'shuffle')]"
        );
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(shuffle));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        shortPause();
    }

    public String getLyricsPanelText() {
        try {
            return driver.findElement(By.id("extraPanelLyrics")).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    // ================== CORE ACTIONS ==================

    public void createPlaylist(String name) {
        safeClick(sidebarPlaylistBtn);
        safeClick(sidebarCreateBtn);
        type(sidebarCreateInput, name);
        sidebarCreateInput.sendKeys(Keys.ENTER);
        shortPause();
    }

    public void renamePlaylist(String oldName, String newName) {
        WebElement item = waitVisible(playlistByName(oldName));

        // Koel supports inline edit via double-click on playlist
        new Actions(driver)
                .moveToElement(item)
                .doubleClick(item)
                .perform();

        WebElement input = waitVisible(RENAME_INPUT);
        input.sendKeys(Keys.CONTROL, "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(newName, Keys.ENTER);

        shortPause();
    }

    public void deletePlaylist(String name) {
        List<WebElement> list = driver.findElements(playlistByName(name));
        if (list.isEmpty()) {
            return; // nothing to delete, keep it simple
        }

        WebElement item = list.get(0);
        item.click();

        safeClick(deletePlaylistBtn);

        try {
            safeClick(okBtn);
        } catch (Exception ignored) {
        }

        shortPause();
    }

    // ================== SMALL HELPERS ==================

    public boolean playlistExists(String name) {
        return !driver.findElements(playlistByName(name)).isEmpty();
    }

    public List<String> listPlaylists() {
        List<String> out = new ArrayList<>();
        for (WebElement el : driver.findElements(ALL_PLAYLIST_ITEMS)) {
            out.add(el.getText().trim());
        }
        return out;
    }

    private void shortPause() {
        try {
            Thread.sleep(300); // tiny, just to let UI apply changes
        } catch (InterruptedException ignored) {}
    }
}
