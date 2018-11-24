package abanking.web.pages;

import abanking.web.Waiters;
import abanking.web.elements.TextInput;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.htmlelements.element.Button;
import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementDecorator;

import static abanking.web.Environment.webDriver;

public class LoginPage {

    @FindBy(xpath = "(//text-control)[1]")
    public TextInput loginInput;

    @FindBy(xpath = "(//text-control)[2]")
    public TextInput passwordInput;

    @FindBy(xpath = "//button")
    public Button okButton;

    public LoginPage(WebDriver webDriver) {
        PageFactory.initElements(new HtmlElementDecorator(webDriver), this);
    }

    @Step("Логинимся на сайте: Логин = {login} Пароль = {password}")
    public void loginMethod(String login, String password) {
        Waiters.waitVisibility(webDriver, loginInput.input, "Не дождались видимости текстового поля Логин");

        loginInput.input.clear();
        loginInput.input.sendKeys(login);

        passwordInput.input.clear();
        passwordInput.input.sendKeys(password);

        okButton.click();
    }
}
