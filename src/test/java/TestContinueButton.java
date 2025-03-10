import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class TestContinueButton {
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
    public void testContinueButton() {
        // Открываем страницу
        driver.get("https://www.mts.by");

        // Ожидаем появления поля для ввода номера телефона
        WebElement phoneNumberField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Номер телефона']")));
        assertTrue(phoneNumberField.isDisplayed(), "Поле для ввода номера телефона не отображается");
        phoneNumberField.sendKeys("297777777");

        // Ожидаем появления поля для ввода суммы и вводим значение
        WebElement amountField = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@placeholder='Сумма']")));
        assertTrue(amountField.isDisplayed(), "Поле для ввода суммы не отображается");
        amountField.sendKeys("10");

        // Ожидаем появления кнопки "Продолжить"
        WebElement continueButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Продолжить')]")));
        assertTrue(continueButton.isDisplayed(), "Кнопка 'Продолжить' не отображается");
        continueButton.click();

        // Проверка, что произошёл переход на следующую страницу
        wait.until(ExpectedConditions.urlContains("payment"));
        assertTrue(driver.getCurrentUrl().contains("payment"), "Переход на страницу оплаты не выполнен");

        // Дополнительные проверки, если необходимо
        // Например, проверка, что введённая сумма корректно отображается на следующей странице
        WebElement displayedAmount = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[@class='amount-display']")));
        assertEquals("10", displayedAmount.getText(), "Сумма отображается некорректно");
    }
}