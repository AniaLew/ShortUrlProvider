package roman_numbers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ConvertFromRomanToDecimalTest {

    @DisplayName("Should convert roman number to decimal number")
    @ParameterizedTest(name = "{index} => roman={0}, decimal={1}")
    @CsvSource({
            "MMXXI, 2021",
            "MCMXCIX, 1999",
            "MDLV, 1555",
            "MCDXLIV, 1444"
    })
    void shouldConvertToDecimal(String roman, int decimal) {
        ConvertFromRomanToDecimal converter = new ConvertFromRomanToDecimal();
        assertEquals(decimal, converter.convert(roman));
    }


    @Test
    void shouldThrowIllegalArgumentException() throws Exception{
        //given
        String romanNumber = "MCDXLIVa";
        ConvertFromRomanToDecimal converter = new ConvertFromRomanToDecimal();
        //when
        Exception exception = assertThrows(Exception.class,
                () -> converter.convert(romanNumber));
        //then
        Assertions.assertEquals("Roman number is incorrect", exception.getMessage());
    }
}