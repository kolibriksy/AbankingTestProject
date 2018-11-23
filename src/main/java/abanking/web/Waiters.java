package abanking.web;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waiters {

    public static long timeoutInSeconds = 30;

    public static void freezeInMilliSeconds(long milliSeconds) {
        //Try.run(() -> Thread.sleep(milliSeconds));

        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void waitVisibility(WebDriver webDriver, String xpath, String message) {
        new WebDriverWait(webDriver, timeoutInSeconds)
                .withMessage(message)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public static void waitVisibility(WebDriver webDriver, By by, String message) {
        new WebDriverWait(webDriver, timeoutInSeconds)
                .withMessage(message).until(ExpectedConditions
                .visibilityOfElementLocated(by));
    }

    public static void waitVisibility(WebDriver webDriver, WebElement element, String message) {
        new WebDriverWait(webDriver, timeoutInSeconds)
                .withMessage(message).until(ExpectedConditions
                .visibilityOf(element));
    }

    public static void waitInvisibility(WebDriver webDriver, String xpath, String message) {
        new WebDriverWait(webDriver, timeoutInSeconds)
                .withMessage(message).until(ExpectedConditions
                .invisibilityOfElementLocated(By.xpath(xpath)));
    }

    public static void waitInvisibility(WebDriver webDriver, By by, String message) {
        new WebDriverWait(webDriver, timeoutInSeconds)
                .withMessage(message).until(ExpectedConditions
                .invisibilityOfElementLocated(by));
    }
}
