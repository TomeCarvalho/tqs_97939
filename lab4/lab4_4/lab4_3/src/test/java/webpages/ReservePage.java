package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

public class ReservePage {
    private WebDriver driver;
    private static final String PAGE_URL = "https://blazedemo.com/reserve.php";

    public ReservePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "tr:nth-child(3) .btn")
    private WebElement chooseFlightButton;

    public void clickChooseFlight() {
        chooseFlightButton.click();
    }
}
