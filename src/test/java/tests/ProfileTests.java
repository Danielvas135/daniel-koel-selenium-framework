package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

import java.time.Duration;

public class ProfileTests extends BaseTest {

    private WebDriverWait wait;
    private static final String PASS = "BrightApple135!";

    private void openProfile() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-testid='view-profile-link']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profileWrapper")));
    }

    private void shortPause() {
        try { Thread.sleep(600); } catch (InterruptedException ignored) {}
    }

    @Test
    public void userCanChangeName() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileName")));
        String newName = "DanielQA_" + System.currentTimeMillis() % 1000;
        nameField.clear();
        nameField.sendKeys(newName);
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        shortPause();

        Assert.assertEquals(nameField.getAttribute("value"), newName, "Name field should show the new name");

        nameField.clear();
        nameField.sendKeys("Dvasquez");
        getDriver().findElement(By.id("inputProfileCurrentPassword")).clear();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Profile updated')]")));
    }

    @Test
    public void updatedNameDisplayedOnHomepage() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileName")));
        String newName = "DanielQA_" + System.currentTimeMillis() % 1000;
        nameField.clear();
        nameField.sendKeys(newName);
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Profile updated')]")));
        shortPause();

        String displayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.name"))).getText();
        Assert.assertTrue(displayed.contains(newName), "Updated name should appear in the header");

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);
        getDriver().findElement(By.id("inputProfileName")).clear();
        getDriver().findElement(By.id("inputProfileName")).sendKeys("Dvasquez");
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Profile updated')]")));
    }

    @Test
    public void userCanUpdateEmail() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileEmail")));
        String newEmail = "daniel.qa" + System.currentTimeMillis() % 1000 + "@testpro.io";
        emailField.clear();
        emailField.sendKeys(newEmail);
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        shortPause();

        Assert.assertEquals(emailField.getAttribute("value"), newEmail, "Email field should show the new email");

        emailField.clear();
        emailField.sendKeys("daniel.vasquez@testpro.io");
        getDriver().findElement(By.id("inputProfileCurrentPassword")).clear();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Profile updated')]")));
    }

    @Test
    public void userCanUpdatePassword() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();

        WebElement current = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileCurrentPassword")));
        current.clear();
        current.sendKeys(PASS);

        WebElement newPass = getDriver().findElement(By.id("inputProfileNewPassword"));
        newPass.clear();
        newPass.sendKeys("QuickMango135!");

        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Profile updated')]")));
        shortPause();

        ((JavascriptExecutor) getDriver())
                .executeScript("document.querySelector(\"[data-testid='btn-logout']\").click();");
        shortPause();
        getDriver().navigate().to("https://qa.koel.app");
        shortPause();

        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", "QuickMango135!");

        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("[data-testid='view-profile-link']")).size() > 0,
                "Should be logged in with the new password"
        );

        openProfile();
        WebElement current2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileCurrentPassword")));
        current2.clear();
        current2.sendKeys("QuickMango135!");

        WebElement restorePass = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileNewPassword")));
        restorePass.clear();
        restorePass.sendKeys(PASS);

        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Profile updated')]")));
    }

    @Test
    public void userCanChangeTheme() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();

        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div[data-testid='theme-card-pines']"))).click();
        shortPause();

        getDriver().navigate().to("https://qa.koel.app");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='view-profile-link']")));

        boolean pinesApplied = getDriver().findElements(
                By.cssSelector("body.theme-pines, .theme-pines, [class*='pines']")).size() > 0
                || getDriver().getPageSource().contains("pines");

        Assert.assertTrue(pinesApplied, "Pines theme should be applied on the homepage");

        openProfile();
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("div[data-testid='theme-card-classic']"))).click();
        shortPause();
    }

    @Test
    public void showNowPlayingNotification() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);

        WebElement checkbox = getDriver().findElement(By.cssSelector("input[name='notify']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
            getDriver().findElement(By.cssSelector("button.btn-submit")).click();
            shortPause();
        }

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("input[name='notify']")).isSelected(),
                "Now Playing checkbox should remain checked after save"
        );
    }

    @Test
    public void confirmBeforeClosingEnabled() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);

        WebElement checkbox = getDriver().findElement(By.cssSelector("input[name='confirm_closing']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
            getDriver().findElement(By.cssSelector("button.btn-submit")).click();
            shortPause();
        }

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("input[name='confirm_closing']")).isSelected(),
                "Confirm before closing should be checked"
        );
    }

    @Test
    public void albumArtOverlayCheckboxStaysChecked() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);

        WebElement checkbox = getDriver().findElement(By.cssSelector("input[name='show_album_art_overlay']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
            getDriver().findElement(By.cssSelector("button.btn-submit")).click();
            shortPause();
        }

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("input[name='show_album_art_overlay']")).isSelected(),
                "Album art overlay checkbox should stay checked after save"
        );
    }

    @Test
    public void albumArtOverlayVisibleWhenEnabled() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();
        getDriver().findElement(By.id("inputProfileCurrentPassword")).sendKeys(PASS);

        WebElement checkbox = getDriver().findElement(By.cssSelector("input[name='show_album_art_overlay']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
            getDriver().findElement(By.cssSelector("button.btn-submit")).click();
            shortPause();
        }

        getDriver().navigate().to("https://qa.koel.app/#!/songs");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("tr.song-item"))).click();
        shortPause();

        boolean overlayPresent = getDriver().findElements(
                By.cssSelector("[data-testid='album-art-overlay'], .album-art-overlay")
        ).size() > 0;

        Assert.assertTrue(overlayPresent,
                "Translucent blurred album art overlay should be visible when the option is enabled");
    }
}