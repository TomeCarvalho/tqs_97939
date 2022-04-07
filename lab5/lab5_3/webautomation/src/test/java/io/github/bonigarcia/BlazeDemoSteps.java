package io.github.bonigarcia;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class BlazeDemoSteps {
    private WebDriver driver;

    @When("I navigate to {string}")
    public void iNavigateTo(String url) {
        driver = WebDriverManager.firefoxdriver().create();
        driver.get(url);
    }

    @And("I select a flight from {string} to {string}")
    public void selectFlight(String from, String to) {
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath(String.format("//option[. = '%s']", from))).click();
        }
        driver.findElement(By.cssSelector(".form-inline:nth-child(1) > option:nth-child(3)")).click();
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath(String.format("//option[. = '%s']", to))).click();
        }
    }

    @And("I click Find Flights")
    public void clickFindFlights() {
        driver.findElement(By.cssSelector(".form-inline:nth-child(4) > option:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @And("I click Choose This Flight")
    public void iClickChooseThisFlight() {
        driver.findElement(By.cssSelector("tr:nth-child(3) .btn")).click();
    }


    @And("I write {string} as my {string}")
    public void iWriteAsMy(String val, String id) {
        driver.findElement(By.id(id)).click();
        driver.findElement(By.id(id)).sendKeys(val);
    }

    @And("I select {string} as my {string}")
    public void iSelectAsMy(String arg0, String arg1) {
        driver.findElement(By.cssSelector(".control-group:nth-child(9) > .control-label")).click();
    }


    @And("I click Purchase Flight")
    public void iClickPurchaseFlight() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    @Then("I should see my receipt")
    public void iShouldSeeMyReceipt() {
        assertThat(driver.getTitle(), is("BlazeDemo Confirmation"));
    }
}
