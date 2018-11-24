package abanking.web.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

import static abanking.web.Environment.webDriver;

/**
 * Главная страница
 */
public class AbankingMainPage {

    @FindBy(xpath = "//div[contains(@class,'menu__item_payment')]")
    public WebElement paymentItem;

    @FindBy(xpath = "//div[contains(@class, 'paymentItem__paymentName') and contains(text(), 'Мобильная связь')]")
    public WebElement mobilePayment;

    @FindBy(xpath = "//div[contains(@class, 'paymentTarget__paymentName') and contains(text(), 'Билайн')]")
    public WebElement beelinePayment;

    public AbankingMainPage() {
        PageFactory.initElements(new HtmlElementDecorator(webDriver), this);
    }
}
