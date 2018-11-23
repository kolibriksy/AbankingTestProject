package abanking.web.elements;

import abanking.web.Waiters;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static abanking.web.Environment.scrollIntoElement;
import static abanking.web.Environment.webDriver;

/**
 * Список "Выбор счета"
 */
public class PaymentSelect extends AbstractElement {

    @FindBy(xpath = ".//div[contains(@class, 'list-item')]")
    private List<PaymentSelectOption> options;

    @FindBy(xpath = ".//div[@class ='select-product-control__title']")
    private PaymentSelectOption selectedValue;

    @FindBy(xpath = ".//div[contains(@class, 'select-product-control__list')]")
    private WebElement list;

    @FindBy(xpath = ".//div[@class = 'form__control-external-container']")
    private WebElement container;

    public void selectByTitle(String title) {
        openList();

        Waiters.freezeInMilliSeconds(1000);
        List<PaymentSelectOption> options = getOptions();

        boolean isFinding = false;
        for (PaymentSelectOption option : options) {
            if (!option.getTitle().isDisplayed())
                scrollIntoElement(option.getWrappedElement());

            System.out.println(option.getTitle().getText());
            Waiters.waitVisibility(webDriver, option.getTitle(), "Не дождались видимости элемента списка");
            if (title.equals(option.getTitle().getText())) {
                option.getTitle().click();
                isFinding = true;
                break;
            }
        }

        if (!isFinding) throw new NotFoundException("Не нашли элемент " + title + " в списке");
    }

    public void openList() {
        scrollIntoElement(container);
        if (list.getAttribute("class").contains("is-hidden")) {
            Waiters.waitVisibility(webDriver, container, "Не дождались видимости списка");
            container.click();
        }
    }

    public PaymentSelectOption getSelectedValue() {
        return selectedValue;
    }

    private List<PaymentSelectOption> getOptions() {
        return options;
    }
}
