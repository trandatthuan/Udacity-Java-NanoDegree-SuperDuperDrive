package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static final String Username =  "johndoe777";
	private static final String FirstName = "John";
	private static final String LastName = "Doe";
	private static final String Password = "123456";
	private static final String NoteTitle = "Test Note Title";
	private static final String NoteDescription = "Test note description.";
	private static final String UpdateNoteTitle = "Test Update Title";
	private static final String UpdateNoteDesc = "Test Update Desc";
	private static final String CredentialUrl = "www.google.com";
	private static final String CredentialUsername = "jd2345";
	private static final String CredentialPassword = "jd316#23";
	private static final String UpdateCredentialUrl = "www.bing.com";
	private static final String UpdateCredentialUsername = "abc987";
	private static final String UpdateCredentialPassword = "qwert$$22";

	public static WebDriver driver;
	public static WebDriverWait wait;

	public String baseUrl;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 1000);
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() { baseUrl = "http://localhost:" + port; }

	// Test verifies that unauthorized user can only access the login and signup page
	// Test sign up a new user, log in, verifies that the home page is accessible,
	//   log out, and verify that the page is not accessible
	@Test
	public void testAuthentication() throws InterruptedException {
		// Unauthorized user should not be able to visit /home nor /result page without logging in
		driver.get(baseUrl + "/home");
		assertEquals("Login", driver.getTitle());

		driver.get(baseUrl + "/result");
		assertEquals("Login", driver.getTitle());

		// Verify unauthorized user can still access signup page
		driver.get(baseUrl + "/signup");
		assertEquals("Sign Up", driver.getTitle());

		// Sign up new user
		testSignup(FirstName, LastName, Username, Password);

        // Test login
        testLogin(Username, Password);

		// Logout...
		HomePage homePage = new HomePage(driver, wait);
		homePage.logout();
		// Verify that home page is no longer accessible
		assertEquals("Login", driver.getTitle());
	}

	// Test create note and verify it is displayed
    // Test update note and verify it is updated correctly
    // Test delete note and verify it is removed from the list
	@Test
	public void testCreateUpdateDeleteNote() throws InterruptedException {
	    // Create user
        testSignup(FirstName, LastName, Username, Password);

		// Login in...
		testLogin(Username, Password);

		// Goto note tab...
		HomePage homePage = new HomePage(driver, wait, "note");
		homePage.clickTab("nav-notes");
		Thread.sleep(1500);

		// Testing create note...
		testCreateNote(homePage);

		// Refresh...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "note");
		homePage.clickTab("nav-notes");
		Thread.sleep(1500);

		// Testing note edit...
		testEditNote(homePage);

		// Refresh...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "note");
		homePage.clickTab("nav-notes");
		Thread.sleep(1500);

		// Testing note delete...
		testDeleteNote(homePage);

		// Logout...
		homePage = new HomePage(driver, wait);
		homePage.logout();
	}

	// Test create credential and verify it is displayed
    // Test update credential and verify it is updated correctly
    // Test delete credential and verify it is removed from the list
	@Test
    public void testCreateUpdateDeleteCredential() throws InterruptedException {
        // Create user
        testSignup(FirstName, LastName, Username, Password);

        // Login in...
        testLogin(Username, Password);

        // Goto credential tab...
        HomePage homePage = new HomePage(driver, wait, "credential");
        homePage.clickTab("nav-credentials");
        Thread.sleep(1500);

		// Testing create note...
		testCreateCredential(homePage);

		// Refresh...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "credential");
		homePage.clickTab("nav-credentials");
		Thread.sleep(1500);

		// Testing note edit...
		testEditCredential(homePage);

		// Refresh...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "credential");
		homePage.clickTab("nav-credentials");
		Thread.sleep(1500);

		// Testing note delete...
		testDeleteCredential(homePage);

		// Logout...
		homePage = new HomePage(driver, wait);
		homePage.logout();
    }

	// Test signup
    public void testSignup(String firstName, String lastName, String username, String password) {
        driver.get(baseUrl + "/signup");
        SignupPage signUpPage = new SignupPage(driver, wait);
        signUpPage.createNewUser(firstName, lastName, username, password);
    }

	// Test login
    public void testLogin(String username, String password) throws InterruptedException {
        // Login in...
        driver.get(baseUrl + "/login");
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.loginUser(username, password);
        Thread.sleep(1500);

        // Verifies that the home page is accessible
        assertEquals("Home", driver.getTitle());
    }

	// Test create note and verify it is displayed
	public void testCreateNote(HomePage homePage) throws InterruptedException {
		// Open add note modal...
		homePage.openModal("note");
		Thread.sleep(1500);

		// Create new note...
		homePage.saveNote(NoteTitle, NoteDescription);

		// Navigate back to home page and note tab after creating new note...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "note");
		Thread.sleep(1500);
		homePage.clickTab("nav-notes");
		Thread.sleep(1500);

		// Check if table has the new note added
		List<WebElement> tbodyRows = wait.until(webDriver -> webDriver.findElement(By.id("noteTable")))
				.findElement(By.tagName("tbody"))
				.findElements(By.tagName("tr"));
		assertTrue(tbodyRows.size() > 0);

		// Compare note title and description on the table
		WebElement row = wait.until(webDriver -> webDriver.findElement(By.id("noteTable")))
				.findElement(By.tagName("tbody"))
				.findElement(By.tagName("tr"));
		assertEquals(NoteTitle, row.findElement(By.tagName("th")).getText());
		assertEquals(NoteDescription, row.findElements(By.tagName("td")).get(1).getText());

		// View note...
		homePage.viewNote();
		Thread.sleep(1500);

		// Compare note title and description in the modal
		WebElement noteTitleField = wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
		WebElement noteDescriptionField = wait.until(webDriver -> webDriver.findElement(By.id("note-description")));
		Thread.sleep(1500);
		assertEquals(NoteTitle, noteTitleField.getAttribute("value"));
		assertEquals(NoteDescription, noteDescriptionField.getAttribute("value"));

		// Close modal...
		homePage.closeModal("note");
		Thread.sleep(1500);
	}

	// Test note edit and verify that the changes are displayed
	public void testEditNote(HomePage homePage) throws InterruptedException {
		// View note
		homePage.viewNote();
		Thread.sleep(1500);

		// Update note
		homePage.saveNote(UpdateNoteTitle, UpdateNoteDesc);
		Thread.sleep(1500);

		// Navigate back to home page and note tab after updating new note...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "note");
		Thread.sleep(1500);
		homePage.clickTab("nav-notes");
		Thread.sleep(1500);

		// Compare note title and description on the table
		WebElement row = wait.until(webDriver -> webDriver.findElement(By.id("noteTable")))
				.findElement(By.tagName("tbody"))
				.findElement(By.tagName("tr"));
		assertEquals(UpdateNoteTitle, row.findElement(By.tagName("th")).getText());
		assertEquals(UpdateNoteDesc, row.findElements(By.tagName("td")).get(1).getText());

		// View note...
		homePage.viewNote();
		Thread.sleep(1500);

		// Compare note title and description in the modal
		WebElement noteTitleField = wait.until(webDriver -> webDriver.findElement(By.id("note-title")));
		WebElement noteDescriptionField = wait.until(webDriver -> webDriver.findElement(By.id("note-description")));
		Thread.sleep(1500);
		assertEquals(UpdateNoteTitle, noteTitleField.getAttribute("value"));
		assertEquals(UpdateNoteDesc, noteDescriptionField.getAttribute("value"));

		// Close modal...
		homePage.closeModal("note");
		Thread.sleep(1500);
	}

	// Test delete note and verify that the note is no longer displayed.
	public void testDeleteNote(HomePage homePage) throws InterruptedException {
		homePage.deleteNote();

		// Return to main page
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "note");
		homePage.clickTab("nav-notes");
		Thread.sleep(1500);

		// There should not be any note exist in the table
		// Check if table has the new note added
		List<WebElement> tbodyRows = wait.until(webDriver -> webDriver.findElement(By.id("noteTable")))
				.findElement(By.tagName("tbody"))
				.findElements(By.tagName("tr"));
		assertTrue(tbodyRows.size() == 0);
	}

	// Test create credential and verify it is displayed
    public void testCreateCredential(HomePage homePage) throws InterruptedException {
		// Open add credential modal...
		homePage.openModal("credential");
		Thread.sleep(1500);

		// Create new credential...
		homePage.saveCredential(CredentialUrl, CredentialUsername, CredentialPassword);

		// Navigate back to home page and credential tab after creating new credential...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "credential");
		Thread.sleep(1500);
		homePage.clickTab("nav-credentials");
		Thread.sleep(1500);

		// Check if table has the new credential added
		List<WebElement> tbodyRows = wait.until(webDriver -> webDriver.findElement(By.id("credentialTable")))
				.findElement(By.tagName("tbody"))
				.findElements(By.tagName("tr"));
		assertTrue(tbodyRows.size() > 0);

		// Compare credential url and username on the table
		WebElement row = wait.until(webDriver -> webDriver.findElement(By.id("credentialTable")))
				.findElement(By.tagName("tbody"))
				.findElement(By.tagName("tr"));
		assertEquals(CredentialUrl, row.findElement(By.tagName("th")).getText());
		assertEquals(CredentialUsername, row.findElements(By.tagName("td")).get(1).getText());

		// Verify password is encrypted
		assertNotEquals(CredentialPassword, row.findElements(By.tagName("td")).get(2).getText());

		// View credential...
		homePage.viewCredential();
		Thread.sleep(1500);

		// Compare credential url, username, and password in the modal
		WebElement credentialUrlField = wait.until(webDriver -> webDriver.findElement(By.id("credential-url")));
		WebElement credentialUsernameField = wait.until(webDriver -> webDriver.findElement(By.id("credential-username")));
		WebElement credentialPasswordField = wait.until(webDriver -> webDriver.findElement(By.id("credential-password")));
		Thread.sleep(1500);
		assertEquals(CredentialUrl, credentialUrlField.getAttribute("value"));
		assertEquals(CredentialUsername, credentialUsernameField.getAttribute("value"));
		assertEquals(CredentialPassword, credentialPasswordField.getAttribute("value"));

		// Close modal...
		homePage.closeModal("credential");
		Thread.sleep(1500);
    }

    // Test edit credential and verify that the changes are displayed
    public void testEditCredential(HomePage homePage) throws InterruptedException {
		// View credential
		homePage.viewCredential();
		Thread.sleep(1500);

		// Update note
		homePage.saveCredential(UpdateCredentialUrl, UpdateCredentialUsername, UpdateCredentialPassword);
		Thread.sleep(1500);

		// Navigate back to home page and credential tab after updating new credential...
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "credential");
		Thread.sleep(1500);
		homePage.clickTab("nav-credentials");
		Thread.sleep(1500);

		// Compare credential url and username on the table
		WebElement row = wait.until(webDriver -> webDriver.findElement(By.id("credentialTable")))
				.findElement(By.tagName("tbody"))
				.findElement(By.tagName("tr"));
		assertEquals(UpdateCredentialUrl, row.findElement(By.tagName("th")).getText());
		assertEquals(UpdateCredentialUsername, row.findElements(By.tagName("td")).get(1).getText());

		// Verify password is encrypted
		assertNotEquals(UpdateCredentialPassword, row.findElements(By.tagName("td")).get(2).getText());

		// View credential...
		homePage.viewCredential();
		Thread.sleep(1500);

		// Compare credential url, username, and password in the modal
		WebElement credentialUrlField = wait.until(webDriver -> webDriver.findElement(By.id("credential-url")));
		WebElement credentialUsernameField = wait.until(webDriver -> webDriver.findElement(By.id("credential-username")));
		WebElement credentialPasswordField = wait.until(webDriver -> webDriver.findElement(By.id("credential-password")));
		Thread.sleep(1500);
		assertEquals(UpdateCredentialUrl, credentialUrlField.getAttribute("value"));
		assertEquals(UpdateCredentialUsername, credentialUsernameField.getAttribute("value"));
		assertEquals(UpdateCredentialPassword, credentialPasswordField.getAttribute("value"));

		// Close modal...
		homePage.closeModal("credential");
		Thread.sleep(1500);
	}

    // Test delete credential and verify that it is no longer displayed
    public void testDeleteCredential(HomePage homePage) throws InterruptedException {
		homePage.deleteCredential();

		// Return to main page
		driver.get(baseUrl + "/home");
		homePage = new HomePage(driver, wait, "credential");
		homePage.clickTab("nav-credentials");
		Thread.sleep(1500);

		// There should not be any note exist in the table
		// Check if table has the new note added
		List<WebElement> tbodyRows = wait.until(webDriver -> webDriver.findElement(By.id("credentialTable")))
				.findElement(By.tagName("tbody"))
				.findElements(By.tagName("tr"));
		assertTrue(tbodyRows.size() == 0);
	}
}
