package abanking.web;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.text.DecimalFormat;

import static abanking.web.Environment.webDriver;

public class Utils {

    public static String formatDecimalToString(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }

    @Attachment
    public static byte[] makeScreenshoot() {
        return ((TakesScreenshot)webDriver).getScreenshotAs(OutputType.BYTES);
    }
}
