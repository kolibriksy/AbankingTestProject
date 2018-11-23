package abanking.web.pages;

import abanking.web.Waiters;
import abanking.web.elements.ModalDialog;
import abanking.web.elements.NotificationOperationSuccess;
import abanking.web.elements.PaymentSelectOption;
import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import java.text.DecimalFormat;

import static abanking.web.Environment.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;


public class LoginPageTest {

    //public WebDriver webDriver;

    @Before
    public void loadStartPage() {
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.get(applicationUrl + "/account/login");

        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.loginMethod("retail", "retail");
        AbankingMainPage mainPage = new AbankingMainPage(webDriver);
        Waiters.waitVisibility(webDriver, mainPage.paymentItem, "Ждем загрузки главной страницы");
    }

    private String formatDecimalToString(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }

    @Test
    public void successPayment() {
        String accountTitle =
                "Счет с очень длинным названием. Очень-очень длинное название, слишком, чтобы влезть в строку";
        String summ = "10";

        // заполним поля для платежа
        NewPaymentPage paymentPage = new NewPaymentPage();
        paymentPage.openPageByUrl();
        paymentPage.fillForm("Билайн", "89031112233", accountTitle, summ);

        //запомним баланс счета до списания
        String balanceBeforePayment = paymentPage.paymentSelect.getSelectedValue().getBalance();
        double expectedBalance = Double.valueOf(balanceBeforePayment) - Double.valueOf(summ);
        String formattedExpectedBalance = formatDecimalToString(expectedBalance);
        System.out.println(formattedExpectedBalance);

        scrollIntoElement(paymentPage.sendButton.getWrappedElement());
        paymentPage.sendButton.click();

        // проверим появление сообщения об успешной оплате
        NotificationOperationSuccess successMesage = paymentPage.successMesage;
        Waiters.waitVisibility(webDriver, successMesage.getWrappedElement(),
                "Не дождались появления окна подтверждения оплаты");
        scrollIntoElement(successMesage.getWrappedElement());
        boolean isDisplayed = successMesage.isDisplayed();
        System.out.println(isDisplayed);
        assertThat(isDisplayed, Is.is(true));

        String title = successMesage.getTitle();
        System.out.println(title);
        assertThat(title, equalToIgnoringWhiteSpace("Операция успешно выполнена."));
        String message = successMesage.getDescription();
        System.out.println(message);
        assertThat(message, equalToIgnoringWhiteSpace("После отправления средств можете обратиться в банк за подтверждением об оплате."));

        // обновим стр и проверим что баланс счета не изменился
        paymentPage.openPageByUrl();

        paymentPage.paymentSelect.selectByTitle(accountTitle);
        PaymentSelectOption selectOption = paymentPage.paymentSelect.getSelectedValue();
        String balanceAfterPayment = selectOption.getBalance();
        String formattedBalanceAfterPayment = formatDecimalToString(Double.valueOf(balanceAfterPayment));
        System.out.println(formattedBalanceAfterPayment);
        assertThat(formattedBalanceAfterPayment, equalToIgnoringWhiteSpace(formattedExpectedBalance));
    }

    @Test
    public void errorPayment() {
        String accountTitle = "Накопительный!";

        // заполним поля для платежа
        NewPaymentPage paymentPage = new NewPaymentPage();
        paymentPage.openPageByUrl();
        paymentPage.fillForm("Билайн", "89031112233", accountTitle, "10");

        //запомним баланс счета до списания
        String balanceBeforePayment = paymentPage.paymentSelect.getSelectedValue().getBalance();

        scrollIntoElement(paymentPage.sendButton.getWrappedElement());
        paymentPage.sendButton.click();

        Waiters.freezeInMilliSeconds(2000);

        // проверим появление окна с ошибкой
        ModalDialog error = paymentPage.modalDialog;
        Waiters.waitVisibility(webDriver, error.getWrappedElement(), "Не дождались появления окна с ошибкой");
        scrollIntoElement(error.getWrappedElement());
        boolean isDisplayed = error.isDisplayed(); //true
        System.out.println(isDisplayed);
        assertThat(isDisplayed, Is.is(true));

        String title = error.getTitle();
        System.out.println(title);
        assertThat(title, equalToIgnoringWhiteSpace("Операция не удалась"));
        String message = error.getMessage();
        System.out.println(message);
        assertThat(message, equalToIgnoringWhiteSpace("Вклад 42305810688800000111 не имеет возможность снятия"));

        // обновим стр и проверим что баланс счета не изменился
        paymentPage.openPageByUrl();

        paymentPage.paymentSelect.selectByTitle(accountTitle);
        PaymentSelectOption selectOption = paymentPage.paymentSelect.getSelectedValue();
        String balanceAfterPayment = selectOption.getBalance();
        System.out.println(balanceAfterPayment);
        assertThat(balanceAfterPayment, equalToIgnoringWhiteSpace(balanceBeforePayment));
    }

    public void validationTest() {
        webDriver.get("https://abanking.artsofte.ru/cabinet/payment/7532/721355/pay");

        NewPaymentPage paymentPage = new NewPaymentPage();
        paymentPage.sendButton.isEnabled(); //false

        paymentPage.paymentSumm.getInput().clear();
        paymentPage.paymentSumm.getValidator(); //Поле "Сумма платежа:" обязательно для заполнения 
        paymentPage.paymentSumm.getPlaceholder(); // 0.00
        paymentPage.mobileNumber.getValidator(); //Поле "Номер телефона:" обязательно для заполнения
        paymentPage.mobileNumber.getPlaceholder(); // +7 (xxx) xx-xx-xxx
        paymentPage.sendButton.isEnabled(); //false
    }

    @After
    public void killWebDriver() {
        webDriver.quit();
    }
}
