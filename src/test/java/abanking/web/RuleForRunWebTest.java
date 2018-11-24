package abanking.web;

import abanking.web.pages.AbankingMainPage;
import abanking.web.pages.LoginPage;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.openqa.selenium.chrome.ChromeDriver;

import static abanking.web.Environment.applicationUrl;
import static abanking.web.Environment.webDriver;
import static abanking.web.Utils.makeScreenshoot;

public class RuleForRunWebTest implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                startWebDriver();
                loginMethod();
                try {
                    base.evaluate();
                }
                finally {
                    makeScreenshoot();
                    killWebDriver();
                }
            }
        };
    }

    private void startWebDriver() {
        System.setProperty("webdriver.chrome.driver", "src//test//resources//chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
    }

    private void loginMethod() {
        webDriver.get(applicationUrl + "/account/login");
        LoginPage loginPage = new LoginPage(webDriver);
        loginPage.loginMethod("retail", "retail");
        AbankingMainPage mainPage = new AbankingMainPage();
        Waiters.waitVisibility(webDriver, mainPage.paymentItem, "Ждем загрузки главной страницы");
    }

    private void killWebDriver() {
        webDriver.quit();
    }
}
