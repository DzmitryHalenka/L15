package by.mts.pages;

import org.openqa.selenium.By;

public class Locators {
    public static final By COOKIE_BLOCK = By.xpath("/html/body/div[6]/main/div/div[2]");
    public static final By ACCEPT_COOKIES_BTN = By.cssSelector(".btn.btn_black.cookie__ok");
    public static final By PAY_BLOCK_NAME = By.xpath("/html/body/div[6]/main/div/div[4]/div[1]/div/div/div[2]/section/div/h2");
    public static final By SERVICES_BUTTON = By.cssSelector("button.select__header");
    public static final By SERVICE_TYPE_BUTTON = By.linkText("Подробнее о сервисе");
    public static final By PHONE_NUMBER_FIELD = By.id("connection-phone");
    public static final By AMOUNT_FIELD = By.id("connection-sum");
    public static final By CONTINUE_BUTTON = By.cssSelector("#pay-connection button");
    public static final By VISA_LOGO = By.cssSelector("img[alt='Visa']");
    public static final By MASTERCARD_LOGO = By.cssSelector("img[alt='MasterCard']");
}
