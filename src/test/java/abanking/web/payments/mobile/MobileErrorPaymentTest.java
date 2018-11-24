package abanking.web.payments.mobile;

import abanking.web.RuleForRunWebTest;
import abanking.web.Waiters;
import abanking.web.elements.ModalDialog;
import abanking.web.elements.PaymentSelectOption;
import abanking.web.pages.NewPaymentPage;
import org.hamcrest.core.Is;
import org.junit.Rule;
import org.junit.Test;

import static abanking.web.Environment.scrollIntoElement;
import static abanking.web.Environment.webDriver;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

public class MobileErrorPaymentTest {

    @Rule
    public RuleForRunWebTest rule = new RuleForRunWebTest();

    @Test
    public void errorPaymentTest() {
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
}
