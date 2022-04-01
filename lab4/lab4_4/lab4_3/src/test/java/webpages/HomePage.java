package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    private WebDriver driver;
    private static final String PAGE_URL = "https://blazedemo.com/";

    @FindBy(name = "fromPort")
    private WebElement fromPortDropdown;

    @FindBy(name = "toPort")
    private WebElement toPortDropdown;

    @FindBy(className = "btn-primary")
    private WebElement findFlightsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void selectDepartureBoston() {
        Select select = new Select(fromPortDropdown);
        select.selectByValue("Boston");
    }

    public void selectDestinationLondon() {
        Select select = new Select(toPortDropdown);
        select.selectByValue("London");
    }

    public void clickFindFlights() {
        findFlightsButton.click();
    }
}
