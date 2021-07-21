package roman_numbers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ConvertFromDecimalToRomanTest {

    @Test
    void shouldConvertDecimalToRomanNumber() {
       int decimal = 2222;
       ConvertFromDecimalToRoman converter = new ConvertFromDecimalToRoman();
       String roman = converter.convert(decimal);
       System.out.println(roman);

    }

    @DisplayName("Should convert decimal number to roman number")
    @ParameterizedTest(name = "{index} => roman={0}, decimal={1}")
    @CsvSource({
            "MMXXI, 2021",
            "MDCCCCLXXXXVIIII, 1999",
            "MDLV, 1555",
            "MCCCCXXXXIIII, 1444"
    })
    void shouldConvertToDecimal(String roman, int decimal) {
        ConvertFromDecimalToRoman converter = new ConvertFromDecimalToRoman();
        assertEquals(roman, converter.convert(decimal));
    }
}