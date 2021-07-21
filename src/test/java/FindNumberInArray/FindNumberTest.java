package FindNumberInArray;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FindNumberTest {

    @Test
    void shouldFindMissingNumber() {
        //when
        int actual = FindNumber.findMissingNumber();
        //then
        Assertions.assertEquals(6, actual);
    }
}