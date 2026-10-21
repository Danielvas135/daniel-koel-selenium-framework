package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends BasePage {

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

    @FindBy(css = "tr.song-item")
    private List<WebElement> songRows;

    @FindBy(css = "[data-test='btn-shuffle-all'], .btn-shuffle-all")
    private WebElement shuffleAllBtn;

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
            new Actions(driver).moveToElement(first).doubleClick(first).perform();
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

    public boolean isLogoutButtonVisible() {
        try {
            waitClickable(logoutBtn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void openArtistTab() {
        jsClick(By.id("extraTabArtist"));
    }

    public void openAlbumTab() {
        jsClick(By.id("extraTabAlbum"));
    }

    public void openLyricsTab() {
        jsClick(By.id("extraTabLyrics"));
    }

    public void closeInfoPanel() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", infoBtn);
    }

    public void clickArtistShuffle() {
        By shuffle = By.xpath("//div[@id='extraPanelArtist']//button[contains(@title,'Shuffle') or contains(@class,'shuffle')]");
        jsClick(shuffle);
    }

    public void clickAlbumShuffle() {
        By shuffle = By.xpath("//div[@id='extraPanelAlbum']//button[contains(@title,'Shuffle') or contains(@class,'shuffle')]");
        jsClick(shuffle);
    }

    public String getLyricsPanelText() {
        try {
            return driver.findElement(By.id("extraPanelLyrics")).getText().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public void createPlaylist(String name) {
        safeClick(sidebarPlaylistBtn);
        safeClick(sidebarCreateBtn);
        type(sidebarCreateInput, name);
        sidebarCreateInput.sendKeys(Keys.ENTER);
        shortPause();
    }

    public void renamePlaylist(String oldName, String newName) {
        WebElement item = waitVisible(playlistByName(oldName));
        new Actions(driver).moveToElement(item).doubleClick(item).perform();
        WebElement input = waitVisible(RENAME_INPUT);
        input.sendKeys(Keys.CONTROL, "a");
        input.sendKeys(Keys.BACK_SPACE);
        input.sendKeys(newName, Keys.ENTER);
        shortPause();
    }

    public void deletePlaylist(String name) {
        List<WebElement> list = driver.findElements(playlistByName(name));
        if (list.isEmpty()) return;
        list.get(0).click();
        safeClick(deletePlaylistBtn);
        try { safeClick(okBtn); } catch (Exception ignored) {}
        shortPause();
    }

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

    private void jsClick(By locator) {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        shortPause();
    }

    private void shortPause() {
        try { Thread.sleep(100); } catch (InterruptedException ignored) {}
    }
}