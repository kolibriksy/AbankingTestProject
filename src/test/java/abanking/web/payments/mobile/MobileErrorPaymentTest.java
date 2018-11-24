package abanking.web.payments.mobile;

import abanking.web.RuleForRunWebTest;
import abanking.web.Waiters;
import abanking.web.elements.ModalDialog;
import abanking.web.elements.PaymentSelect;
import abanking.web.elements.PaymentSelectOption;
import abanking.web.pages.NewPaymentPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;

import static abanking.web.Environment.webDriver;
import static abanking.web.Utils.scrollIntoElement;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

public class MobileErrorPaymentTest {

    @Rule
    public RuleForRunWebTest rule = new RuleForRunWebTest();

    @Test()
    @Description("Проверка запрета платежа с вкладного счета")
    @Feature("Оплата.Мобильная связь")
    public void errorPaymentTest() {
        String accountTitle = "Накопительный!";

        // заполним поля для платежа
        NewPaymentPage paymentPage = new NewPaymentPage();
        paymentPage.openPageByUrl();
        paymentPage.fillFormWoOperator("89031112233", accountTitle, "10");

        //запомним баланс счета до списания
        String balanceBeforePayment = paymentPage.paymentSelect.getSelectedValue().getBalance();

        scrollIntoElement(paymentPage.sendButton.getWrappedElement());
        paymentPage.sendButton.click();

        Waiters.freezeInMilliSeconds(2000);

        // проверим появление окна с ошибкой
        ModalDialog error = paymentPage.modalDialog;
        checkErrorModalDialog(error,
                "Операция не удалась",
                "Вклад 42305810688800000111 не имеет возможность снятия");

        // обновим стр и проверим что баланс счета не изменился
        paymentPage.openPageByUrl();

        PaymentSelect paymentSelect = paymentPage.paymentSelect;
        checkAccountBalance(paymentSelect, accountTitle, balanceBeforePayment);
    }

    @Test
    @Description("Проверка запрета платежа с кредитного счета")
    @Feature("Оплата.Мобильная связь")
    public void errorPaymentCreditAccountTest() {
        String accountTitle = "линное название Длинное название Длинное название Длинное название Длинное названи";

        // заполним поля для платежа
        NewPaymentPage paymentPage = new NewPaymentPage();
        paymentPage.openPageByUrl();
        paymentPage.fillFormWoOperator("89031112233", accountTitle, "10");

        //запомним баланс счета до списания
        String balanceBeforePayment = paymentPage.paymentSelect.getSelectedValue().getBalance();

        scrollIntoElement(paymentPage.sendButton.getWrappedElement());
        paymentPage.sendButton.click();

        Waiters.freezeInMilliSeconds(2000);

        // проверим появление окна с ошибкой
        ModalDialog error = paymentPage.modalDialog;
        checkErrorModalDialog(error,
                "Операция не удалась",
                "Счет 0817810100000005544 не имеет возможность снятия");

        // обновим стр и проверим что баланс счета не изменился
        paymentPage.openPageByUrl();

        PaymentSelect paymentSelect = paymentPage.paymentSelect;
        checkAccountBalance(paymentSelect, accountTitle, balanceBeforePayment);
    }

    @Step("Проверим сообщение об ошибке")
    private void checkErrorModalDialog(ModalDialog error, String expectedTitle, String expectedMessage) {
        Waiters.waitVisibility(webDriver, error.getWrappedElement(), "Не дождались появления окна с ошибкой");
        scrollIntoElement(error.getWrappedElement());
        boolean isDisplayed = error.isDisplayed();
        System.out.println(isDisplayed);
        assertThat(isDisplayed, Is.is(true));

        String title = error.getTitle();
        System.out.println(title);
        assertThat(title, equalToIgnoringWhiteSpace(expectedTitle));
        String message = error.getMessage();
        System.out.println(message);
        assertThat(message, equalToIgnoringWhiteSpace(expectedMessage));
    }

    @Step("Проверим баланс счета после оплаты")
    private void checkAccountBalance(PaymentSelect paymentSelect, String accountTitle, String expectedBalance) {
        paymentSelect.selectByTitle(accountTitle);
        PaymentSelectOption selectOption = paymentSelect.getSelectedValue();
        String balanceAfterPayment = selectOption.getBalance();
        System.out.println(balanceAfterPayment);
        assertThat(balanceAfterPayment, equalToIgnoringWhiteSpace(expectedBalance));
    }
}
