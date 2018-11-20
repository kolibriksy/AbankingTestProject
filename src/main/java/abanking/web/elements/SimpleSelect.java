package abanking.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

/**
 * Простой список "Выбор оператора"
 */
public class SimpleSelect extends AbstractElement {

    @FindBy(xpath = ".//div[contains(@class, 'select-control__list')]")
    private WebElement list;

    @FindBy(xpath = ".//div[contains(@class,'select-control__data')]")
    private WebElement selectedValue;

    private String xpathList = "//select-simple//div[contains(@class, 'select-control__list')]";
    private String xpathItem = ".//*[text() = '%s']";

    public List<String> getValues() {
        openList();

        List<String> result = new ArrayList<>();
        List<WebElement> items = list.findElements(By.xpath(xpathItem));
        for (WebElement item : items) {
            result.add(item.getText());
        }

        return result;
    }

    public void selectByValue(String value){
        openList();
        List<WebElement> items = list.findElements(By.xpath(xpathItem));
        for (WebElement item : items) {
            if (value.equals(item.getText())){
                item.click();
                break;
            }
        }
    }

    public void openList(){
        if (list.getAttribute("class").contains("is-hidden")) {
            list.click();
        }
    }

    public String getSelectedValue(){
        return selectedValue.getText();
    }

    public WebElement getLabel() {
        return label;
    }

    public WebElement getValidator() {
        return validator;
    }

    public WebElement getPlaceholder() {
        return placeholder;
    }
}
