package abanking.web.elements;

import abanking.web.Waiters;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

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
            if (title.equals(option.getTitle().getText())) {
                option.getTitle().click();
                isFinding = true;
                break;
            }
        }

        if (!isFinding) throw new NotFoundException("Не нашли элемент " + title + " в списке");
    }

    public void openList() {
        if (list.getAttribute("class").contains("is-hidden")) {
            container.click();
        }
    }

    public PaymentSelectOption getSelectedValue() {
        return selectedValue;
    }

    public List<PaymentSelectOption> getOptions() {
        return options;
    }
}
