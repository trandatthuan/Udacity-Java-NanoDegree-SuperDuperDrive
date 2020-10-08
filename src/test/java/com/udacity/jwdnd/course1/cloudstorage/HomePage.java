package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;

public class HomePage {
    private WebElement logoutButton;
    //Tab
    private List<WebElement> tabs;
    // Note
    private WebElement showNoteModalButton;
    private WebElement closeNoteModalButton;
    private WebElement noteTitleField;
    private WebElement noteDescriptionField;
    private WebElement saveNoteButton;
    private List<WebElement> noteTableRows;
    // Credential
    private WebElement showCredentialModalButton;
    private WebElement closeNoteCredentialModalButton;
    private WebElement credentialUrlField;
    private WebElement credentialUsernameField;
    private WebElement credentialPasswordField;
    private WebElement saveCredentialButton;
    private List<WebElement> credentialTableRows;


    public HomePage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);

        this.logoutButton = wait.until(webDriver -> webDriver.findElement(By.id("logout-button")));
    }

    public HomePage(WebDriver driver, WebDriverWait wait, String initTab) {
        this.logoutButton = wait.until(webDriver -> webDriver.findElement(By.id("logout-button")));
        this.tabs = wait.until(webDriver -> webDriver.findElement(By.id("nav-tab")).findElements(By.tagName("a")));

        if (initTab == "note") {
            this.showNoteModalButton = wait.until(webDriver -> webDriver.findElement(By.id("showNoteModal-button")));
            this.closeNoteModalButton = wait.until(webDriver -> webDriver.findElement(By.id("closeNoteModal-button")));
            this.saveNoteButton = wait.until(webDriver -> webDriver.findElement(By.id("saveNote-button")));
            this.noteTitleField = wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
            this.noteDescriptionField = wait.until(webDriver -> webDriver.findElement(By.id("note-description")));
            this.noteTableRows = wait.until(webDriver -> webDriver.findElement(By.id("noteTable")))
                                                            .findElement(By.tagName("tbody"))
                                                            .findElements(By.tagName("tr"));
        } else {
            this.showCredentialModalButton = wait.until(webDriver -> webDriver.findElement(By.id("showCredentialModal-btn")));
            this.closeNoteCredentialModalButton = wait.until(webDriver -> webDriver.findElement(By.id("closeCredentialModal-btn")));
            this.saveCredentialButton = wait.until(webDriver -> webDriver.findElement(By.id("saveCredentialModal-btn")));
            this.credentialUrlField = wait.until(webDriver -> webDriver.findElement(By.id("credential-url")));
            this.credentialUsernameField = wait.until(webDriver -> webDriver.findElement(By.id("credential-username")));
            this.credentialPasswordField = wait.until(webDriver -> webDriver.findElement(By.id("credential-password")));
            this.credentialTableRows = wait.until(webDriver -> webDriver.findElement(By.id("credentialTable")))
                                                                    .findElement(By.tagName("tbody"))
                                                                    .findElements(By.tagName("tr"));
        }
    }

    public void logout() { logoutButton.click(); }

    public void clickTab(String href) {
        Iterator<WebElement> iterator = tabs.iterator();

        while(iterator.hasNext()) {
            WebElement tab = iterator.next();
            if (tab.getAttribute("href").contains(href)) {
                tab.click();
                break;
            }
        }
    }

    public void openModal(String tabName) {
        if (tabName == "note") {
            showNoteModalButton.click();
        } else {
            showCredentialModalButton.click();
        }
    }

    public void closeModal(String tabName) {
        if (tabName == "note") {
            closeNoteModalButton.click();
        } else {
            closeNoteCredentialModalButton.click();
        }
    }

    public void saveNote(String title, String description) {
        noteTitleField.clear();
        noteTitleField.sendKeys(title);
        noteDescriptionField.clear();
        noteDescriptionField.sendKeys(description);
        saveNoteButton.click();
    }

    public void viewNote() {
        noteTableRows.get(0).findElement(By.tagName("td")).findElement(By.tagName("button")).click();
    }

    public void deleteNote() {
        noteTableRows.get(0).findElement(By.tagName("td")).findElement(By.tagName("a")).click();
    }

    public void saveCredential(String url, String username, String password) {
        credentialUrlField.clear();
        credentialUrlField.sendKeys(url);
        credentialUsernameField.clear();
        credentialUsernameField.sendKeys(username);
        credentialPasswordField.clear();
        credentialPasswordField.sendKeys(password);
        saveCredentialButton.click();
    }

    public void viewCredential() {
        credentialTableRows.get(0).findElement(By.tagName("td")).findElement(By.tagName("button")).click();
    }

    public void deleteCredential() {
        credentialTableRows.get(0).findElement(By.tagName("td")).findElement(By.tagName("a")).click();
    }

}
