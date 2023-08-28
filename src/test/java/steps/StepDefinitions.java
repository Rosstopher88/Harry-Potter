package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepDefinitions {

    private WebDriver driver;

    @Given("Navigate to amazon.co.uk")
    public void NavigateToAmazon() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\rosen.kostadinov\\Documents\\ChromeDriver\\chrome-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);
        driver.get("https://www.amazon.co.uk/");
    }

    @When("Click on Privacy Notice")
    public void clickOnPrivacyNotice() {
        driver.findElement(By.linkText("Privacy Notice")).click();
    }

    @And("Accept cookies")
    public void acceptCookies() {
        driver.findElement(By.id("a-autoid-0")).click();
    }


    @When("Search for {string} in the Books section")
    public void SearchForBook(String bookTitle) throws InterruptedException {
        Thread.sleep(1000);
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys(bookTitle);
        driver.findElement(By.cssSelector("input.nav-input[value='Go']")).click();
    }

    @Then("Verify the first item title is {string}")
    public void VerifyFirstItemTitle(String expectedTitle) {
        WebElement firstItemTitle = driver.findElement(By.cssSelector(".s-result-list h2 span"));
        assertEquals(expectedTitle, firstItemTitle.getText());
    }

    @And("Verify if the first item has a badge")
    public void VerifyBadge() {
        WebElement badgeElement = driver.findElement(By.cssSelector(".s-result-list .a-badge"));
        assertTrue(badgeElement.isDisplayed());
    }

    @When("Click on the first book")
    public void ClickOnFirstBook() {
        WebElement firstBookLink = driver.findElement(By.partialLinkText("Harry Potter and the Cursed Child - Parts One and Two: The Official Playscript of the Original West End Production"));
        firstBookLink.click();
    }

    @And("Verify the selected type and price")
    public void VerifyTypeAndPrice() {
        WebElement typeAndPriceElement = driver.findElement(By.id("a-autoid-6"));
        String typeAndPriceText = typeAndPriceElement.getText();
        String expectedType = "Paperback";
        assertTrue(typeAndPriceText.contains(expectedType));
        System.out.println("Type and Price: " + typeAndPriceText);
    }


    @Then("Verify the title, badge, price, and type")
    public void VerifyDetailsOnBookPage() {
        WebElement titleElement = driver.findElement(By.id("productTitle"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement priceElement = driver.findElement(By.cssSelector(".a-price .a-offscreen"));
        WebElement typeElement = driver.findElement(By.xpath("//th[contains(text(),'Format')]/following-sibling::td/span"));
        assertEquals("Harry Potter and the Cursed Child - Parts One & Two", titleElement.getText());
        assertTrue(priceElement.getText().startsWith("£"));
        assertEquals("Paperback", typeElement.getText());
    }

    @And("Add the book to the basket")
    public void AddBookToBasket() {
        WebElement addToCartButton = driver.findElement(By.id("add-to-cart-button"));
        addToCartButton.click();
    }

    @Then("Verify the notification {string}")
    public void VerifyNotification(String expectedNotification) {
        WebElement notificationElement = driver.findElement(By.id("NATC_SMART_WAGON_CONF_MSG_SUCCESS"));
        assertEquals(expectedNotification, notificationElement.getText());
    }

    @And("there is one item in the basket")
    public void thereIsOneItemInTheBasket() {
        WebElement basketCountElement = driver.findElement(By.id("nav-cart-count"));
        assertEquals("1", basketCountElement.getText());
    }

    @When("Open Basket")
    public void openBasket() {
        WebElement basket = driver.findElement(By.id("nav-cart-count"));
        basket.click();
    }

    @Then("Verify the book details in the basket")
    public void VerifyBookDetailsInBasket() {
        WebDriverWait wait = new WebDriverWait(driver, 10); // Wait for up to 10 seconds
        WebElement basketItemTitle = driver.findElement(By.cssSelector(".a-truncate-cut"));
        WebElement basketItemType = driver.findElement(By.cssSelector(".sc-list-item-content .sc-product-binding"));
        wait.until(ExpectedConditions.visibilityOf(basketItemTitle));
        wait.until(ExpectedConditions.visibilityOf(basketItemType));
        String expectedTitlePartial = "Harry Potter and the Cursed Child - Parts One and Two";
        String expectedType = "Paperback";
        String actualTitle = basketItemTitle.getText().trim();
        String actualType = basketItemType.getText().trim();
        assertTrue(actualTitle.contains(expectedTitlePartial));
        assertEquals(expectedType, actualType);

    }

    public class Hooks {

        private WebDriver driver;

        @After
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
