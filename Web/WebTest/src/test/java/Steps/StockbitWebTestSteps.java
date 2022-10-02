package Steps;

import Models.SearchedBookDto;
import Pages.pages;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class StockbitWebTestSteps {
    private static WebDriver driver;
    public final static int TIMEOUT = 10;
    public List<String> colour = new ArrayList<String>();
    public List<String> expectedColour = new ArrayList<String>();
    public SearchedBookDto searchedBookDto;
    public String val;
    public String selOne;
    public String oldStyleSelected;

    public StockbitWebTestSteps() {
        // Load web setting
        //settings = TestHelper.Settings(AppSettings);

        Properties prop = new Properties();
        prop.setProperty("log4j.rootLogger", "WARN");
        PropertyConfigurator.configure(prop);

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        options.addArguments("window-size=1920,1080");
        options.addArguments("start-maximized");
        options.addArguments("disable-gpu");
        options.addArguments("disable-extensions");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        driver.manage().window().maximize();

        searchedBookDto = new SearchedBookDto();
        oldStyleSelected = null;
    }

    @After
    public void TearDown(){
        driver.quit();
    }

    @Given("User go to {string}")
    public void user_go_to(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(1000);
    }

    @When("User in {string} page")
    public void user_in_page(String pageTitle) {
        driver.findElement(pages.PAGE_TITLE).getText();
        Assert.assertEquals(pageTitle,driver.findElement(pages.PAGE_TITLE).getText());

    }

    @When("User choose select value {string}")
    public void user_choose_select_value(String val) throws InterruptedException {
        this.val = val;
        Thread.sleep(1000);
        WebElement selectMyElement = driver.findElement(pages.SELECT_VALUE);
        selectMyElement.click();

        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(val)).perform();
        keyDown.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        Assert.assertEquals(val,driver.findElement(pages.SELECT_VALUE).getText());
    }

    @When("User choose select one {string}")
    public void user_choose_select_one(String selOne) throws InterruptedException {
        this.selOne = selOne;
        Thread.sleep(1000);

        WebElement selectMyElement = driver.findElement(pages.SELECT_ONE);
        selectMyElement.click();

        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(selOne)).perform();
        keyDown.sendKeys(Keys.ENTER).perform();
        Thread.sleep(1000);
        Assert.assertEquals(selOne,driver.findElement(pages.SELECT_ONE).getText());
    }

    @When("User choose old style select menu {string}")
    public void user_choose_old_style_select_menu(String colour) {
        Select selCol = new Select(driver.findElement(pages.SELECT_OLD_STYLE));
        selCol.selectByVisibleText(colour);
        oldStyleSelected = selCol.getFirstSelectedOption().getText();
    }

    @When("User choose multi select drop down all color")
    public void user_choose_multi_select_drop_down() throws InterruptedException {
        colour.add("Green");
        colour.add("Blue");
        colour.add("Black");
        colour.add("Red");

        WebElement selectMyElement = driver.findElement(pages.MULTISELECT_DROPDOWN);
        selectMyElement.click();
        Actions keyDown = new Actions(driver);
        for(String col : colour){
            keyDown.sendKeys(Keys.chord(col)).perform();
            keyDown.sendKeys(Keys.ENTER).perform();
        }
        keyDown.sendKeys(Keys.ESCAPE).perform();
        Thread.sleep(1000);

        String retColour = null;
        List<WebElement> webElements = driver.findElements(pages.MULTISELECT_DROPDOWN);
        for(WebElement we : webElements){
            retColour = we.getText();
        }
        System.out.println(retColour);
        expectedColour = Arrays.asList(retColour.split("\\n"));
    }

    @Then("User success input all select menu")
    public void user_success_input_all_select_menu() {
        // Select value
        Assert.assertEquals(val,driver.findElement(pages.SELECT_VALUE).getText());
        // Select One other
        Assert.assertEquals(selOne,driver.findElement(pages.SELECT_ONE).getText());
        // Select old Style
        Assert.assertEquals(oldStyleSelected, "Aqua");
        //Multi Select Assertion
        Assert.assertEquals(expectedColour.size(), colour.size());
    }

    @And("User search book {string}")
    public void userSearchBookQaEngineer(String book) throws InterruptedException {
        driver.findElement(pages.SEARCH_BOX_BOOK).click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(book).perform();
        keyDown.sendKeys(Keys.ESCAPE).perform();
        Thread.sleep(1000);
    }

    @Then("User see {string}")
    public void userSeeNoRowsFound(String noRows) {
        Assert.assertEquals(noRows, driver.findElement(pages.NO_ROWS_SEARCH).getText());
    }

    @And("User click book {string}")
    public void userClickBookGitPocketGuide(String title) throws InterruptedException {
        List<WebElement> we = driver.findElements(pages.SEARCH_BOOK_RESULT);
        String searchRslt = null;
        for(WebElement val : we){
            searchRslt = val.getText();
        }
        List<String>searched = Arrays.asList(searchRslt.split("\\n"));
        searchedBookDto.setTitle(title);
        searchedBookDto.setAuthor(searched.get(1));
        searchedBookDto.setPublisher(searched.get(2));

        if(driver.findElement(pages.BOOK_LINK).getText().contains(title)){
            driver.findElement(pages.BOOK_LINK).click();
        }
        Thread.sleep(1000);
    }

    @Then("User see {string} details")
    public void userSeeGitPocketGuideDetails(String bookTitle) {
        Assert.assertEquals(driver.findElement(pages.BOOK_TITLE).getText(),bookTitle);
        Assert.assertEquals(driver.findElement(pages.BOOK_AUTHOR).getText(), searchedBookDto.getAuthor());
        Assert.assertEquals(driver.findElement(pages.BOOK_PUBLISHER).getText(), searchedBookDto.getPublisher());
    }
}
