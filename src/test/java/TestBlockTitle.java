import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestBlockTitle {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Укажите путь к chromedriver
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
    public void testBlockTitle() {
        // Открываем страницу
        driver.get("https://www.mts.by");

        // Ожидаем появления заголовка блока
        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2") // Упрощенный XPath
        ));
        assertTrue(blockTitle.isDisplayed(), "Заголовок блока не отображается");

        // Проверяем текст заголовка
        String expectedText = "Онлайн пополнение без комиссии";
        String actualText = blockTitle.getText().replace("\n", " "); // Убираем перенос строки
        assertEquals(expectedText, actualText, "Текст заголовка не соответствует ожидаемому");
    }
}