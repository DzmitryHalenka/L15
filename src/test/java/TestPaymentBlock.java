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

public class TestPaymentBlock {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(TestPaymentBlock.class); // Логгер

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
    public void testPaymentBlock() {
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

        // Ожидаем появления блока оплаты
        WebElement paymentBlock = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div")));
        assertTrue(paymentBlock.isDisplayed(), "Блок оплаты не отображается");
        logger.info("Блок оплаты отображается"); // Логируем отображение блока оплаты

        // Выбираем "Услуги связи" в первом поле
        WebElement serviceTypeButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/div[1]/div[1]/div[2]/button/span[1]")));

        // Используем JavaScript для клика, если обычный клик не работает
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", serviceTypeButton);
        logger.info("Выбрана услуга: Услуги связи"); // Логируем выбор услуги

        // Заполняем поле "Номер телефона" (после +375)
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/div[1]/div[2]/form[1]/div[1]/input")));
        phoneNumberField.sendKeys("297777777");
        logger.info("Заполнено поле 'Номер телефона': 297777777"); // Логируем заполнение поля

        // Заполняем поле "Сумма"
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/div[1]/div[2]/form[1]/div[2]/input")));
        amountField.sendKeys("10"); // Пример суммы
        logger.info("Заполнено поле 'Сумма': 10"); // Логируем заполнение поля

        // Проверяем, что кнопка "Продолжить" активна и кликабельна
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/div[1]/div[2]/form[1]/button")));
        assertTrue(continueButton.isEnabled(), "Кнопка 'Продолжить' не активна");
        logger.info("Кнопка 'Продолжить' активна и кликабельна"); // Логируем проверку кнопки

        // Логируем успешное завершение теста
        logger.info("Тест testPaymentBlock завершен успешно");
    }
}