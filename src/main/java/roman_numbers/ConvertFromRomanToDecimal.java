package roman_numbers;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConvertFromRomanToDecimal {
    HashMap<Character, Integer> romanNumbers;

    public ConvertFromRomanToDecimal() {
        this.romanNumbers = RomanNumber.createRomanDecimalMap();
    }

    public int convert(String romanNumber) {
        if (isRomanNumberCorrect(romanNumber)) {
            int sum = 0;
            int decimal = 0;
            int previousDecimal = 0;
            int i = romanNumber.length() - 1;
            while(i >= 0) {
                decimal = RomanNumber.valueOf(String.valueOf(romanNumber.charAt(i))).getDecimal();
                if (i != 0) {
                    previousDecimal = RomanNumber.valueOf(String.valueOf(romanNumber.charAt(i - 1))).getDecimal();
                    if (previousDecimal < decimal) {
                        sum += (decimal - previousDecimal);
                        i-=2;
                    } else {
                        sum += decimal;
                        i--;
                    }
                } else {
                    sum += decimal;
                    i--;
                }
            }
            return sum;
        } else {
            throw new IllegalArgumentException("Roman number is incorrect");
        }
    }

    private boolean isRomanNumberCorrect(String romanNumber) {
        String number = romanNumber.trim();
        String parameterRegex = "[MDCLXVI]+";
        Pattern parameterPattern = Pattern.compile(parameterRegex);
        Matcher parameterMatcher = parameterPattern.matcher(number);
        String found = "";
        while (parameterMatcher.find()) {
            found = parameterMatcher.group();
        }
        if (found.length() == number.length()) {
            return true;
        }
        return false;
    }
}
