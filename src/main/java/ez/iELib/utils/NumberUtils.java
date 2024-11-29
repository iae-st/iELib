package ez.iELib.utils;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NumberUtils {
    public static Float randomChance() {
        return ThreadLocalRandom.current().nextFloat();
    }

    public static int randomNumBetween(int min, int max) {
        Random rand = new Random();

        return rand.nextInt((max - min) + 1) + min;
    }

    public static Double randomLongBetween(int min, int max) {
        Random rand = new Random();
        double output = (rand.nextDouble((max - min) + 1) + min);


        return Double.parseDouble(new DecimalFormat("##,###").format(output));
    }

    public static Integer parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 1;
        }
    }

    public static String intToRoman(int num) {
        if (num <= 0 || num > 3999) {
            return String.valueOf(num);
        }

        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                result.append(symbols[i]);
            }
        }

        return result.toString();
    }

    public static boolean isPercentageRolled(double targetPercentage) {
        double randomPercentage = getRandomPercentage();
        return randomPercentage <= targetPercentage;
    }

    private static double getRandomPercentage() {
        Random rand = new Random();
        return 0.001 + (100 - 0.001) * rand.nextDouble();
    }

    public static boolean isNumberInArray(int[] array, int numberToCheck) {
        for (int element : array) {
            if (element == numberToCheck) {
                return true;
            }
        }
        return false;
    }

    public static String addSuffix(int number) {
        if (number >= 11 && number <= 13) {
            return number + "th";
        }

        int lastDigit = number % 10;
        String suffix = switch (lastDigit) {
            case 1 -> "st";
            case 2 -> "nd";
            case 3 -> "rd";
            default -> "th";
        };

        return number + suffix;
    }

    public static boolean shouldDrop(double percentage) {
        if (percentage < 0 || percentage > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }

        int randomValue = (int) (Math.random() * 101);

        return randomValue < percentage;
    }

}
