package by.mts.tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import by.mts.pages.Locators;
import by.mts.pages.PaymentPage;

public class PaymentPageTest {
    private WebDriver driver;
    private PaymentPage paymentPage;

    @BeforeEach
    public void setUp() {
        // Инициализация драйвера и страницы
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://mts.by");

        // Создаем экземпляр PaymentPage
        paymentPage = new PaymentPage(driver);
        paymentPage.acceptCookies();  // Принимаем cookies
    }

    @Test
    public void testPayBlockCustomTitle() {
        // Проверка названия блока
        paymentPage.checkPayBlock();  // Проверка наличия блока с платежной информацией
        paymentPage.checkPayBlockName("Онлайн пополнение\nбез комиссии"); // Проверка названия блока
    }

    @Test
    public void checkPaymentLogosTest() {
        // Проверка логотипа Visa
        paymentPage.checkLogoVisibility(Locators.VISA_LOGO, "Visa");

        // Проверка логотипа MasterCard
        paymentPage.checkLogoVisibility(Locators.MASTERCARD_LOGO, "MasterCard");
    }

    @Test
    public void testServiceTypeButton() {
        // Проверяем видимость и кликаем по ссылке "Подробнее о сервисе"
        paymentPage.checkAndClickButton(Locators.SERVICE_TYPE_BUTTON);
    }

    @Test
    public void testFillPaymentForm() {
        // Найти блок с платежной информацией
        paymentPage.checkPayBlock();

        // Выбрать услугу
        paymentPage.selectService("Услуги связи");

        // Очистить поля и ввести данные
        paymentPage.clearFields();
        paymentPage.enterData("297777777", "10");

        // Проверить видимость кнопки "Продолжить" и нажать ее
        paymentPage.checkAndClickButton(Locators.CONTINUE_BUTTON);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
