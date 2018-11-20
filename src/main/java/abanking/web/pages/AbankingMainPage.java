package abanking.web.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

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

    private WebDriver webDriver;

    public AbankingMainPage(WebDriver webDriver) {
        PageFactory.initElements(new HtmlElementDecorator(webDriver), this);
        this.webDriver = webDriver;
    }
}
