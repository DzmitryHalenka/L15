import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestBlockTitle {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(TestBlockTitle.class); // Логгер

    @Before
    public void setUp() {
        // Автоматическая настройка chromedriver с помощью WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Инициализация драйвера
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        // Закрываем браузер после завершения теста
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBlockTitle() {
        // Открываем страницу
        driver.get("https://www.mts.by");

        // Принимаем куки, если окно появилось
        try {
            // Ожидаем появления окна с куки
            WebElement cookieBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//div[@class='cookie show']")));
            logger.info("Окно с куки отображается"); // Логируем отображение окна с куки

            // Ожидаем появления кнопки "Принять куки" и кликаем по ней
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//*[@id=\"cookie-agree\"]")));
            acceptCookiesButton.click();
            logger.info("Куки приняты"); // Логируем успешное принятие куки

            // Ожидаем, пока окно с куки исчезнет
            wait.until(ExpectedConditions.invisibilityOf(cookieBlock));
            logger.info("Окно с куки исчезло"); // Логируем исчезновение окна с куки
        } catch (TimeoutException e) {
            logger.info("Окно с куки не появилось или не удалось найти элемент"); // Логируем, если окно с куки не появилось
        }

        // Ожидаем появления заголовка блока
        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/h2") // Упрощенный XPath
        ));
        assertTrue(blockTitle.isDisplayed(), "Заголовок блока не отображается");

        // Проверяем текст заголовка
        String expectedText = "Онлайн пополнение без комиссии";
        String actualText = blockTitle.getText().replace("\n", " "); // Убираем перенос строки

        // Логируем ожидаемый и фактический текст
        logger.info("Ожидаемый текст заголовка: {}", expectedText);
        logger.info("Фактический текст заголовка: {}", actualText);

        // Проверяем соответствие текста
        assertEquals(expectedText, actualText, "Текст заголовка не соответствует ожидаемому");

        // Логируем успешное завершение проверки
        logger.info("Проверка заголовка блока завершена успешно: текст соответствует ожидаемому");
    }
}