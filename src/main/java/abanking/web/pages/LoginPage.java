package abanking.web.pages;

import abanking.web.elements.TextInput;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

public class LoginPage {

    @FindBy(xpath = "(//text-control)[1]")
    public TextInput loginInput;

    @FindBy(xpath = "(//text-control)[2]")
    public TextInput passwordInput;

    @FindBy(xpath = "//button")
    public Button okButton;

    private WebDriver webDriver;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(new HtmlElementDecorator(webDriver), this);
        this.webDriver = webDriver;
    }

    public void loginMethod(String login, String password) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        loginInput.input.clear();
        loginInput.input.sendKeys(login);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        passwordInput.input.clear();
        passwordInput.input.sendKeys(password);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        okButton.click();
    }
}
