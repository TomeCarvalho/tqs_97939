package tqs.n97939.covid;

import io.github.bonigarcia.seljup.Browser;
import io.github.bonigarcia.seljup.EnabledIfBrowserAvailable;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@EnabledIfBrowserAvailable(Browser.FIREFOX)
@ExtendWith(SeleniumJupiter.class)
public class USAStatistics20210617Test {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;

    @BeforeEach
    public void setUp() {
        driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;
        vars = new HashMap<String, Object>();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void uSAStatistics20210617() {
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
        driver.findElement(By.id("autocomplete-countries-option-222")).click();
        driver.findElement(By.id(":r5:")).click();
        driver.findElement(By.cssSelector(".css-fd2y78-MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".PrivatePickersYear-root:nth-child(122) > .PrivatePickersYear-yearButton")).click();
        js.executeScript("window.scrollTo(0,0)");
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".MuiIconButton-edgeStart > .MuiSvgIcon-root")).click();
        driver.findElement(By.cssSelector(".css-mvmu1r:nth-child(3) > div:nth-child(5) > .MuiButtonBase-root")).click();
        driver.findElement(By.cssSelector(".MuiButton-root:nth-child(2)")).click();
    }
}
