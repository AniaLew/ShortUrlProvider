package FindNumberInArray;

import java.util.Arrays;

public class FindNumber {
    public static int findMissingNumber() {
        int[] numbers = {4, 7, 0, 2, 1, 3, 5, 8};
        int sumAll = 0;
        for (int i = 0; i <= numbers.length; i++) {
            sumAll += i;
        }
        int sumArray = Arrays.stream(numbers).sum();
        return sumAll - sumArray;
    }
}
