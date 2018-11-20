package abanking.web.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 * Абстрактный класс для всех элементов
 */
public class AbstractElement extends HtmlElement {

    @FindBy(xpath = ".//div[contains(@class, 'form__validation')]")
    protected WebElement validator;

    @FindBy(xpath = ".//div[contains(@class, 'form__label-container')]")
    protected WebElement label;

    @FindBy(xpath = ".//div[contains(@class, 'placeholder')]")
    protected WebElement placeholder;

    public WebElement getValidator() {
        return validator;
    }

    public WebElement getLabel() {
        return label;
    }

    public WebElement getPlaceholder() {
        return placeholder;
    }
}
