package tqs.n97939.covid;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class StatisticsSearchSteps {
    private FirefoxDriver driver;
    private JavascriptExecutor js;
    private Map<String, Object> vars;

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime iso8601Date(String year, String month, String day) {
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
    }

    @Given("the user opens Firefox and navigates to the application")
    public void open_firefox_navigate_to_app() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
        vars = new HashMap<String, Object>();
        driver.get("http://localhost:3000/");
        driver.manage().window().setSize(new Dimension(810, 824));
        {
            WebElement element = driver.findElement(By.cssSelector(".MuiSvgIcon-fontSizeMedium > path"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element).perform();
        }
        driver.findElement(By.cssSelector(".MuiSvgIcon-fontSizeMedium > path")).click();
        {
            WebElement element = driver.findElement(By.tagName("body"));
            Actions builder = new Actions(driver);
            builder.moveToElement(element, 0, 0).perform();
        }
    }


    @When("the user searches for statistics for the country {string} on the date {iso8601Date}")
    public void theUserSearchesForStatisticsForTheCountryOnTheDate(String country, LocalDateTime date) {
        driver.findElement(By.id("autocomplete-countries-option-0")).click();
        driver.findElement(By.id("autocomplete-countries")).sendKeys(country);
        driver.findElement(By.id(":r5:")).click();
        driver.findElement(By.cssSelector(".css-fd2y78-MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".PrivatePickersYear-root:nth-child(122) > .PrivatePickersYear-yearButton")).click();
        js.executeScript("window.scrollTo(0,0)");
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".css-mvmu1r:nth-child(3) > div:nth-child(5) > .MuiButtonBase-root")).click();
        driver.findElement(By.cssSelector(".MuiButton-root:nth-child(2)")).click();
    }

    @Then("the table should show {string} new cases")
    public void theTableShouldShowNewCases(String newCases) {
        String res = driver.findElement(By.cssSelector(".MuiTableRow-root:nth-child(1) > .MuiTableCell-body:nth-child(2)")).getText();
        assertThat(res, equalTo(newCases));
    }

    @And("the table should show {string} new deaths")
    public void theTableShouldShowNewDeaths(String newDeaths) {
        String res = driver.findElement(By.cssSelector(".MuiTableRow-root:nth-child(1) > .MuiTableCell-body:nth-child(3)")).getText();
        assertThat(res, equalTo(newDeaths));
    }
}
