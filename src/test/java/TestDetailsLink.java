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

public class TestDetailsLink {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(TestDetailsLink.class); // Логгер

    @Before
    public void setUp() {
        // Автоматическая настройка chromedriver с помощью WebDriverManager
        WebDriverManager.chromedriver().setup();

        // Инициализация драйвера
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Увеличенное время ожидания
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
    public void testDetailsLink() {
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
        // Ожидаем появления ссылки "Подробнее о сервисе"
        WebElement detailsLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[@id=\"pay-section\"]/div/div/div[2]/section/div/a")));
        assertTrue(detailsLink.isDisplayed(), "Ссылка 'Подробнее о сервисе' не отображается");
        logger.info("Ссылка 'Подробнее о сервисе' отображается"); // Логируем отображение ссылки

        // Проверяем текст ссылки (опционально)
        String expectedLinkText = "Подробнее о сервисе";
        String actualLinkText = detailsLink.getText();
        assertEquals(expectedLinkText, actualLinkText, "Текст ссылки не соответствует ожидаемому");
        logger.info("Текст ссылки соответствует ожидаемому: {}", actualLinkText); // Логируем текст ссылки

        // Логируем текущий URL до клика
        logger.info("Текущий URL до клика: {}", driver.getCurrentUrl());

        // Кликаем по ссылке
        detailsLink.click();
        logger.info("Клик по ссылке 'Подробнее о сервисе' выполнен");

        // Ожидаем перехода на новую страницу
        wait.until(ExpectedConditions.urlToBe("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/"));
        logger.info("Текущий URL после перехода: {}", driver.getCurrentUrl());

        // Проверяем, что URL соответствует ожидаемому
        assertEquals("https://www.mts.by/help/poryadok-oplaty-i-bezopasnost-internet-platezhey/", driver.getCurrentUrl(),
                "Переход на страницу с деталями не выполнен");
        logger.info("Переход на страницу с деталями выполнен: {}", driver.getCurrentUrl());

        // Логируем успешное завершение теста
        logger.info("Тест testDetailsLink завершен успешно");
    }
}