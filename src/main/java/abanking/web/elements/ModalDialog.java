package abanking.web.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 * Всплывающее информационное окно
 */
public class ModalDialog extends HtmlElement {

    @FindBy(xpath = ".//div[contains(@class, 'title')]")
    private WebElement title;

    @FindBy(xpath = ".//div[contains(@class, 'message')]")
    private WebElement message;

    @FindBy(xpath = ".//div[contains(@class, 'close')]")
    private WebElement closeLink;

    public String getType() {
        if (getWrappedElement().getAttribute("class").contains("isError")) {
            return "ERROR";
        } else {
            return "WARNING";
        }
    }

    public String getTitle() {
        return title.getText();
    }

    public String getMessage() {
        return message.getText();
    }

    public WebElement getCloseLink() {
        return closeLink;
    }
}
