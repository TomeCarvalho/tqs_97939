package tqs.n97939.covid;

import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class StatisticsSearchSteps {
    private FirefoxDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;


    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime iso8601Date(String year, String month, String day) {
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
    }

    @Given("the user opens Firefox and navigates to the application")
    public void open_firefox_navigate_to_app() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);
        js = driver;
        driver.get("http://localhost:3000/");
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


    @When("the user searches for statistics for the country USA on the date 2021-06-17")
    public void theUserSearchesForStatisticsForTheCountryOnTheDate() {
        WebElement usa = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("autocomplete-countries-option-222")));
        usa.click();
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
