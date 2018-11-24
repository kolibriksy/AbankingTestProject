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
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static abanking.web.Environment.webDriver;
import static abanking.web.Utils.formatDecimalToString;
import static abanking.web.Utils.scrollIntoElement;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;

@RunWith(value = Parameterized.class)
public class MobileSuccessPaymentTest {

    private String accountTitle;
    private String summ;

    public MobileSuccessPaymentTest(String accountTitle, String summ) {
        this.accountTitle = accountTitle;
        this.summ = summ;
    }

    @Parameterized.Parameters(name = "Счет = {0} | Сумма = {1}")
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][]{
                {"Счет с очень длинным названием. Очень-очень длинное название, слишком, чтобы влезть в строку", "10"},
                {"На обед жене и детям1", "15.50"}
        };
        return Arrays.asList(data);
    }

    @Rule
    public RuleForRunWebTest rule = new RuleForRunWebTest();

    @Test
    @Description("Проверка успешного платежа")
    @Feature("Оплата.Мобильная связь")
    public void successPaymentTest1() {
        String accountTitle = this.accountTitle;
        String summ = this.summ;

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
