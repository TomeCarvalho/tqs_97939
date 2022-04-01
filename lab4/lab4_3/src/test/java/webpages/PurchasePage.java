package webpages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PurchasePage {
    private WebDriver driver;
    private static final String PAGE_URL = "https://blazedemo.com/purchase.php";

    @FindBy(id = "inputName")
    private WebElement inputName;

    @FindBy(id = "address")
    private WebElement address;

    @FindBy(id = "city")
    private WebElement city;

    @FindBy(id = "state")
    private WebElement state;

    @FindBy(id = "zipCode")
    private WebElement zipCode;

    @FindBy(id = "cardType")
    private WebElement cardType;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumber;

    @FindBy(id = "creditCardMonth")
    private WebElement creditCardMonth;

    @FindBy(id = "creditCardYear")
    private WebElement creditCardYear;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCard;

    @FindBy(id = "rememberMe")
    private WebElement rememberMe;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseButton;

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
        PageFactory.initElements(driver, this);
    }

    public void enterName() {
        inputName.sendKeys("Geralt of Rivia");
    }

    public void enterAddress() {
        address.sendKeys("Kaer Morhen, Kaedwen");
    }

    public void enterCity() {
        city.sendKeys("Hertch");
    }

    public void enterState() {
        state.sendKeys("Hertch");
    }

    public void enterZipCode() {
        zipCode.sendKeys("12345");
    }

    public void selectCardType() {
        Select select = new Select(cardType);
        select.selectByIndex(1);
    }

    public void enterCardNumber() {
        creditCardNumber.sendKeys("9876");
    }

    public void enterMonth() {
        creditCardMonth.sendKeys("10");
    }

    public void enterYear() {
        creditCardYear.sendKeys("2026");
    }

    public void enterNameOnCard() {
        nameOnCard.sendKeys("Geralt Roger Eric du Haute-Bellegarde");
    }

    public void clickRememberMe() {
        rememberMe.click();
    }

    public void clickPurchaseFlight() {
        purchaseButton.click();
    }
}
