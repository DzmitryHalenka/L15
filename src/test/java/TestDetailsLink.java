import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestDetailsLink {
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
    public void testDetailsLink() {
        // Открываем страницу
        driver.get("https://www.mts.by");

        // Ожидаем появления ссылки "Подробнее о сервисе"
        WebElement detailsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(), 'Подробнее о сервисе')]")));
        assertTrue(detailsLink.isDisplayed(), "Ссылка 'Подробнее о сервисе' не отображается");
        detailsLink.click();

        // Проверка, что произошёл переход на страницу с деталями
        wait.until(ExpectedConditions.urlContains("service-details"));
        assertTrue(driver.getCurrentUrl().contains("service-details"),
                "Переход на страницу с деталями не выполнен");
    }
}