package roman_numbers;

import java.util.HashMap;

public class ConvertFromDecimalToRoman {
    HashMap<Character, Integer> romanNumbers;

    public ConvertFromDecimalToRoman() {
        this.romanNumbers = RomanNumber.createRomanDecimalMap();
    }

    public String convert(int decimalNumber) {
        if(decimalNumber >= 3000) {
            throw new IllegalArgumentException("Decimal number must be less than 3000");
        }
        StringBuilder builder = new StringBuilder();
        int remainder = decimalNumber;
        int divisionResult = 0;
        for(RomanNumber n: RomanNumber.values()) {
            divisionResult = remainder / n.getDecimal();
            remainder = remainder % n.getDecimal();
            if(divisionResult != 0) {
                for (int i = 0; i < divisionResult; i++) {
                    builder.append(n);
                }
            }
            if(remainder == 0) {
                break;
            }
        }
        return builder.toString();
    }
}
