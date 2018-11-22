package abanking.web.pages;


import abanking.web.Waiters;
import abanking.web.elements.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

/**
 * Страница Новый платеж
 */
public class NewPaymentPage {

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

    private WebDriver webDriver;

    public NewPaymentPage(WebDriver webDriver) {
        PageFactory.initElements(new HtmlElementDecorator(webDriver), this);
        this.webDriver = webDriver;
    }
    
    public void fillForm(String operator, String number, String accountTitle, String summ) {

//        this.mobileNumber.getInput().sendKeys(number);
//        Waiters.freezeInMilliSeconds(1000);
//
//        this.paymentSumm.getInput().sendKeys(summ);
//        Waiters.freezeInMilliSeconds(1000);

//        this.operatorSelect.selectByValue(operator);
//        Waiters.freezeInMilliSeconds(1000);

        this.paymentSelect.selectByTitle(accountTitle);
        Waiters.freezeInMilliSeconds(1000);
    }
}
