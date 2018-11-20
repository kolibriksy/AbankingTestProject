package abanking.web.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Список "Выбор счета"
 */
public class PaymentSelect extends AbstractElement {

    @FindBy(xpath = ".div[contains(@class, 'list-item')]")
    private List<PaymentSelectOption> options;

    @FindBy(xpath = ".//div[contains(@class,'select-product-control__title')]")
    private PaymentSelectOption selectedValue;

    @FindBy(xpath = ".//div[contains(@class, 'select-product-control__list')]")
    private WebElement list;

    public void selectByTitle(String title){
        openList();

        List<PaymentSelectOption> options = getOptions();

        for (PaymentSelectOption option : options) {
            if(title.equals(option.getTitle().getText())) {
                option.getTitle().click();
            }
        }
    }

    public void openList(){
        if (list.getAttribute("class").contains("is-hidden")) {
            list.click();
        }
    }

    public PaymentSelectOption getSelectedValue(){
        return selectedValue;
    }

    public List<PaymentSelectOption> getOptions(){
        return options;
    }
}
