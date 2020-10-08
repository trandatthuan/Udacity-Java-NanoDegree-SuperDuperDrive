package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignupPage {
    private WebElement firstNameField;
    private WebElement lastNameField;
    private WebElement usernameField;
    private WebElement passwordField;
    private WebElement registerButton;

    public SignupPage(WebDriver driver, WebDriverWait wait) {
        PageFactory.initElements(driver, this);
        this.firstNameField = wait.until(webDriver -> webDriver.findElement(By.id("inputFirstName")));
        this.lastNameField = wait.until(webDriver -> webDriver.findElement(By.id("inputLastName")));
        this.usernameField = wait.until(webDriver -> webDriver.findElement(By.id("inputUsername")));
        this.passwordField = wait.until(webDriver -> webDriver.findElement(By.id("inputPassword")));
        this.registerButton = wait.until(webDriver -> webDriver.findElement(By.id("registerButton")));
    }

    public void createNewUser(String firstName, String lastName, String username, String password) {
        firstNameField.sendKeys(firstName);
        lastNameField.sendKeys(lastName);
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);
        registerButton.click();
    }

}
