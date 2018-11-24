package abanking.web.payments.mobile;

import abanking.web.RuleForRunWebTest;
import abanking.web.Waiters;
import abanking.web.elements.NotificationOperationSuccess;
import abanking.web.elements.PaymentSelect;
import abanking.web.elements.PaymentSelectOption;
import abanking.web.pages.NewPaymentPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;

import static abanking.web.Environment.scrollIntoElement;
import static abanking.web.Environment.webDriver;
import static abanking.web.Utils.formatDecimalToString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

public class MobileSuccessPaymentTest {

    @Rule
    public RuleForRunWebTest rule = new RuleForRunWebTest();

    @Test
    @Description("Проверка успешного платежа")
    @Feature("Оплата.Мобильная связь")
    public void successPaymentTest1() {
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
        checkSuccessMessage(successMesage,
                "Операция успешно выполнена.",
                "После отправления средств можете обратиться в банк за подтверждением об оплате.");

        // обновим стр и проверим что баланс счета уменьшился на сумму платежа
        paymentPage.openPageByUrl();

        PaymentSelect paymentSelect = paymentPage.paymentSelect;
        checkAccountBalance(paymentSelect, accountTitle, formattedExpectedBalance);

        /*paymentPage.paymentSelect.selectByTitle(accountTitle);
        PaymentSelectOption selectOption = paymentPage.paymentSelect.getSelectedValue();
        String balanceAfterPayment = selectOption.getBalance();
        String formattedBalanceAfterPayment = formatDecimalToString(Double.valueOf(balanceAfterPayment));
        System.out.println(formattedBalanceAfterPayment);
        assertThat(formattedBalanceAfterPayment, equalToIgnoringWhiteSpace(formattedExpectedBalance));*/
    }

    @Test
    @Description("Проверка успешного платежа")
    @Feature("Оплата.Мобильная связь")
    public void successPaymentTest2() {
        String accountTitle = "На обед жене и детям1";
        String summ = "15.10";

        // заполним поля для платежа
        NewPaymentPage paymentPage = new NewPaymentPage();
        paymentPage.openPageByUrl();
        paymentPage.fillFormWoOperator("89031112233", accountTitle, summ);

        //запомним баланс счета до списания
        String balanceBeforePayment = paymentPage.paymentSelect.getSelectedValue().getBalance();
        double expectedBalance = Double.valueOf(balanceBeforePayment) - Double.valueOf(summ);
        String formattedExpectedBalance = formatDecimalToString(expectedBalance);
        System.out.println(formattedExpectedBalance);

        scrollIntoElement(paymentPage.sendButton.getWrappedElement());
        paymentPage.sendButton.click();

        // проверим появление сообщения об успешной оплате
        NotificationOperationSuccess successMesage = paymentPage.successMesage;
        checkSuccessMessage(successMesage,
                "Операция успешно выполнена.",
                "После отправления средств можете обратиться в банк за подтверждением об оплате.");

        // обновим стр и проверим что баланс счета уменьшился на сумму платежа
        paymentPage.openPageByUrl();

        PaymentSelect paymentSelect = paymentPage.paymentSelect;
        checkAccountBalance(paymentSelect, accountTitle, formattedExpectedBalance);

        /*paymentPage.paymentSelect.selectByTitle(accountTitle);
        PaymentSelectOption selectOption = paymentPage.paymentSelect.getSelectedValue();
        String balanceAfterPayment = selectOption.getBalance();
        String formattedBalanceAfterPayment = formatDecimalToString(Double.valueOf(balanceAfterPayment));
        System.out.println(formattedBalanceAfterPayment);
        assertThat(formattedBalanceAfterPayment, equalToIgnoringWhiteSpace(formattedExpectedBalance));*/
    }

    @Step("Проверим сообщение об успешной операции")
    private void checkSuccessMessage(NotificationOperationSuccess successMesage, String expectedTitle, String expectedMessage) {
        Waiters.waitVisibility(webDriver, successMesage.getWrappedElement(),
                "Не дождались появления окна подтверждения оплаты");
        scrollIntoElement(successMesage.getWrappedElement());
        boolean isDisplayed = successMesage.isDisplayed();
        System.out.println(isDisplayed);
        assertThat(isDisplayed, Is.is(true));

        String title = successMesage.getTitle();
        System.out.println(title);
        assertThat(title, equalToIgnoringWhiteSpace(expectedTitle));
        String message = successMesage.getDescription();
        System.out.println(message);
        assertThat(message, equalToIgnoringWhiteSpace(expectedMessage));
    }

    @Step("Проверим баланс счета после оплаты")
    private void checkAccountBalance(PaymentSelect paymentSelect, String accountTitle, String expectedBalance) {
        paymentSelect.selectByTitle(accountTitle);
        PaymentSelectOption selectOption = paymentSelect.getSelectedValue();
        String balanceAfterPayment = selectOption.getBalance();
        String formattedBalanceAfterPayment = formatDecimalToString(Double.valueOf(balanceAfterPayment));
        System.out.println(formattedBalanceAfterPayment);
        assertThat(formattedBalanceAfterPayment, equalToIgnoringWhiteSpace(expectedBalance));
    }
}
