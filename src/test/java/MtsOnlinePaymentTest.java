import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class MtsOnlinePaymentTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LoggerFactory.getLogger(MtsOnlinePaymentTest.class); // Инициализация логгера

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Открываем сайт
        driver.get("https://www.mts.by");

        // Принимаем куки, если окно появилось
        try {
            WebElement acceptCookiesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//button[contains(text(), 'Принять')]")));
            acceptCookiesButton.click();
            logger.info("Куки приняты");
        } catch (TimeoutException e) {
            logger.info("Окно с куки не появилось");
        }
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Браузер Chrome закрыт");
        }
    }

    @Test
    public void testBlockTitle() {
        WebElement blockTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h2[contains(text(), 'Онлайн пополнение без комиссии')]")));
        assertEquals("Онлайн пополнение без комиссии", blockTitle.getText());
        logger.info("Заголовок блока проверен");
    }

    @Test
    public void testPaymentLogos() {
        WebElement visaLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//img[contains(@alt, 'Visa')]")));
        WebElement mastercardLogo = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//img[contains(@alt, 'MasterCard')]")));

        assertTrue(visaLogo.isDisplayed());
        assertTrue(mastercardLogo.isDisplayed());
        logger.info("Логотипы платёжных систем проверены");
    }

    @Test
    public void testDetailsLink() {
        WebElement detailsLink = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(), 'Подробнее о сервисе')]")));
        detailsLink.click();
        logger.info("Нажата ссылка 'Подробнее о сервисе'");

        wait.until(ExpectedConditions.urlContains("service-details"));
        assertTrue(driver.getCurrentUrl().contains("service-details"));
        logger.info("Переход на страницу с деталями выполнен");
    }

    @Test
    public void testContinueButton() {
        // Вводим номер телефона
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Номер телефона']")));
        phoneNumberField.sendKeys("297777777");
        logger.info("Введён номер телефона: 297777777");

        // Вводим сумму
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Сумма']"))); // Убедитесь, что локатор корректный
        amountField.sendKeys("10"); // Вводим сумму, например, 10 рублей
        logger.info("Введена сумма: 10");

        // Нажимаем кнопку "Продолжить"
        WebElement continueButton = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[contains(text(), 'Продолжить')]")));
        continueButton.click();
        logger.info("Нажата кнопка 'Продолжить'");

        // Проверяем, что произошёл переход на страницу оплаты
        wait.until(ExpectedConditions.urlContains("payment"));
        assertTrue(driver.getCurrentUrl().contains("payment"));
        logger.info("Переход на страницу оплаты выполнен");
    }
}