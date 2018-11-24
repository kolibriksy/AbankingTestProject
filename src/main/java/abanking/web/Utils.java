package abanking.web;

import java.text.DecimalFormat;

public class Utils {

    public static String formatDecimalToString(double value) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(value);
    }
}
