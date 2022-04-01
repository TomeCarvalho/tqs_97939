package tests;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import webpages.HomePage;
import webpages.PurchasePage;
import webpages.ReservePage;

@ExtendWith(SeleniumJupiter.class)
public class FlightTest {
    WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new HtmlUnitDriver();
    }

    @Test
    public void flightTest() {
        HomePage home = new HomePage(driver);
        home.selectDepartureBoston();
        home.selectDestinationLondon();
        home.clickFindFlights();

        ReservePage reservePage = new ReservePage(driver);
        reservePage.clickChooseFlight();

        PurchasePage purchasePage = new PurchasePage(driver);
        purchasePage.enterName();
        purchasePage.enterAddress();
        purchasePage.enterCity();
        purchasePage.enterState();
        purchasePage.enterZipCode();
        purchasePage.selectCardType();
        purchasePage.enterCardNumber();
        purchasePage.enterMonth();
        purchasePage.enterYear();
        purchasePage.enterNameOnCard();
        purchasePage.clickRememberMe();
        purchasePage.clickPurchaseFlight();
    }
}
