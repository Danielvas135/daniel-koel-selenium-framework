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
import java.util.List;


public class ProfileTests extends BaseTest {

    private WebDriverWait wait;
    private static final String PASS = "BrightApple135!";

    private void openProfile() {
        wait = new WebDriverWait(getDriver(), Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-testid='view-profile-link']"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("profileWrapper")));
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Profile updated')]")));

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

        WebElement currentPass = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("inputProfileCurrentPassword")));
        currentPass.sendKeys(PASS);

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileName")));
        String newName = "DanielQA_" + System.currentTimeMillis() % 1000;
        nameField.clear();
        nameField.sendKeys(newName);
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Profile updated')]")));

        String displayed = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("span.name"))).getText();
        Assert.assertTrue(displayed.contains(newName), "Updated name should appear in the header");

        // restore original name
        openProfile();
        currentPass = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.id("inputProfileCurrentPassword")));
        currentPass.sendKeys(PASS);

        WebElement restoreName = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileName")));
        restoreName.clear();
        restoreName.sendKeys("Dvasquez");
        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Profile updated')]")));
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Profile updated')]")));

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
        final String currentPass = "BrightApple135!";
        final String tempPass = "QuickMango135!";

        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", currentPass);

        openProfile();

        WebElement current = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileCurrentPassword")));
        current.clear();
        current.sendKeys(currentPass);

        WebElement newPass = getDriver().findElement(By.id("inputProfileNewPassword"));
        newPass.clear();
        newPass.sendKeys(tempPass);

        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Profile updated')]")));

        ((JavascriptExecutor) getDriver())
                .executeScript("document.querySelector(\"[data-testid='btn-logout']\").click();");

        getDriver().navigate().to("https://qa.koel.app");

        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", tempPass);

        Assert.assertTrue(
                getDriver().findElements(By.cssSelector("[data-testid='view-profile-link']")).size() > 0,
                "Should be logged in with the new password"
        );

        // restore original password
        openProfile();
        WebElement current2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileCurrentPassword")));
        current2.clear();
        current2.sendKeys(tempPass);

        WebElement restorePass = wait.until(ExpectedConditions.elementToBeClickable(By.id("inputProfileNewPassword")));
        restorePass.clear();
        restorePass.sendKeys(currentPass);

        getDriver().findElement(By.cssSelector("button.btn-submit")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[contains(text(),'Profile updated')]")));
    }

    @Test
    public void userCanChangeTheme() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();

        WebElement pinesCard = getDriver().findElement(By.cssSelector("div[data-testid='theme-card-pines']"));
        pinesCard.click();

        wait.until(ExpectedConditions.attributeContains(pinesCard, "class", "selected"));

        Assert.assertTrue(
                pinesCard.getAttribute("class").contains("selected"),
                "Pines theme should be selected"
        );
    }

    @Test
    public void showNowPlayingNotification() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();

        List<WebElement> currentPass = getDriver().findElements(By.id("inputProfileCurrentPassword"));
        if (!currentPass.isEmpty()) {
            currentPass.get(0).sendKeys(PASS);
        }

        WebElement checkbox = getDriver().findElement(By.cssSelector("input[name='notify']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
            getDriver().findElement(By.cssSelector("button.btn-submit")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Profile updated')]")));
        }

        getDriver().navigate().to("https://qa.koel.app");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='view-profile-link']")));

        openProfile();
        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("input[name='notify']")).isSelected(),
                "Show now playing notification should be checked after reload"
        );
    }

    @Test
    public void confirmBeforeClosingEnabled() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();

        List<WebElement> currentPass = getDriver().findElements(By.id("inputProfileCurrentPassword"));
        if (!currentPass.isEmpty()) {
            currentPass.get(0).sendKeys(PASS);
        }

        WebElement checkbox = getDriver().findElement(By.cssSelector("input[name='confirm_closing']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
            getDriver().findElement(By.cssSelector("button.btn-submit")).click();
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Profile updated')]")));
        }

        getDriver().navigate().to("https://qa.koel.app");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='view-profile-link']")));

        openProfile();
        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("input[name='confirm_closing']")).isSelected(),
                "Confirm before closing should be checked after reload"
        );
    }

    @Test
    public void albumArtOverlayCheckboxStaysChecked() {
        new LoginPage(getDriver())
                .loginAs("daniel.vasquez@testpro.io", PASS);

        openProfile();

        // Only fill current password if the field is present
        List<WebElement> currentPass = getDriver().findElements(By.id("inputProfileCurrentPassword"));
        if (!currentPass.isEmpty()) {
            currentPass.get(0).sendKeys(PASS);
        }

        WebElement checkbox = getDriver().findElement(By.cssSelector("input[name='show_album_art_overlay']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
            getDriver().findElement(By.cssSelector("button.btn-submit")).click();

            // Wait for the real success message instead of sleeping
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Profile updated')]")));
        }

        // Refresh and re-open so we prove the setting actually persisted
        getDriver().navigate().refresh();
        openProfile();

        Assert.assertTrue(
                getDriver().findElement(By.cssSelector("input[name='show_album_art_overlay']")).isSelected(),
                "Album art overlay checkbox should stay checked after save and refresh"
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
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'Profile updated')]")));
        }

        getDriver().navigate().to("https://qa.koel.app/#!/songs");
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("tr.song-item"))).click();

        // The wait itself is the assertion
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[data-testid='album-art-overlay'], .album-art-overlay")
        ));
    }
}