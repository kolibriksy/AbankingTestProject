package abanking.web.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 * Окно подтверждения успешности операции
 */
public class NotificationOperationSuccess extends HtmlElement {

    @FindBy(xpath = ".//div[contains(@class, 'title')]")
    private WebElement title;

    @FindBy(xpath = ".//div[contains(@class, 'desc')]")
    private WebElement description;

    @FindBy(xpath = ".//div[contains(@class, 'action_download')]")
    private WebElement downloadReceiptLink;

    @FindBy(xpath = ".//div[contains(@class, 'action_createTemplate')]")
    private WebElement createTemplateLink;

    @FindBy(xpath = ".//div[contains(@class, 'action_RegularTemplate')]")
    private WebElement createRegularTemplateLink;

    public String getTitle() {
        return title.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public WebElement getDownloadReceiptLink() {
        return downloadReceiptLink;
    }

    public WebElement getCreateTemplateLink() {
        return createTemplateLink;
    }

    public WebElement getCreateRegularTemplateLink() {
        return createRegularTemplateLink;
    }
}
