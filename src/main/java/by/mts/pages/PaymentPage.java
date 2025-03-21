package by.mts.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class PaymentPage extends BasePage {

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    // Используем метод acceptCookies из BasePage
    public void acceptCookies() {
        acceptCookiesIfPresent(Locators.COOKIE_BLOCK, Locators.ACCEPT_COOKIES_BTN);
    }

    // Метод для проверки и нахождения блока с платежной информацией
    public void checkPayBlock() {
        WebElement payBlock = find(Locators.PAY_BLOCK_NAME);
        if (payBlock != null) {
            System.out.println("Блок с платежной информацией найден.");
        } else {
            System.out.println("Блок с платежной информацией не найден.");
        }
        Assert.assertNotNull(payBlock, "Блок с платежной информацией не найден.");
    }

    // Метод для проверки названия блока с платежной информацией
    public void checkPayBlockName(String expectedTitle) {
        WebElement payBlockTitle = find(Locators.PAY_BLOCK_NAME);
        String actualTitle = payBlockTitle.getText();

        // Сравниваем фактический текст с ожидаемым
        if (actualTitle.equals(expectedTitle)) {
            System.out.println("Название блока соответствует ожидаемому.");
        } else {
            System.out.println("Название блока не соответствует ожидаемому.");
        }
        Assert.assertEquals(actualTitle, expectedTitle, "Название блока не соответствует ожидаемому");
    }

    // Метод для выбора услуги из списка
    public void selectService(String serviceName) {
        try {
            click(Locators.SERVICES_BUTTON);
            WebElement serviceOption = driver.findElement(By.xpath("//span[text()='" + serviceName + "']"));
            serviceOption.click();
            System.out.println("Услуга \"" + serviceName + "\" успешно выбрана.");
        } catch (Exception e) {
            System.out.println("Ошибка при выборе услуги: " + e.getMessage());
        }
    }

    // Метод для очистки полей
    public void clearFields() {
        try {
            setText(Locators.PHONE_NUMBER_FIELD, "");
            setText(Locators.AMOUNT_FIELD, "");
            System.out.println("Поля успешно очищены.");
        } catch (Exception e) {
            System.out.println("Ошибка при очистке полей: " + e.getMessage());
        }
    }

    // Метод для ввода данных в поля
    public void enterData(String phoneNumber, String amount) {
        try {
            setText(Locators.PHONE_NUMBER_FIELD, phoneNumber);
            setText(Locators.AMOUNT_FIELD, amount);
            System.out.println("Данные успешно введены: номер телефона - " + phoneNumber + ", сумма - " + amount);
        } catch (Exception e) {
            System.out.println("Ошибка при вводе данных: " + e.getMessage());
        }
    }

    // Универсальный метод для проверки и клика по кнопке
    public void checkAndClickButton(By locator) {
        try {
            Assert.assertTrue(isElementVisible(locator), "Кнопка не видна");
            click(locator);
            System.out.println("Кнопка по локатору " + locator + " успешно кликнута.");
        } catch (Exception e) {
            System.out.println("Ошибка при клике по кнопке: " + e.getMessage());
        }
    }

    // Проверка видимости логотипа
    public void checkLogoVisibility(By locator, String logoName) {
        boolean isVisible = isElementVisible(locator);
        if (isVisible) {
            System.out.println(logoName + " логотип виден.");
        } else {
            System.out.println(logoName + " логотип не виден.");
        }
        Assert.assertTrue(isVisible, logoName + " логотип не виден.");
    }
}
