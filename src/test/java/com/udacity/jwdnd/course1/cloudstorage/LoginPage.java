package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebElement usernameField;
    private WebElement passwordField;
    private WebElement signInButton;

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.usernameField = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        this.passwordField = wait.until(webDriver -> webDriver.findElement(By.id("inputPassword")));
        this.signInButton = wait.until(webDriver -> webDriver.findElement(By.id("signin-button")));
    }

    public void loginUser(String username, String password) {
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        signInButton.click();
    }
}
