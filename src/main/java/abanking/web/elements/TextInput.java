package abanking.web.elements;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

public class TextInput extends AbstractElement {

    @FindBy(xpath = ".//div[contains(@class, 'form__label-container')]")
    public WebElement label;

    @FindBy(xpath = ".//input")
    public WebElement input;

    @FindBy(xpath = ".//div[contains(@class, 'form__validation')]")
    public WebElement validator;

    public String getText(){
        return input.getText();
    }

    public WebElement getInput() {
        return this.input;
    }

    public String getValue() {
        try {
            return this.getInput().getAttribute("value");
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Input value not found", ex);
        }
    }
}
