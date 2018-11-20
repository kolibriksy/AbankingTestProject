package abanking.web.pages;

import abanking.web.pages.Waiters;
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

        Waiters.freezeInMilliSeconds(10000);
        NewPaymentPage paymentPage = new NewPaymentPage(driver);
        paymentPage.operatorSelect.selectByValue("Билайн");
        Waiters.freezeInMilliSeconds(1000);
        paymentPage.paymentSumm.getInput().sendKeys("100");
        Waiters.freezeInMilliSeconds(5000);
    }

    @After
    public void killWebDriver() {
        driver.quit();
    }
}