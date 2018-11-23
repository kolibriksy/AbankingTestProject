package abanking.web.pages;


import abanking.web.Waiters;
import abanking.web.elements.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

import static abanking.web.Environment.*;

/**
 * Страница Новый платеж
 */
public class NewPaymentPage {

    public static final String PAGE_ADDRESS = "/cabinet/payment/7532/721355/pay";

    @FindBy(xpath = "//text-control[1]")
    public TextInput mobileNumber;

    @FindBy(xpath = "//amount-control/div")
    public TextInput paymentSumm;

    @FindBy(xpath = "//select-simple")
    public SimpleSelect operatorSelect;

    @FindBy(xpath = "//select-product-control")
    public PaymentSelect paymentSelect;

    @FindBy(xpath = "//ab-button/button[text() = 'Отправить']")
    public Button sendButton;

    @FindBy(xpath = "//div[contains(@class, 'form-message-error')]")
    public MessageErrorBlock messageErrorBlock;

    @FindBy(xpath = "//div[@class = 'notificationOperationSuccess']")
    public NotificationOperationSuccess successMesage;

    @FindBy(xpath = "//dlg/div")
    public ModalDialog modalDialog;

    public NewPaymentPage() {
        PageFactory.initElements(new HtmlElementDecorator(webDriver), this);
    }

    public void fillForm(String operator, String number, String accountTitle, String summ) {
        this.operatorSelect.selectByValue(operator);
        Waiters.freezeInMilliSeconds(2000);

        scrollIntoElement(this.mobileNumber.getWrappedElement());
        this.mobileNumber.getInput().clear();
        this.mobileNumber.getInput().sendKeys(number);
        Waiters.freezeInMilliSeconds(1000);

        this.paymentSelect.selectByTitle(accountTitle);
        Waiters.freezeInMilliSeconds(1000);

        scrollIntoElement(this.paymentSumm.getWrappedElement());
        this.paymentSumm.getInput().sendKeys(summ);
        Waiters.freezeInMilliSeconds(1000);
    }

    public void openPageByUrl() {
        webDriver.get(applicationUrl + PAGE_ADDRESS);
        Waiters.waitVisibility(webDriver, this.sendButton.getWrappedElement(),
                "Не дождались открытия страницы 'Оплата мобильной связи'");
    }
}
