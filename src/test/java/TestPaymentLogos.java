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

public class TestPaymentLogos {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(TestPaymentLogos.class); // Логгер

    @Before
    public void setUp() {
        // Автоматическая настройка chromedriver с помощью WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Инициализация драйвера
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.manage().window().maximize();
        logger.info("Браузер запущен и настроен"); // Логируем запуск браузера
    }

    @After
    public void tearDown() {
        // Закрываем браузер после завершения теста
        if (driver != null) {
            driver.quit();
            logger.info("Браузер закрыт"); // Логируем закрытие браузера
        }
    }

    @Test
    public void testPaymentLogos() {
        // Открываем страницу
        driver.get("https://www.mts.by");
        logger.info("Открыта страница: https://www.mts.by"); // Логируем открытие страницы
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

        // Проверяем логотип Visa
        WebElement visaLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[1]")));
        assertTrue(visaLogo.isDisplayed(), "Логотип Visa не отображается");
        logger.info("Логотип Visa отображается"); // Логируем успешное отображение логотипа Visa

        // Проверяем логотип MasterCard
        WebElement mastercardLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[3]")));
        assertTrue(mastercardLogo.isDisplayed(), "Логотип MasterCard не отображается");
        logger.info("Логотип MasterCard отображается"); // Логируем успешное отображение логотипа MasterCard

        // Проверяем логотип Белкарт
        WebElement belcardLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/div[2]/ul/li[5]")));
        assertTrue(belcardLogo.isDisplayed(), "Логотип Белкарт не отображается");
        logger.info("Логотип Белкарт отображается"); // Логируем успешное отображение логотипа Белкарт

        // Логируем успешное завершение теста
        logger.info("Тест testPaymentLogos завершен успешно");
    }
}