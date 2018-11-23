package abanking.web.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static abanking.web.Environment.scrollIntoElement;

/**
 * Простой список "Выбор оператора"
 */
public class SimpleSelect extends AbstractElement {

    @FindBy(xpath = ".//div[contains(@class, 'select-control__list')]")
    private WebElement list;

    @FindBy(xpath = ".//div[@class = 'select-control__title']")
    private WebElement selectedValue;

    private String xpathList = "//select-simple//div[contains(@class, 'select-control__list')]";
    private final String xpathItem = ".//div[@class = 'select-control__data-title']";

    public List<String> getValues() {
        openList();

        List<String> result = new ArrayList<>();
        List<WebElement> items = list.findElements(By.xpath(xpathItem));
        for (WebElement item : items) {
            result.add(item.getText());
        }

        return result;
    }

    public void selectByValue(String value) {
        openList();
        List<WebElement> items = list.findElements(By.xpath(xpathItem));
        System.out.println(items.size());
        int i = 0;
        for (WebElement item : items) {
            scrollIntoElement(item);

            System.out.println((++i));
            System.out.println(item.getText());
            if (value.equals(item.getText())) {
                item.click();
                break;
            }
        }
    }

    public void openList() {
        scrollIntoElement(selectedValue);
        if (list.getAttribute("class").contains("is-hidden")) {
            selectedValue.click();
        }
    }

    public String getSelectedValue() {
        return selectedValue.getText();
    }
}
