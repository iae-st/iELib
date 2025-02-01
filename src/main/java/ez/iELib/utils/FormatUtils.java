package ez.iELib.utils;

import ez.iELib.utils.colorUtils.ColorUtils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class FormatUtils {
    private final static int CENTER_PX = 154;

    public static String formatValueLong(double value) {
        DecimalFormatSymbols symbols = DecimalFormatSymbols.getInstance();
        symbols.setGroupingSeparator(',');
        DecimalFormat formatter = new DecimalFormat("###,###,###,###,###", symbols);
        return formatter.format(value);
    }

    public static String formatNumber(double number) {
        String[] suffixes = {"", "K", "M", "B"};

        if (number < 1000) {
            return String.valueOf(number);
        }

        int magnitude = (int) Math.floor(Math.log10(number) / 3);
        double rounded = number / Math.pow(10, magnitude * 3);

        return formatDecimal(rounded) + suffixes[magnitude];
    }

    private static String formatDecimal(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(number);
    }

    public static String centerMessage(String message) {
        return centerMessage(message, 0, true, false);
    }

    public static String centerMessage(String message, int length, Boolean color, Boolean onlyCompensate) {
        if (message != null && !message.equals("") && message.contains("<center>") && message.contains("</center")) {
            message = color ? ColorUtils.color(message.replaceAll("<center>|</center>", "")) : message.replaceAll("<center>|</center>", "");
            String var13 = color
                    ? ColorUtils.color(message.replaceAll("&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|\\{#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})}.*?", ""))
                    : message.replaceAll("&#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})|\\{#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})}.*?", "");
            int messagePxSize = length;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : var13.toCharArray()) {
                if (c == 167 || c == '&') {
                    previousCode = true;
                } else if (previousCode) {
                    previousCode = false;
                    if (c != 'l' && c != 'L') {
                        isBold = false;
                    } else {
                        isBold = true;
                    }
                } else {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    messagePxSize++;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            int toCompensate = 154 - halvedMessageSize;
            int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
            int compensated = 0;

            StringBuilder sb;
            for (sb = new StringBuilder(); compensated < toCompensate; compensated += spaceLength) {
                sb.append(" ");
            }

            return onlyCompensate ? sb.toString() : sb + var13;
        } else if (onlyCompensate) {
            return "";
        } else {
            return color ? ColorUtils.color(message) : message;
        }
    }

    public static double roundUpToTwoDecimalPlaces(double value) {
        double roundedValue = Math.ceil(value * 100) / 100;
        return roundedValue;
    }
}
