import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestPaymentLogos {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPaymentLogos() {
        // Открываем страницу
        driver.get("https://www.mts.by");

        // Ожидаем появления логотипа Visa
        WebElement visaLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//img[contains(@alt, 'Visa')]")));
        assertTrue(visaLogo.isDisplayed(), "Логотип Visa не отображается");

        // Ожидаем появления логотипа MasterCard
        WebElement mastercardLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//img[contains(@alt, 'MasterCard')]")));
        assertTrue(mastercardLogo.isDisplayed(), "Логотип MasterCard не отображается");
    }
}