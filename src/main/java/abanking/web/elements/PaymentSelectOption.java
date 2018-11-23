package abanking.web.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 * Составной элемент списка PaymentSelect
 */
public class PaymentSelectOption extends HtmlElement {

    @FindBy(xpath = ".//div[contains(@class, 'pic-img')]")
    private WebElement icon;

    @FindBy(xpath = ".//div[contains(@class, 'data-title')]")
    private WebElement title;

    @FindBy(xpath = ".//div[contains(@class, 'data-desc')]")
    private WebElement account;

    @FindBy(xpath = ".//div[contains(@class, 'balance')]/span")
    private WebElement balance;

    public WebElement getTitle() {
        return title;
    }

    public WebElement getAccount() {
        return account;
    }

    public String getBalance() {
        return balance.getAttribute("ng-reflect-balance");
    }
}
