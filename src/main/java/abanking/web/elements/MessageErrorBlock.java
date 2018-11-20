package abanking.web.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 * Блок "Суммы недостаточно"
 */
public class MessageErrorBlock extends HtmlElement {

    @FindBy(xpath = ".//div[contains(@class, 'rest-amount')]")
    private WebElement message;

    @FindBy(xpath = ".//div[contains(@class, 'link_dashed')]")
    private WebElement button;

    public WebElement getMessage() {
        return message;
    }

    public WebElement getButton() {
        return button;
    }
}
