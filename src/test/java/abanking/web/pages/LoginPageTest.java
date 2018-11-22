package abanking.web.pages;

import abanking.web.Waiters;
import abanking.web.elements.ModalDialog;
import abanking.web.elements.PaymentSelectOption;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginPageTest {

    public WebDriver driver;

    @Before
    public void loadStartPage() {
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://abanking.artsofte.ru/account/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginMethod("retail", "retail");
        AbankingMainPage mainPage = new AbankingMainPage(driver);
        Waiters.waitVisibility(driver, mainPage.paymentItem, "Ждем загрузки главной страницы");
    }

    @Test
    public void loginMethod() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginMethod("retail", "retail");

        AbankingMainPage mainPage = new AbankingMainPage(driver);
        Waiters.freezeInMilliSeconds(10000);
        mainPage.paymentItem.click();
        Waiters.freezeInMilliSeconds(1000);
        mainPage.mobilePayment.click();
        Waiters.freezeInMilliSeconds(5000);
        mainPage.beelinePayment.click();

        //todo можно сразу открыть нужный url платежа Билайн
        driver.get("https://abanking.artsofte.ru/cabinet/payment/7532/721355/pay");
        
        Waiters.freezeInMilliSeconds(10000);
        NewPaymentPage paymentPage = new NewPaymentPage(driver);
        paymentPage.operatorSelect.selectByValue("Билайн");
        Waiters.freezeInMilliSeconds(1000);
        paymentPage.paymentSumm.getInput().sendKeys("100");
        Waiters.freezeInMilliSeconds(5000);
        
        // todo заполняем форму данными
        //NewPaymentPage paymentPage = new NewPaymentPage(driver);
        paymentPage.fillForm("Билайн", "9031112233", "На обед жене и детям1", "10");
        paymentPage.sendButton.click();
        
        //todo ждем появления ссобщения успешного платежа
        Waiters.freezeInMilliSeconds(10000);
        paymentPage.successMesage.isDisplayed();
        
        //todo проверяем что сумма на счете уменьшилась
        driver.get("https://abanking.artsofte.ru/cabinet/payment/7532/721355/pay");

        PaymentSelectOption account =
                paymentPage.paymentSelect.getOptions().stream().filter(paymentSelectOption -> paymentSelectOption.getTitle().equals("gfd")).findFirst().get();
        account.getBalance();
    }
    
    @Test
    public void errorPayment() {
        driver.get("https://abanking.artsofte.ru/cabinet/payment/7532/721355/pay");
        
        NewPaymentPage paymentPage = new NewPaymentPage(driver);
        paymentPage.fillForm("Билайн", "9031112233", "Пенсионный", "10");
        paymentPage.sendButton.click();
        
        Waiters.freezeInMilliSeconds(2000);
        
        ModalDialog error = paymentPage.modalDialog;
        error.isDisplayed(); //true
        error.getTitle(); // Операция не удалась
        error.getMessage(); // Вклад 4256487867564 не имеет возможность снятия
        
        //todo проверяем что сумма на счете не изменилась
        driver.get("https://abanking.artsofte.ru/cabinet/payment/7532/721355/pay");
        PaymentSelectOption account =
                paymentPage.paymentSelect.getOptions().stream().filter(paymentSelectOption -> paymentSelectOption.getTitle().equals("Пенсионный")).findFirst().get();
        account.getBalance();
        
    }
    
    public void validationTest() {
        driver.get("https://abanking.artsofte.ru/cabinet/payment/7532/721355/pay");
        
        NewPaymentPage paymentPage = new NewPaymentPage(driver);
        paymentPage.sendButton.isEnabled(); //false
        
        paymentPage.paymentSumm.getInput().clear();
        paymentPage.paymentSumm.getValidator(); //Поле "Сумма платежа:" обязательно для заполнения 
        paymentPage.paymentSumm.getPlaceholder(); // 0.00
        paymentPage.mobileNumber.getValidator(); //Поле "Номер телефона:" обязательно для заполнения
        paymentPage.mobileNumber.getPlaceholder(); // +7 (xxx) xx-xx-xxx
        paymentPage.sendButton.isEnabled(); //false
    }

    @After
    public void killWebDriver() {
        driver.quit();
    }
}
