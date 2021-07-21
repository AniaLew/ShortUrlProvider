package roman_numbers;

import java.util.HashMap;

public enum RomanNumber {
    M(1000),
    D(500),
    C(100),
    L( 50),
    X(10),
    V(5),
    I(1);

    private final int decimal;

    RomanNumber(int decimal) {
        this.decimal = decimal;
    }

    public int getDecimal() {
        return decimal;
    }

    public static HashMap<Character, Integer> createRomanDecimalMap() {
        HashMap<Character, Integer> romanNumbers = new HashMap<>();
        romanNumbers.put('M',  1000);
        romanNumbers.put('D',  500);
        romanNumbers.put('C',  100);
        romanNumbers.put('L',  50);
        romanNumbers.put('X',  10);
        romanNumbers.put('V',  5);
        romanNumbers.put('I',  1);
        return romanNumbers;
    }
}
